package com.bacbpl.iptv.utils

import android.content.Context
import com.bacbpl.iptv.data.SharedPrefManager
import com.bacbpl.iptv.ui.activities.otpscreen.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object UserSession {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName.asStateFlow()

    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail.asStateFlow()

    private val _userMobile = MutableStateFlow<String?>(null)
    val userMobile: StateFlow<String?> = _userMobile.asStateFlow()

    private val _userId = MutableStateFlow(-1)
    val userId: StateFlow<Int> = _userId.asStateFlow()

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token.asStateFlow()

    fun updateSession(context: Context) {
        try {
            val sharedPrefManager = SharedPrefManager(context)

            // Update login state
            _isLoggedIn.value = sharedPrefManager.isLoggedIn()

            if (_isLoggedIn.value) {
                // Get user details from SharedPrefManager
                _userName.value = sharedPrefManager.getUserName()
                _userEmail.value = sharedPrefManager.getUserEmail()
                _userMobile.value = sharedPrefManager.getUserMobile()
                _userId.value = sharedPrefManager.getUserId()
                _token.value = sharedPrefManager.getToken()

                println("=== UserSession Updated ===")
                println("Logged In: ${_isLoggedIn.value}")
                println("Name: ${_userName.value}")
                println("Email: ${_userEmail.value}")
                println("Mobile: ${_userMobile.value}")
                println("User ID: ${_userId.value}")
                println("Token: ${_token.value}")
            } else {
                clearSessionData()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            clearSessionData()
        }
    }

    private fun clearSessionData() {
        _isLoggedIn.value = false
        _userName.value = null
        _userEmail.value = null
        _userMobile.value = null
        _userId.value = -1
        _token.value = null
    }

    fun clearSession(context: Context) {
        try {
            SharedPrefManager(context).clearUserSession()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            clearSessionData()
        }
    }
}

// Extension functions for easy access
fun Context.getUserName(): String? = SharedPrefManager(this).getUserName()
fun Context.getUserEmail(): String? = SharedPrefManager(this).getUserEmail()
fun Context.getUserMobile(): String? = SharedPrefManager(this).getUserMobile()
fun Context.getUserId(): Int = SharedPrefManager(this).getUserId()
fun Context.getToken(): String? = SharedPrefManager(this).getToken()