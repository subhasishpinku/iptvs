package com.bacbpl.iptv.ui.activities.otpscreen.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bacbpl.iptv.ui.activities.otpscreen.data.OtpResponse
import com.bacbpl.iptv.data.SharedPrefManager
import com.bacbpl.iptv.ui.activities.otpscreen.data.VerifyOtpResponse
import com.bacbpl.iptv.ui.activities.otpscreen.data.repository.OtpRepository
import com.bacbpl.iptv.ui.activities.otpscreen.data.repository.Resource

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
class OtpViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = OtpRepository()
    private val sharedPrefManager = SharedPrefManager(application.applicationContext)

    private val _sendOtpState = MutableStateFlow<OtpUiState>(OtpUiState.Initial)
    val sendOtpState: StateFlow<OtpUiState> = _sendOtpState.asStateFlow()

    private val _verifyOtpState = MutableStateFlow<VerifyOtpUiState>(VerifyOtpUiState.Initial)
    val verifyOtpState: StateFlow<VerifyOtpUiState> = _verifyOtpState.asStateFlow()

    private val _timerState = MutableStateFlow(TimerState(30, true))
    val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    fun sendOtp(mobile: String) {
        viewModelScope.launch {
            repository.sendOtp(mobile).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _sendOtpState.value = OtpUiState.Loading
                    }
                    is Resource.Success -> {
                        _sendOtpState.value = OtpUiState.Success(resource.data)
                        startTimer()
                    }
                    is Resource.Error -> {
                        _sendOtpState.value = OtpUiState.Error(resource.message)
                    }
                }
            }
        }
    }

    fun verifyOtp(mobile: String, otp: String) {
        viewModelScope.launch {
            repository.verifyOtp(mobile, otp).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _verifyOtpState.value = VerifyOtpUiState.Loading
                    }
                    is Resource.Success -> {
                        // Save the login response to SharedPreferences
                        resource.data?.let { response ->
                            sharedPrefManager.saveUserLogin(response)
                        }
                        _verifyOtpState.value = VerifyOtpUiState.Success(resource.data)
                    }
                    is Resource.Error -> {
                        _verifyOtpState.value = VerifyOtpUiState.Error(resource.message)
                    }
                }
            }
        }
    }

    fun startTimer() {
        viewModelScope.launch {
            _timerState.value = TimerState(30, true)
            for (i in 30 downTo 1) {
                kotlinx.coroutines.delay(1000)
                _timerState.value = TimerState(i, true)
            }
            _timerState.value = TimerState(0, false)
        }
    }

    fun resetTimer() {
        _timerState.value = TimerState(30, true)
    }

    fun resetStates() {
        _sendOtpState.value = OtpUiState.Initial
        _verifyOtpState.value = VerifyOtpUiState.Initial
    }

    fun checkIfLoggedIn(): Boolean {
        return sharedPrefManager.isLoggedIn()
    }

    fun getCurrentUser(): VerifyOtpResponse? {
        return sharedPrefManager.getUserLogin()
    }
}

// UI States
sealed class OtpUiState {
    object Initial : OtpUiState()
    object Loading : OtpUiState()
    data class Success(val response: OtpResponse) : OtpUiState()
    data class Error(val message: String) : OtpUiState()
}

sealed class VerifyOtpUiState {
    object Initial : VerifyOtpUiState()
    object Loading : VerifyOtpUiState()
    data class Success(val response: VerifyOtpResponse) : VerifyOtpUiState()
    data class Error(val message: String) : VerifyOtpUiState()
}

data class TimerState(
    val seconds: Int,
    val isRunning: Boolean
)