package com.bacbpl.iptv.jetStram.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bacbpl.iptv.data.api.ApiService
import com.bacbpl.iptv.data.api.LogoutResponse
import com.bacbpl.iptv.data.api.RetrofitClient
import com.bacbpl.iptv.ui.activities.signupscreen.data.repository.Resource
import com.bacbpl.iptv.utils.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _logoutState = MutableStateFlow<Resource<LogoutResponse>?>(null)
    val logoutState = _logoutState.asStateFlow()

    private val _isLoggingOut = MutableStateFlow(false)
    val isLoggingOut = _isLoggingOut.asStateFlow()

    fun logout(deviceId: String?, context: Context) {
        viewModelScope.launch {
            _isLoggingOut.value = true
            _logoutState.value = Resource.Loading()

            try {
                println("=== Starting Logout Process ===")
                println("Device ID: $deviceId")

                // Get token from UserSession and ensure it's set in RetrofitClient
                val token = UserSession.token.value
                if (token != null) {
                    println("Token found: ${token.take(20)}...")
                    RetrofitClient.setAuthToken(token)
                } else {
                    println("No token found in UserSession")
                }

                // Verify token is set
                val currentToken = RetrofitClient.getAuthToken()
                println("Token in RetrofitClient: ${if (currentToken != null) "${currentToken.take(20)}..." else "null"}")

                // Make logout API call
                val response = apiService.logout(deviceId ?: "")
                println("Logout API Response Code: ${response.code()}")

                if (response.isSuccessful) {
                    val logoutResponse = response.body()
                    println("Logout Response: $logoutResponse")

                    if (logoutResponse?.status == true) {
                        // Clear session after successful logout
                        UserSession.clearSession(context)
                        _logoutState.value = Resource.Success(logoutResponse)
                        println("Logout successful, session cleared")
                    } else {
                        // Even if API returns false, we should still logout locally
                        UserSession.clearSession(context)
                        _logoutState.value = Resource.Success(
                            LogoutResponse(
                                status = true,
                                message = logoutResponse?.message ?: "Logged out successfully"
                            )
                        )
                        println("Logout API returned false, but cleared local session")
                    }
                } else {
                    // Even if API fails, clear local session
                    println("Logout API failed with code: ${response.code()}")
                    UserSession.clearSession(context)
                    _logoutState.value = Resource.Success(
                        LogoutResponse(
                            status = true,
                            message = "Logged out locally"
                        )
                    )
                }
            } catch (e: IOException) {
                // Network error - still clear local session
                println("Network error during logout: ${e.message}")
                UserSession.clearSession(context)
                _logoutState.value = Resource.Success(
                    LogoutResponse(
                        status = true,
                        message = "Logged out locally (Network issue)"
                    )
                )
            } catch (e: HttpException) {
                // HTTP error - still clear local session
                println("HTTP error during logout: ${e.message}")
                UserSession.clearSession(context)
                _logoutState.value = Resource.Success(
                    LogoutResponse(
                        status = true,
                        message = "Logged out locally"
                    )
                )
            } catch (e: Exception) {
                // Other errors
                println("Unexpected error during logout: ${e.message}")
                e.printStackTrace()
                _logoutState.value = Resource.Error(e.message ?: "Logout failed")
            } finally {
                _isLoggingOut.value = false
                println("=== Logout Process Completed ===")
            }
        }
    }

    fun resetLogoutState() {
        _logoutState.value = null
    }
}