package com.bacbpl.iptv.ui.activities.otpscreen.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bacbpl.iptv.ui.activities.otpscreen.viewmodel.OtpViewModel

class OtpViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OtpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OtpViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}