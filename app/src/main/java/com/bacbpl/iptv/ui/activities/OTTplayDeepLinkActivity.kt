package com.bacbpl.iptv.ui.activities

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.CookieManager
import android.webkit.WebStorage
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.bacbpl.iptv.BuildConfig
import com.bacbpl.iptv.R
import com.bacbpl.iptv.utils.ToastUtils

class OTTplayDeepLinkActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var videoContainer: FrameLayout
    private var customView: View? = null
    private var customViewCallback: WebChromeClient.CustomViewCallback? = null
    private var originalOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    private var webChromeClient: MyWebChromeClient? = null

    // Add cache management
    private var isPageLoaded = false

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ottplay_deep_link)

        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)
        videoContainer = findViewById(R.id.videoContainer)

        setupOptimizedWebView()

        val deepLink = intent.dataString
        if (deepLink != null) {
            loadUrlOptimized(deepLink)
        } else {
            // FIXED: Use ToastUtils instead of direct Toast
            ToastUtils.showSafeToast(this, "No URL provided")
            finish()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupOptimizedWebView() {
        val webSettings = webView.settings

        // Essential settings only
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true

        // For Android 7.1.2, use standard cache mode
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT

        // Enable localStorage
        webSettings.databaseEnabled = true

        // Set cache size via WebView database quota
        webView.webChromeClient = object : WebChromeClient() {
            override fun onExceededDatabaseQuota(url: String, databaseIdentifier: String,
                                                 currentQuota: Long, estimatedSize: Long,
                                                 totalUsedQuota: Long, quotaUpdater: WebStorage.QuotaUpdater) {
                // Allow up to 50MB
                quotaUpdater.updateQuota(50 * 1024 * 1024)
            }
        }

        // Enable mixed content but with caution
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
        }

        // Disable features that slow down older devices
        webSettings.setSupportZoom(false)
        webSettings.builtInZoomControls = false
        webSettings.displayZoomControls = false
        webSettings.allowFileAccess = false
        webSettings.allowContentAccess = false

        // Video settings
        webSettings.mediaPlaybackRequiresUserGesture = true // Better performance

        // Use software rendering for Android 7.1.2 if needed
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        } else {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        }

        webChromeClient = MyWebChromeClient()
        webView.webChromeClient = webChromeClient

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.visibility = View.VISIBLE
                isPageLoaded = false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
                if (!isPageLoaded) {
                    injectMinimalVideoHandlerJS()
                    isPageLoaded = true
                }
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    // Handle video URLs
                    if (url.contains(".mp4") || url.contains(".m3u8") ||
                        url.contains(".mkv") || url.contains(".webm")) {
                        playVideoDirectly(url)
                        return true
                    }

                    // Load all other URLs in WebView
                    if (url.startsWith("http://") || url.startsWith("https://")) {
                        view?.loadUrl(url)
                        return true
                    }
                }
                return false
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                url?.let {
                    if (it.contains(".mp4") || it.contains(".m3u8")) {
                        Log.d("OTTplay", "Video: $it")
                    }
                }
            }
        }

        // Enable debugging only in debug builds
        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }

    inner class MyWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            progressBar.progress = newProgress
            if (newProgress >= 90) {
                progressBar.visibility = View.GONE
            } else {
                progressBar.visibility = View.VISIBLE
            }
        }

        override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
            if (customView != null) {
                callback?.onCustomViewHidden()
                return
            }

            customView = view
            customViewCallback = callback

            webView.visibility = View.GONE
            videoContainer.visibility = View.VISIBLE
            videoContainer.addView(view)

            originalOrientation = requestedOrientation
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        override fun onHideCustomView() {
            customView?.let {
                videoContainer.removeView(it)
                customView = null
            }
            customViewCallback?.onCustomViewHidden()
            customViewCallback = null

            webView.visibility = View.VISIBLE
            videoContainer.visibility = View.GONE
            requestedOrientation = originalOrientation
        }

        override fun getVideoLoadingProgressView(): View? {
            return layoutInflater.inflate(R.layout.video_loading_progress, null)
        }
    }

    private fun injectMinimalVideoHandlerJS() {
        val jsCode = """
            javascript:(function() {
                try {
                    // Add minimal video controls
                    var videos = document.getElementsByTagName('video');
                    for(var i = 0; i < videos.length; i++) {
                        videos[i].setAttribute('controls', 'true');
                        videos[i].setAttribute('playsinline', 'true');
                        videos[i].setAttribute('webkit-playsinline', 'true');
                    }
                    
                    // Make elements focusable for DPAD
                    var elements = document.querySelectorAll('button, a, .play-button, [role="button"]');
                    for(var i = 0; i < elements.length; i++) {
                        elements[i].setAttribute('tabindex', '0');
                    }
                } catch(e) {
                    console.log('Error: ' + e);
                }
            })();
        """.trimIndent()

        webView.loadUrl(jsCode)
    }

    private fun loadUrlOptimized(url: String) {
        try {
            if (isPageLoaded) {
                webView.reload()
            } else {
                webView.loadUrl(url)
            }
        } catch (e: Exception) {
            Log.e("OTTplay", "Error loading URL: ${e.message}")
            // FIXED: Use ToastUtils with safe check
            runOnUiThread {
                ToastUtils.showSafeToast(this@OTTplayDeepLinkActivity, "Error loading content")
            }
        }
    }

    private fun playVideoDirectly(videoUrl: String) {
        try {
            val videoType = when {
                videoUrl.contains(".m3u8") -> "application/x-mpegURL"
                videoUrl.contains(".mp4") -> "video/mp4"
                videoUrl.contains(".webm") -> "video/webm"
                else -> "video/mp4"
            }

            val videoHtml = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <style>
                        body { margin:0; padding:0; background:black; }
                        video { width:100%; height:100%; }
                    </style>
                </head>
                <body>
                    <video controls autoplay>
                        <source src="$videoUrl" type="$videoType">
                    </video>
                </body>
                </html>
            """.trimIndent()

            webView.loadDataWithBaseURL(null, videoHtml, "text/html", "UTF-8", null)
        } catch (e: Exception) {
            Log.e("OTTplay", "Error playing video: ${e.message}")
            // FIXED: Use ToastUtils with safe check
            runOnUiThread {
                ToastUtils.showSafeToast(this@OTTplayDeepLinkActivity, "Error playing video")
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                webView.loadUrl("javascript:document.activeElement?.click()")
                return true
            }
            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> {
                webView.loadUrl("javascript:document.querySelector('video')?.paused ? document.querySelector('video')?.play() : document.querySelector('video')?.pause()")
                return true
            }
            KeyEvent.KEYCODE_BACK -> {
                if (customView != null) {
                    webChromeClient?.onHideCustomView()
                    return true
                }
                if (webView.canGoBack()) {
                    webView.goBack()
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
        webView.loadUrl("javascript:document.querySelector('video')?.pause()")
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
    }

    override fun onDestroy() {
        try {
            if (customView != null) {
                webChromeClient?.onHideCustomView()
            }
            webView.stopLoading()
            webView.destroy()
        } catch (e: Exception) {
            Log.e("OTTplay", "Error in onDestroy: ${e.message}")
        }
        super.onDestroy()
    }
}