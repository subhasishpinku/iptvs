package com.bacbpl.iptv.ui.activities.subscribescreen.data
import com.google.gson.annotations.SerializedName
data class SubscribePlanRequest(
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("plan_code")
    val planCode: Int
)

data class SubscribePlanResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("is_registered")
    val isRegistered: Boolean,
    @SerializedName("message")
    val message: String
)