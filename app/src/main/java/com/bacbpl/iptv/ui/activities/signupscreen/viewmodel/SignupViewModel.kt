package com.bacbpl.iptv.ui.activities.signupscreen.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bacbpl.iptv.data.SharedPrefManager
import com.bacbpl.iptv.ui.activities.signupscreen.data.SignupResponse
import com.bacbpl.iptv.ui.activities.signupscreen.data.repository.Resource
import com.bacbpl.iptv.ui.activities.signupscreen.data.repository.SignupRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SignupViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SignupRepository()
    private val sharedPrefManager = SharedPrefManager(application.applicationContext)

    private val _signupState = MutableStateFlow<SignupUiState>(SignupUiState.Initial)
    val signupState: StateFlow<SignupUiState> = _signupState.asStateFlow()

    fun signup(mobile: String, name: String, email: String) {
        viewModelScope.launch {
            _signupState.value = SignupUiState.Loading

            try {
                repository.signup(mobile, name, email).collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            _signupState.value = SignupUiState.Loading
                        }
                        is Resource.Success -> {
                            try {
                                // Save the signup response to SharedPreferences
                                resource.data?.let { response ->
                                    if (response.status) {
                                        println("=== Signup Success ===")
                                        println("Response: $response")
                                        println("User: ${response.user}")
                                        saveUserData(response)
                                        _signupState.value = SignupUiState.Success(response)
                                    } else {
                                        _signupState.value = SignupUiState.Error(response.message ?: "Registration failed")
                                    }
                                } ?: run {
                                    _signupState.value = SignupUiState.Error("No data received")
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                _signupState.value = SignupUiState.Error("Error saving user data: ${e.message}")
                            }
                        }
                        is Resource.Error -> {
                            _signupState.value = SignupUiState.Error(resource.message)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _signupState.value = SignupUiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    private fun saveUserData(response: SignupResponse) {
        try {
            sharedPrefManager.saveUserFromSignup(response)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e // Re-throw to be caught in the calling function
        }
    }

    fun resetState() {
        _signupState.value = SignupUiState.Initial
    }
}

// UI States
sealed class SignupUiState {
    object Initial : SignupUiState()
    object Loading : SignupUiState()
    data class Success(val response: SignupResponse) : SignupUiState()
    data class Error(val message: String) : SignupUiState()
}