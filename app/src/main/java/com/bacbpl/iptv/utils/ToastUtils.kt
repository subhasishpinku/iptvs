package com.bacbpl.iptv.utils

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import java.lang.ref.WeakReference

object ToastUtils {

    private const val TAG = "ToastUtils"
    private var currentToast: Toast? = null

    // Main safe method for Activity
    fun showSafeToast(activity: Activity?, message: String?, duration: Int = Toast.LENGTH_SHORT) {
        if (message.isNullOrEmpty()) {
            Log.d(TAG, "Message is null or empty")
            return
        }

        activity?.let { act ->
            // Check if Activity is in valid state
            if (!act.isFinishing && !act.isDestroyed) {
                // Run on UI thread
                Handler(Looper.getMainLooper()).post {
                    try {
                        cancelCurrentToast()
                        currentToast = Toast.makeText(act, message, duration)
                        currentToast?.show()
                    } catch (e: Exception) {
                        Log.e(TAG, "Error showing toast: ${e.message}")
                    }
                }
            } else {
                Log.d(TAG, "Activity is finishing/destroyed, cannot show toast: $message")
            }
        } ?: run {
            Log.d(TAG, "Activity is null, cannot show toast: $message")
        }
    }

    // Safe method for Context
    fun showSafeToast(context: Context?, message: String?, duration: Int = Toast.LENGTH_SHORT) {
        if (message.isNullOrEmpty()) {
            Log.d(TAG, "Message is null or empty")
            return
        }

        context?.let { ctx ->
            Handler(Looper.getMainLooper()).post {
                try {
                    when (ctx) {
                        is Activity -> {
                            if (!ctx.isFinishing && !ctx.isDestroyed) {
                                cancelCurrentToast()
                                currentToast = Toast.makeText(ctx, message, duration)
                                currentToast?.show()
                            } else {
                                Log.d(TAG, "Activity is finishing/destroyed, message: $message")
                            }
                        }
                        else -> {
                            // For Application or Service contexts
                            cancelCurrentToast()
                            currentToast = Toast.makeText(ctx.applicationContext, message, duration)
                            currentToast?.show()
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error showing toast: ${e.message}")
                }
            }
        } ?: run {
            Log.d(TAG, "Context is null, cannot show toast: $message")
        }
    }

    // Safe method with WeakReference to Activity
    fun showSafeToast(activityRef: WeakReference<Activity>?, message: String?, duration: Int = Toast.LENGTH_SHORT) {
        if (message.isNullOrEmpty()) {
            return
        }

        activityRef?.get()?.let { activity ->
            showSafeToast(activity, message, duration)
        } ?: run {
            Log.d(TAG, "Activity reference is null or cleared")
        }
    }

    // Method to cancel current toast (useful to prevent queuing)
    fun cancelCurrentToast() {
        try {
            currentToast?.cancel()
            currentToast = null
        } catch (e: Exception) {
            // Ignore cancellation errors
        }
    }

    // Simple method with application context (always works but might show on wrong context)
    fun showSimpleToast(context: Context?, message: String?, duration: Int = Toast.LENGTH_SHORT) {
        if (message.isNullOrEmpty() || context == null) {
            return
        }

        Handler(Looper.getMainLooper()).post {
            try {
                Toast.makeText(context.applicationContext, message, duration).show()
            } catch (e: Exception) {
                Log.e(TAG, "Error showing simple toast: ${e.message}")
            }
        }
    }
}