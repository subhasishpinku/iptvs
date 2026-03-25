package com.bacbpl.iptv.jetStram.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bacbpl.iptv.jetStram.data.models.UpdateProfileResponse
import com.bacbpl.iptv.jetStram.data.repositories.ProfileRepository
import com.bacbpl.iptv.jetStram.presentation.screens.profile.SubscriberInfo
import com.bacbpl.iptv.ui.activities.signupscreen.data.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _updateProfileState = MutableStateFlow<Resource<UpdateProfileResponse>?>(null)
    val updateProfileState: StateFlow<Resource<UpdateProfileResponse>?> = _updateProfileState.asStateFlow()

    private val _isUpdating = MutableStateFlow(false)
    val isUpdating: StateFlow<Boolean> = _isUpdating.asStateFlow()

    fun updateSubscriberInfo(subscriberInfo: SubscriberInfo) {
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
            }
        }
    }

    fun resetUpdateState() {
        _updateProfileState.value = null
    }
}