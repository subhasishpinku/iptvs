package com.bacbpl.iptv.jetStram.presentation.screens.videoPlayer

import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.compose.PlayerSurface
import androidx.tv.material3.MaterialTheme
import com.bacbpl.iptv.jetStram.data.entities.MovieDetails
import com.bacbpl.iptv.jetStram.presentation.common.Error
import com.bacbpl.iptv.jetStram.presentation.common.Loading
import com.bacbpl.iptv.jetStram.presentation.screens.videoPlayer.components.VideoPlayerControls
import com.bacbpl.iptv.jetStram.presentation.screens.videoPlayer.components.VideoPlayerOverlay
import com.bacbpl.iptv.jetStram.presentation.screens.videoPlayer.components.VideoPlayerPulse
import com.bacbpl.iptv.jetStram.presentation.screens.videoPlayer.components.VideoPlayerPulse.Type.BACK
import com.bacbpl.iptv.jetStram.presentation.screens.videoPlayer.components.VideoPlayerPulse.Type.FORWARD
import com.bacbpl.iptv.jetStram.presentation.screens.videoPlayer.components.VideoPlayerPulseState
import com.bacbpl.iptv.jetStram.presentation.screens.videoPlayer.components.VideoPlayerState
import com.bacbpl.iptv.jetStram.presentation.screens.videoPlayer.components.rememberVideoPlayerPulseState
import com.bacbpl.iptv.jetStram.presentation.screens.videoPlayer.components.rememberVideoPlayerState
import com.bacbpl.iptv.jetStram.presentation.utils.handleDPadKeyEvents
import com.bacbpl.iptv.utils.ToastUtils
import java.io.File

private const val TAG = "VideoPlayer"
private const val SURFACE_TYPE_TEXTURE_VIEW = 1

object VideoPlayerScreen {
    const val MovieIdBundleKey = "movieId"
}

@OptIn(UnstableApi::class)
@Composable
fun rememberPlayer(context: android.content.Context): ExoPlayer {
    return remember {
        // Create cache directory
        val cacheDir = File(context.cacheDir, "media")
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }

        // Create data source factory with proper headers for streaming
        val dataSourceFactory = DefaultHttpDataSource.Factory()
            .setUserAgent("JetStream/1.0 (Android TV)")
            .setConnectTimeoutMs(30000)
            .setReadTimeoutMs(30000)
            .setAllowCrossProtocolRedirects(true)
            .setDefaultRequestProperties(mapOf(
                "Accept" to "*/*",
                "Connection" to "keep-alive"
            ))

        ExoPlayer.Builder(context)
            .setSeekForwardIncrementMs(10000)
            .setSeekBackIncrementMs(10000)
            .build()
            .apply {
                playWhenReady = true
                repeatMode = Player.REPEAT_MODE_OFF
                setHandleAudioBecomingNoisy(true)
            }
    }
}

