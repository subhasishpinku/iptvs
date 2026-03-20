package com.bacbpl.iptv.ui.activities.subscribescreen.data.repositories

import com.bacbpl.iptv.data.api.RetrofitClient
import com.bacbpl.iptv.ui.activities.subscribescreen.data.PlanResponse
import com.bacbpl.iptv.ui.activities.subscribescreen.data.SubscribePlanResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlanRepository @Inject constructor() {

    private val apiService = RetrofitClient.apiService

    suspend fun getPlansByDuration(duration: Int): Response<PlanResponse> {
        return apiService.getPlanListByPlanType(duration)
    }

    suspend fun subscribePlan(mobile: String, planCode: Int): Response<SubscribePlanResponse> {
        return apiService.subscribePlan(mobile, planCode)
    }

    companion object {
        // Duration constants
        const val DURATION_MONTHLY = 1
        const val DURATION_QUARTERLY = 3
        const val DURATION_HALF_YEARLY = 6

        // Plan type constants
        const val PLAN_TYPE_PREMIUM = "Premium"
        const val PLAN_TYPE_GOLD = "Gold"
        const val PLAN_TYPE_SILVER = "Silver"

        fun getPlanTypeFromName(planName: String): String {
            return when {
                planName.contains("Silver", ignoreCase = true) -> PLAN_TYPE_SILVER
                planName.contains("Gold", ignoreCase = true) -> PLAN_TYPE_GOLD
                planName.contains("Premium", ignoreCase = true) -> PLAN_TYPE_PREMIUM

                else -> ""
            }
        }

        fun getDurationString(duration: Int): String {
            return when (duration) {
                DURATION_MONTHLY -> "Monthly"
                DURATION_QUARTERLY -> "Quarterly"
                DURATION_HALF_YEARLY -> "Half-Yearly"
                else -> ""
            }
        }
    }
}