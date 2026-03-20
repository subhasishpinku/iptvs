package com.bacbpl.iptv.ui.activities.otpscreen.data.repository


import com.bacbpl.iptv.data.api.RetrofitClient
import com.bacbpl.iptv.ui.activities.otpscreen.data.OtpResponse
import com.bacbpl.iptv.ui.activities.otpscreen.data.VerifyOtpRequest
import com.bacbpl.iptv.ui.activities.otpscreen.data.VerifyOtpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OtpRepository {
    private val api = RetrofitClient.apiService

    suspend fun sendOtp(mobile: String): Flow<Resource<OtpResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.sendOtp(mobile)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("Empty response"))
            } else {
                emit(Resource.Error("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Network error"))
        }
    }

    suspend fun verifyOtp(mobile: String, otp: String): Flow<Resource<VerifyOtpResponse>> = flow {
        emit(Resource.Loading())
        try {
            val request = VerifyOtpRequest(mobile, otp)
            val response = api.verifyOtp(request)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("Empty response"))
            } else {
                emit(Resource.Error("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Network error"))
        }
    }
}

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
    class Loading<T> : Resource<T>()
}