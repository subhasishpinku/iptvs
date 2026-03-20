package com.bacbpl.iptv.ui.activities.subscribescreen.data


import com.google.gson.annotations.SerializedName

data class PlanResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: List<Plan>?
)

data class Plan(
    @SerializedName("id")
    val id: Int,
    @SerializedName("ottplay_plan_code")
    val ottplayPlanCode: String,
    @SerializedName("ottplay_plan_name")
    val ottplayPlanName: String,
    @SerializedName("sys_plan_code")
    val sysPlanCode: Int,
    @SerializedName("sys_plan_name")
    val sysPlanName: String,
    @SerializedName("sys_plan_duration")
    val sysPlanDuration: Int,
    @SerializedName("sys_plan_duration_mode")
    val sysPlanDurationMode: String,
    @SerializedName("ottplay_plan_price")
    val ottplayPlanPrice: String?,
    @SerializedName("sys_plan_price")
    val sysPlanPrice: String,
    @SerializedName("ott_list")
    val ottList: List<OTT>,
    @SerializedName("status")
    val status: Int
)

data class OTT(
    @SerializedName("id")
    val id: Int,
    @SerializedName("ott_name")
    val ottName: String,
    @SerializedName("ott_code")
    val ottCode: String?,
    @SerializedName("ott_thumbnail")
    val ottThumbnail: String,
    @SerializedName("ott_image")
    val ottImage: String?,
    @SerializedName("status")
    val status: Int
)