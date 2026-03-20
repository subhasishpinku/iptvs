package com.bacbpl.iptv.ui.activities.subscribescreen.viewmodels

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bacbpl.iptv.JetStreamActivity
import com.bacbpl.iptv.ui.activities.subscribescreen.data.Plan
import com.bacbpl.iptv.ui.activities.subscribescreen.data.SubscribePlanResponse
import com.bacbpl.iptv.ui.activities.subscribescreen.data.repositories.PlanRepository
import com.bacbpl.iptv.utils.ToastUtils
import kotlinx.coroutines.launch
import java.io.IOException

class PlanViewModel : ViewModel() {

    private val repository = PlanRepository()

    private val _monthlyPlans = MutableLiveData<List<Plan>>()
    val monthlyPlans: LiveData<List<Plan>> = _monthlyPlans

    private val _quarterlyPlans = MutableLiveData<List<Plan>>()
    val quarterlyPlans: LiveData<List<Plan>> = _quarterlyPlans

    private val _halfYearlyPlans = MutableLiveData<List<Plan>>()
    val halfYearlyPlans: LiveData<List<Plan>> = _halfYearlyPlans

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _selectedPlan = MutableLiveData<Plan?>()
    val selectedPlan: LiveData<Plan?> = _selectedPlan

    private val _subscribeResponse = MutableLiveData<SubscribePlanResponse?>()
    val subscribeResponse: LiveData<SubscribePlanResponse?> = _subscribeResponse

    private val _isSubscribing = MutableLiveData<Boolean>()
    val isSubscribing: LiveData<Boolean> = _isSubscribing

    // Events for UI
    private val _navigateToProfile = MutableLiveData<Boolean>()
    val navigateToProfile: LiveData<Boolean> = _navigateToProfile

    init {
        loadAllPlans()
    }

    fun loadAllPlans() {
        loadMonthlyPlans()
        loadQuarterlyPlans()
        loadHalfYearlyPlans()
    }

    fun loadMonthlyPlans() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getPlansByDuration(PlanRepository.DURATION_MONTHLY)
                if (response.isSuccessful) {
                    response.body()?.data?.let { plans ->
                        _monthlyPlans.value = plans
                    }
                } else {
                    _errorMessage.value = "Error: ${response.code()}"
                }
            } catch (e: IOException) {
                _errorMessage.value = "Network error: ${e.message}"
            } catch (e: Exception) {
                _errorMessage.value = "Unexpected error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadQuarterlyPlans() {
        viewModelScope.launch {
            try {
                val response = repository.getPlansByDuration(PlanRepository.DURATION_QUARTERLY)
                if (response.isSuccessful) {
                    response.body()?.data?.let { plans ->
                        _quarterlyPlans.value = plans
                    }
                }
            } catch (e: Exception) {
                // Handle error silently or log
            }
        }
    }

    fun loadHalfYearlyPlans() {
        viewModelScope.launch {
            try {
                val response = repository.getPlansByDuration(PlanRepository.DURATION_HALF_YEARLY)
                if (response.isSuccessful) {
                    response.body()?.data?.let { plans ->
                        _halfYearlyPlans.value = plans
                    }
                }
            } catch (e: Exception) {
                // Handle error silently or log
            }
        }
    }

    fun selectPlan(plan: Plan) {
        _selectedPlan.value = plan
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    fun clearSubscribeResponse() {
        _subscribeResponse.value = null
    }

    fun clearNavigateToProfile() {
        _navigateToProfile.value = false
    }

    fun subscribeToPlan(mobile: String, planCode: Int, context: Context) {
        viewModelScope.launch {
            _isSubscribing.value = true
            _subscribeResponse.value = null
            _errorMessage.value = null

            try {
                val response = repository.subscribePlan(mobile, planCode)

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _subscribeResponse.value = responseBody
                    println("Subscription Success: $responseBody")

                    // Check if subscriber is registered
                    if (responseBody?.isRegistered == true) {
                        // Success - show toast
                        ToastUtils.showSafeToast(context, "Subscription successful!")
                    } else {
                        // Subscriber doesn't exist - navigate to profile
                        _navigateToProfile.value = true
                        _errorMessage.value = responseBody?.message ?: "Subscriber does not exist"
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    println("Subscription Failed: ${response.code()} - $errorBody")

                    // Check if the error indicates subscriber doesn't exist
                    if (errorBody?.contains("Subscriber does not exists") == true) {
                        _navigateToProfile.value = true
                        _errorMessage.value = "Subscriber does not exist. Please register."
                    } else {
                        _errorMessage.value = "Subscription failed: ${response.code()}"
                        ToastUtils.showSafeToast(context, "Subscription failed: ${response.code()}")

                    }
                }
            } catch (e: IOException) {
                _errorMessage.value = "Network error: ${e.message}"
                println("Network Error: ${e.message}")
            } catch (e: Exception) {
                _errorMessage.value = "Unexpected error: ${e.message}"
                println("Unexpected Error: ${e.message}")
            } finally {
                _isSubscribing.value = false
            }
        }
    }
}