@Composable
fun VideoPlayerScreen(
    onBackPressed: () -> Unit,
    videoPlayerScreenViewModel: VideoPlayerScreenViewModel = hiltViewModel()
) {
    val uiState by videoPlayerScreenViewModel.uiState.collectAsStateWithLifecycle()

    when (val s = uiState) {
        is VideoPlayerScreenUiState.Loading -> {
            Loading(modifier = Modifier.fillMaxSize())
        }
        is VideoPlayerScreenUiState.Error -> {
            Error(modifier = Modifier.fillMaxSize())
        }
        is VideoPlayerScreenUiState.Done -> {
            VideoPlayerScreenContent(
                movieDetails = s.movieDetails,
                onBackPressed = onBackPressed
            )
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreenContent(movieDetails: MovieDetails, onBackPressed: () -> Unit) {
    val context = LocalContext.current
    val exoPlayer = rememberPlayer(context)

    var isPlaybackError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isPlayerReady by remember { mutableStateOf(false) }
    var debugInfo by remember { mutableStateOf("") }

    val videoPlayerState = rememberVideoPlayerState(hideSeconds = 4)

    // Print URL for debugging
    LaunchedEffect(movieDetails) {
        Log.d(TAG, "=== VIDEO PLAYER DEBUG ===")
        Log.d(TAG, "Movie ID: ${movieDetails.id}")
        Log.d(TAG, "Movie Name: ${movieDetails.name}")
        Log.d(TAG, "Video URL: ${movieDetails.videoUri}")
        Log.d(TAG, "Video URL length: ${movieDetails.videoUri?.length ?: 0}")
        Log.d(TAG, "Is URL empty: ${movieDetails.videoUri.isNullOrEmpty()}")
        Log.d(TAG, "==========================")

        debugInfo = """
            URL: ${movieDetails.videoUri ?: "null"}
            Length: ${movieDetails.videoUri?.length ?: 0}
            Empty: ${movieDetails.videoUri.isNullOrEmpty()}
        """.trimIndent()
    }

    // Error listener with detailed logging
    LaunchedEffect(exoPlayer) {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                isPlaybackError = true
                errorMessage = error.message
                isPlayerReady = false

                // Detailed error logging
//                Log.e(TAG, "=== PLAYBACK ERROR ===")
//                Log.e(TAG, "Error: ${error.message}")
//                Log.e(TAG, "Error code: ${error.errorCodeName}")
//                Log.e(TAG, "Stack trace:", error)
//                Log.e(TAG, "URL attempted: ${movieDetails.videoUri}")
//                Log.e(TAG, "======================")

                ToastUtils.showSafeToast(
                    context,
                    "Playback Error: ${error.message ?: "Unknown error"}"
                )
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_READY -> {
                        Log.d(TAG, "Player STATE_READY")
                        isPlayerReady = true
                        isPlaybackError = false
                    }
                    Player.STATE_BUFFERING -> {
                        Log.d(TAG, "Player STATE_BUFFERING")
                    }
                    Player.STATE_ENDED -> {
                        Log.d(TAG, "Player STATE_ENDED")
                    }
                    Player.STATE_IDLE -> {
                        Log.d(TAG, "Player STATE_IDLE")
                    }
                }
            }

            override fun onIsLoadingChanged(isLoading: Boolean) {
                Log.d(TAG, "Loading: $isLoading")
            }
        })
    }

    LaunchedEffect(exoPlayer, movieDetails) {
        try {
            // Clear any existing media
            exoPlayer.clearMediaItems()

            val videoUrl = movieDetails.videoUri
            if (videoUrl.isNullOrEmpty()) {
                Log.e(TAG, "Video URL is null or empty!")
                isPlaybackError = true
                errorMessage = "Video URL is empty"
                isPlayerReady = false
                return@LaunchedEffect
            }

            Log.d(TAG, "Creating media item for URL: $videoUrl")

            // Parse URI to validate
            val uri = Uri.parse(videoUrl)
            Log.d(TAG, "Parsed URI: $uri")
            Log.d(TAG, "URI scheme: ${uri.scheme}")
            Log.d(TAG, "URI host: ${uri.host}")
            Log.d(TAG, "URI path: ${uri.path}")

            // Create and set the media item with proper MIME type detection
            val mediaItem = createMediaItem(videoUrl)
            exoPlayer.setMediaItem(mediaItem)
            Log.d(TAG, "Media item created and set, preparing player...")
            exoPlayer.prepare()
        } catch (e: Exception) {
            Log.e(TAG, "Exception in LaunchedEffect", e)
            isPlaybackError = true
            errorMessage = e.message ?: "Unknown error"
            isPlayerReady = false
            e.printStackTrace()
        }
    }

    BackHandler(onBack = {
        Log.d(TAG, "Back pressed, releasing player")
        exoPlayer.release()
        onBackPressed()
    })

    val pulseState = rememberVideoPlayerPulseState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .dPadEvents(
                exoPlayer,
                videoPlayerState,
                pulseState
            )
            .focusable()
    ) {
        // Show loading while player is not ready
        if (!isPlayerReady && !isPlaybackError) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Loading video...", color = MaterialTheme.colorScheme.onSurface)
//                    if (debugInfo.isNotEmpty()) {
//                        Text(
//                            text = debugInfo,
//                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
//                            fontSize = 12.sp
//                        )
//                    }
                }
            }
        } else {
            PlayerSurface(
                player = exoPlayer,
                modifier = Modifier.fillMaxSize(),
                surfaceType = SURFACE_TYPE_TEXTURE_VIEW
            )
        }

        if (isPlaybackError) {
            PlaybackErrorOverlay(
                errorMessage = errorMessage,
                debugInfo = debugInfo,
                onRetry = {
                    Log.d(TAG, "Retrying playback...")
                    isPlaybackError = false
                    exoPlayer.prepare()
                },
                onBackPressed = onBackPressed
            )
        } else {
            val focusRequester = remember { FocusRequester() }
            VideoPlayerOverlay(
                modifier = Modifier.align(Alignment.BottomCenter),
                focusRequester = focusRequester,
                isPlaying = exoPlayer.isPlaying,
                isControlsVisible = videoPlayerState.isControlsVisible,
                centerButton = { VideoPlayerPulse(pulseState) },
                subtitles = { /* TODO Implement subtitles */ },
                showControls = videoPlayerState::showControls,
                controls = {
                    VideoPlayerControls(
                        player = exoPlayer,
                        movieDetails = movieDetails,
                        focusRequester = focusRequester,
                        onShowControls = { videoPlayerState.showControls(exoPlayer.isPlaying) },
                    )
                }
            )
        }
    }
}

