package com.bacbpl.iptv.jetStram.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bacbpl.iptv.data.api.ApiService
import com.bacbpl.iptv.data.api.DeleteAccountResponse
import com.bacbpl.iptv.data.api.SendDeleteOtpResponse
import com.bacbpl.iptv.ui.activities.signupscreen.data.repository.Resource
import com.bacbpl.iptv.utils.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _sendOtpState = MutableStateFlow<Resource<SendDeleteOtpResponse>?>(null)
    val sendOtpState: StateFlow<Resource<SendDeleteOtpResponse>?> = _sendOtpState.asStateFlow()

    private val _isSendingOtp = MutableStateFlow(false)
    val isSendingOtp: StateFlow<Boolean> = _isSendingOtp.asStateFlow()

    private val _deleteAccountState = MutableStateFlow<Resource<DeleteAccountResponse>?>(null)
    val deleteAccountState: StateFlow<Resource<DeleteAccountResponse>?> = _deleteAccountState.asStateFlow()

    private val _isDeletingAccount = MutableStateFlow(false)
    val isDeletingAccount: StateFlow<Boolean> = _isDeletingAccount.asStateFlow()

    private val _generatedOtp = MutableStateFlow<Int?>(null)
    val generatedOtp: StateFlow<Int?> = _generatedOtp.asStateFlow()

    fun sendDeleteOtp(mobile: String, deviceId: String, context: Context) {
        viewModelScope.launch {
            _isSendingOtp.value = true
            _sendOtpState.value = Resource.Loading()

            try {
                val cleanedMobile = mobile.replace("+91", "").replace(" ", "")
                val response = apiService.sendDeleteOtp(cleanedMobile, deviceId)

                if (response.isSuccessful && response.body()?.status == true) {
                    val otpResponse = response.body()
                    _generatedOtp.value = otpResponse?.otp
                    _sendOtpState.value = Resource.Success(otpResponse) as Resource<SendDeleteOtpResponse>?
                } else {
                    _sendOtpState.value = Resource.Error(
                        response.body()?.message ?: "Failed to send OTP",
                        null
                    )
                }
            } catch (e: IOException) {
                _sendOtpState.value = Resource.Error("Network error: ${e.message}", null)
            } catch (e: HttpException) {
                _sendOtpState.value = Resource.Error("Server error: ${e.message}", null)
            } catch (e: Exception) {
                _sendOtpState.value = Resource.Error("Unexpected error: ${e.message}", null)
            } finally {
                _isSendingOtp.value = false
            }
        }
    }

    fun verifyOtpAndDelete(otp: String, deviceId: String, mobile: String, context: Context) {
        viewModelScope.launch {
            _isDeletingAccount.value = true
            _deleteAccountState.value = Resource.Loading()

            try {
                val cleanedMobile = mobile.replace("+91", "").replace(" ", "")
                val response = apiService.verifyOtpAndDelete(otp, deviceId, cleanedMobile)

                if (response.isSuccessful && response.body()?.status == true) {
                    _deleteAccountState.value = Resource.Success(response.body()) as Resource<DeleteAccountResponse>?
                    // Clear session on successful deletion
                    UserSession.clearSession(context)
                } else {
                    _deleteAccountState.value = Resource.Error(
                        response.body()?.message ?: "Failed to delete account",
                        null
                    )
                }
            } catch (e: IOException) {
                _deleteAccountState.value = Resource.Error("Network error: ${e.message}", null)
            } catch (e: HttpException) {
                _deleteAccountState.value = Resource.Error("Server error: ${e.message}", null)
            } catch (e: Exception) {
                _deleteAccountState.value = Resource.Error("Unexpected error: ${e.message}", null)
            } finally {
                _isDeletingAccount.value = false
            }
        }
    }

    fun resetStates() {
        _sendOtpState.value = null
        _deleteAccountState.value = null
        _generatedOtp.value = null
        _isSendingOtp.value = false
        _isDeletingAccount.value = false
    }
}