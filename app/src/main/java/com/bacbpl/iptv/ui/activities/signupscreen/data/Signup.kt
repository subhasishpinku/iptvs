package com.bacbpl.iptv.ui.activities.signupscreen.data

data class SignupRequest(
    val mobile: String,
    val name: String,
    val email: String,
    val password: String  // Added password field
)

data class SignupResponse(
    val status: Boolean,
    val token: String? = null,  // Made nullable
    val message: String,
    val user: SignupUser? = null  // Made nullable
)

data class SignupUser(
    val id: Int,
    val name: String,
    val mobile: String,
    val email: String
)