@OptIn(UnstableApi::class)
private fun createMediaItem(url: String): MediaItem {
    return when {
        url.contains(".m3u8", ignoreCase = true) -> {
            // HLS stream
            MediaItem.Builder()
                .setUri(url)
                .setMimeType(androidx.media3.common.MimeTypes.APPLICATION_M3U8)
                .build()
        }
        url.contains(".mpd", ignoreCase = true) -> {
            // DASH stream
            MediaItem.Builder()
                .setUri(url)
                .setMimeType(androidx.media3.common.MimeTypes.APPLICATION_MPD)
                .build()
        }
        url.contains(".mp4", ignoreCase = true) ||
                url.contains(".mkv", ignoreCase = true) ||
                url.contains(".avi", ignoreCase = true) -> {
            // Progressive video
            MediaItem.Builder()
                .setUri(url)
                .setMimeType(androidx.media3.common.MimeTypes.VIDEO_MP4)
                .build()
        }
        else -> {
            // Let ExoPlayer detect automatically
            MediaItem.fromUri(url)
        }
    }
}

@Composable
private fun PlaybackErrorOverlay(
    errorMessage: String?,
    debugInfo: String,
    onRetry: () -> Unit,
    onBackPressed: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Playback Error",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage ?: "Unknown error",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Debug info
            Text(
                text = "Debug Info:",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = debugInfo,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text(text = "Retry")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onBackPressed) {
                Text(text = "Go Back")
            }
        }
    }
}

private fun Modifier.dPadEvents(
    exoPlayer: ExoPlayer,
    videoPlayerState: VideoPlayerState,
    pulseState: VideoPlayerPulseState
): Modifier = this.handleDPadKeyEvents(
    onLeft = {
        if (!videoPlayerState.isControlsVisible) {
            exoPlayer.seekBack()
            pulseState.setType(BACK)
        }
    },
    onRight = {
        if (!videoPlayerState.isControlsVisible) {
            exoPlayer.seekForward()
            pulseState.setType(FORWARD)
        }
    },
    onUp = { videoPlayerState.showControls() },
    onDown = { videoPlayerState.showControls() },
    onEnter = {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
        } else {
            exoPlayer.play()
        }
        videoPlayerState.showControls()
    }
)