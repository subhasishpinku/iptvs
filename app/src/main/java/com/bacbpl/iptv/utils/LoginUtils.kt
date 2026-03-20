package com.bacbpl.iptv.utils

import android.app.Application
import android.content.Context
import com.bacbpl.iptv.data.SharedPrefManager
import com.bacbpl.iptv.ui.activities.otpscreen.data.VerifyOtpResponse
import com.bacbpl.iptv.ui.activities.otpscreen.viewmodel.OtpViewModel

object LoginUtils {

    fun isUserLoggedIn(context: Context): Boolean {
        return try {
            SharedPrefManager(context).isLoggedIn()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    fun getCurrentUser(context: Context): VerifyOtpResponse? {
        return SharedPrefManager(context).getUserLogin()
    }

    fun clearUserSession(context: Context) {
        try {
            SharedPrefManager(context).clearUserSession()
            UserSession.clearSession(context)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // If you need ViewModel
    fun checkUserLoggedInWithViewModel(application: Application): Boolean {
        val viewModel = OtpViewModel(application)
        return viewModel.checkIfLoggedIn()
    }

    fun getCurrentUserWithViewModel(application: Application): VerifyOtpResponse? {
        val viewModel = OtpViewModel(application)
        return viewModel.getCurrentUser()
    }
}