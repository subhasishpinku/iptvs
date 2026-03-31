package com.bacbpl.iptv.data

import android.content.Context
import android.content.SharedPreferences
import com.bacbpl.iptv.data.api.RetrofitClient
import com.bacbpl.iptv.ui.activities.otpscreen.data.VerifyOtpResponse
import com.bacbpl.iptv.ui.activities.signupscreen.data.SignupResponse
import com.google.gson.Gson

class SharedPrefManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("IPTV_PREFS", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val KEY_USER_LOGIN = "user_login"
        private const val KEY_USER_SIGNUP = "user_signup"
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_MOBILE = "user_mobile"
    }

    fun saveUserLogin(loginResponse: VerifyOtpResponse) {
        try {
            println("=== Saving User Login ===")
            // Save as login response
            val json = gson.toJson(loginResponse)
            prefs.edit().putString(KEY_USER_LOGIN, json).apply()

            // Clear any signup data
            prefs.edit().remove(KEY_USER_SIGNUP).apply()

            // Save token and user details
            loginResponse.token?.let { token ->
                prefs.edit().putString(KEY_TOKEN, token).apply()
                // Set token in RetrofitClient
                RetrofitClient.setAuthToken(token)
                println("Token saved: ${token.take(20)}...")
            }

            loginResponse.user?.let { user ->
                prefs.edit().putInt(KEY_USER_ID, user.id).apply()
                prefs.edit().putString(KEY_USER_NAME, user.name).apply()
                prefs.edit().putString(KEY_USER_EMAIL, user.email).apply()
                prefs.edit().putString(KEY_USER_MOBILE, user.mobile).apply()
                println("User saved: ${user.name}, ID: ${user.id}")
            }
            prefs.edit().putBoolean(KEY_IS_LOGGED_IN, true).apply()

            println("=== User Login Saved Successfully ===")
        } catch (e: Exception) {
            println("Error saving user login: ${e.message}")
            e.printStackTrace()
        }
    }

    fun saveUserFromSignup(signupResponse: SignupResponse) {
        try {
            println("=== Saving Signup Data ===")
            println("Response: $signupResponse")
            println("User: ${signupResponse.user}")

            // Save signup response
            val signupJson = gson.toJson(signupResponse)
            prefs.edit().putString(KEY_USER_SIGNUP, signupJson).apply()

            // Also save as login response for compatibility
            val loginJson = gson.toJson(signupResponse)
            prefs.edit().putString(KEY_USER_LOGIN, loginJson).apply()

            // Save token and user details
            signupResponse.token?.let { token ->
                prefs.edit().putString(KEY_TOKEN, token).apply()
                // Set token in RetrofitClient
                RetrofitClient.setAuthToken(token)
                println("Token saved: ${token.take(20)}...")
            }

            prefs.edit().putInt(KEY_USER_ID, signupResponse.user.id).apply()
            prefs.edit().putString(KEY_USER_NAME, signupResponse.user.name).apply()
            prefs.edit().putString(KEY_USER_EMAIL, signupResponse.user.email).apply()
            prefs.edit().putString(KEY_USER_MOBILE, signupResponse.user.mobile).apply()
            prefs.edit().putBoolean(KEY_IS_LOGGED_IN, true).apply()

            println("=== Data Saved Successfully ===")
            println("Token: ${signupResponse.token?.take(20)}...")
            println("User ID: ${signupResponse.user.id}")
            println("User Name: ${signupResponse.user.name}")
            println("User Email: ${signupResponse.user.email}")
            println("User Mobile: ${signupResponse.user.mobile}")

        } catch (e: Exception) {
            println("=== Error Saving Data ===")
            e.printStackTrace()
        }
    }

    fun getSignupResponse(): SignupResponse? {
        return try {
            val json = prefs.getString(KEY_USER_SIGNUP, null)
            if (json.isNullOrEmpty()) {
                println("No signup data found")
                return null
            }
            val response = gson.fromJson(json, SignupResponse::class.java)
            println("Retrieved signup data: $response")
            response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getUserLogin(): VerifyOtpResponse? {
        val json = prefs.getString(KEY_USER_LOGIN, null) ?: return null
        return try {
            gson.fromJson(json, VerifyOtpResponse::class.java)
        } catch (e: Exception) {
            null
        }
    }

    fun getToken(): String? {
        val token = prefs.getString(KEY_TOKEN, null)
        println("=== Getting Token from SharedPref ===")
        println("Token present: ${token != null}")
        return token
    }

    fun getUserId(): Int {
        return prefs.getInt(KEY_USER_ID, -1)
    }

    fun getUserName(): String? {
        return prefs.getString(KEY_USER_NAME, null)
    }

    fun getUserEmail(): String? {
        return prefs.getString(KEY_USER_EMAIL, null)
    }

    fun getUserMobile(): String? {
        return prefs.getString(KEY_USER_MOBILE, null)
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun clearUserSession() {
        println("=== Clearing User Session ===")
        prefs.edit().clear().apply()
        // Clear token from RetrofitClient
        RetrofitClient.setAuthToken(null)
        println("Session cleared")
    }
}