package com.bacbpl.iptv.jetStram.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bacbpl.iptv.jetStram.data.models.SubscriberDetailsResponse
import com.bacbpl.iptv.jetStram.data.models.UpdateProfileResponse
import com.bacbpl.iptv.jetStram.data.repositories.ProfileRepository
import com.bacbpl.iptv.jetStram.presentation.screens.profile.SubscriberInfo
import com.bacbpl.iptv.utils.UserSession
import com.bacbpl.iptv.ui.activities.signupscreen.data.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _subscriberDetailsState = MutableStateFlow<Resource<SubscriberDetailsResponse>>(Resource.Loading())
    val subscriberDetailsState: StateFlow<Resource<SubscriberDetailsResponse>> = _subscriberDetailsState.asStateFlow()

    private val _updateProfileState = MutableStateFlow<Resource<UpdateProfileResponse>?>(null)
    val updateProfileState: StateFlow<Resource<UpdateProfileResponse>?> = _updateProfileState.asStateFlow()

    private val _isUpdating = MutableStateFlow(false)
    val isUpdating: StateFlow<Boolean> = _isUpdating.asStateFlow()

    fun getSubscriberDetails(mobile: String, context: Context) {
        viewModelScope.launch {
            _subscriberDetailsState.value = Resource.Loading()
            try {
                val result = profileRepository.getSubscriberDetails(mobile)
                _subscriberDetailsState.value = result

                if (result is Resource.Success) {
                    val details = result.data
                    details?.let { subscriberDetails ->
                        val ottplayData = subscriberDetails.ottplayDetails?.data
                        val subscriber = subscriberDetails.subscriber

                        UserSession.saveSubscriberDetails(
                            context = context,
                            firstName = subscriber?.firstname ?: ottplayData?.name?.split(" ")?.firstOrNull() ?: "",
                            lastName = subscriber?.lastname ?: ottplayData?.name?.split(" ")?.drop(1)?.joinToString(" ") ?: "",
                            email = subscriber?.email ?: ottplayData?.email ?: "",
                            mobile = subscriber?.mobile ?: ottplayData?.phone ?: mobile,
                            address = subscriber?.address ?: "",
                            zone = subscriber?.zone ?: ottplayData?.zone ?: "",
                            serviceNumber = subscriber?.serviceNumber ?: ottplayData?.serviceNo ?: "",
                            stateCode = subscriber?.stateCode ?: "",
                            useAltLcoCode = subscriber?.useAltLcoCode ?: "0",
                            partnerReferenceId = ottplayData?.subCode ?: ""
                        )
                    }
                }
            } catch (e: Exception) {
                _subscriberDetailsState.value = Resource.Error(e.message ?: "Failed to fetch subscriber details")
            }
        }
    }

    fun updateSubscriberInfo(subscriberInfo: SubscriberInfo, context: Context) {
        viewModelScope.launch {
            _isUpdating.value = true
            profileRepository.updateProfile(
                mobile = subscriberInfo.phone,
                firstName = subscriberInfo.firstName,
                lastName = subscriberInfo.lastName,
                address = subscriberInfo.address,
                email = subscriberInfo.email,
                useAltLcoCode = subscriberInfo.useAltLcoCode,
                zone = subscriberInfo.zone,
                serviceNumber = subscriberInfo.serviceNumber,
                stateCode = subscriberInfo.stateCode
            ).collect { resource ->
                _updateProfileState.value = resource
                _isUpdating.value = false

                if (resource is Resource.Success) {
                    getSubscriberDetails(subscriberInfo.phone, context)
                }
            }
        }
    }

    fun resetUpdateState() {
        _updateProfileState.value = null
    }
}

// DO NOT define Resource class here - it's already defined in the repository package