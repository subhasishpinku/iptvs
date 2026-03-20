package com.bacbpl.iptv.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast

object ToastUtils {

    // Method for Activity
    fun showSafeToast(activity: Activity?, message: String?) {
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            message?.let {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.d("ToastUtils", "Activity not available, message: $message")
        }
    }

    // Overloaded method for Context
    fun showSafeToast(context: Context?, message: String?) {
        if (context == null || message.isNullOrEmpty()) {
            Log.d("ToastUtils", "Context or message is null")
            return
        }

        when (context) {
            is Activity -> {
                if (!context.isFinishing() && !context.isDestroyed()) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("ToastUtils", "Activity is finishing/destroyed, message: $message")
                }
            }
            else -> {
                // For Application or other Context types, show directly
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Optional: Add a version that always uses Application Context
    fun showToast(context: Context?, message: String?) {
        if (context != null && !message.isNullOrEmpty()) {
            Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }
}