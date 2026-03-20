package com.bacbpl.iptv.ui.activities.signupscreen.data

data class SignupRequest(
    val mobile: String,
    val name: String,
    val email: String
)

data class SignupResponse(
    val status: Boolean,
    val token: String,
    val message: String,
    val user: SignupUser
)

data class SignupUser(
    val id: Int,
    val name: String,
    val mobile: String,
    val email: String

)