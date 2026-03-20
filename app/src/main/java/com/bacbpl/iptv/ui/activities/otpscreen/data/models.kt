package com.bacbpl.iptv.ui.activities.otpscreen.data


data class OtpRequest(
    val mobile: String
)

data class OtpResponse(
    val status: Boolean,
    val message: String,
    val otp: Int // 4-digit OTP from your API
)

data class VerifyOtpRequest(
    val mobile: String,
    val otp: String // 4-digit OTP
)

data class VerifyOtpResponse(
    val status: Boolean,
    val message: String,
    val token: String? = null,
    val token_type: String? = null,
    val user: User? = null
)

data class User(
    val id: Int,
    val name: String,
    val mobile: String,
    val email: String
)