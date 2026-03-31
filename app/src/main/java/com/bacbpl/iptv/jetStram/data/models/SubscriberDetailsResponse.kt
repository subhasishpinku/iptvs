package com.bacbpl.iptv.jetStram.data.models

import com.google.gson.annotations.SerializedName

data class SubscriberDetailsResponse(
    @SerializedName("subscriber")
    val subscriber: Subscriber?,
    @SerializedName("ottplay_details")
    val ottplayDetails: OttplayDetails?
)

data class Subscriber(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("firstname")
    val firstname: String?,
    @SerializedName("lastname")
    val lastname: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("mobile")
    val mobile: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("zone")
    val zone: String?,
    @SerializedName("service_number")
    val serviceNumber: String?,
    @SerializedName("state_code")
    val stateCode: String?,
    @SerializedName("use_alt_lco_code")
    val useAltLcoCode: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("account_status")
    val accountStatus: String?,
    @SerializedName("plan_ott_id")
    val planOttId: Int?
)

data class OttplayDetails(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: OttplayData?
)

data class OttplayData(
    @SerializedName("sub_code")
    val subCode: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("zone")
    val zone: String?,
    @SerializedName("service_no")
    val serviceNo: String?,
    @SerializedName("operator_name")
    val operatorName: String?,
    @SerializedName("operator_code")
    val operatorCode: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("active_plan")
    val activePlan: Boolean?,
    @SerializedName("subs_udf_1")
    val subsUdf1: String?,
    @SerializedName("subs_udf_2")
    val subsUdf2: String?,
    @SerializedName("subs_udf_3")
    val subsUdf3: String?,
    @SerializedName("auto_renew")
    val autoRenew: Int?,
    @SerializedName("sub_creation_date")
    val subCreationDate: String?,
    @SerializedName("plan_details")
    val planDetails: List<PlanDetail>?,
    @SerializedName("plan_details_2")
    val planDetails2: List<PlanDetail>?
)

data class PlanDetail(
    @SerializedName("name")
    val name: String?,
    @SerializedName("plan_code")
    val planCode: String?,
    @SerializedName("interval")
    val interval: Int?,
    @SerializedName("interval_unit")
    val intervalUnit: String?,
    @SerializedName("activation_date")
    val activationDate: String?,
    @SerializedName("expiry_date")
    val expiryDate: String?,
    @SerializedName("plan_udf_1")
    val planUdf1: String?,
    @SerializedName("plan_udf_2")
    val planUdf2: String?,
    @SerializedName("plan_udf_3")
    val planUdf3: String?
)