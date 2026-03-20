package com.bacbpl.iptv.ui.activities.signupscreen.data.repository

import com.bacbpl.iptv.data.api.RetrofitClient
import com.bacbpl.iptv.ui.activities.signupscreen.data.SignupResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class SignupRepository {
    private val api = RetrofitClient.apiService

    suspend fun signup(mobile: String, name: String, email: String): Flow<Resource<SignupResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.signup(mobile, name, email)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Resource.Success(body))
                } else {
                    emit(Resource.Error("Empty response from server"))
                }
            } else {
                val errorMessage = try {
                    response.errorBody()?.string() ?: "Unknown error"
                } catch (e: Exception) {
                    "Error parsing error response"
                }
                emit(Resource.Error("Error ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "Network error occurred"))
        }
    }.catch { e ->
        // This catch block handles any exceptions that might occur during flow collection
        e.printStackTrace()
        emit(Resource.Error(e.message ?: "Unexpected error occurred"))
    }
}

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String, val throwable: Throwable? = null) : Resource<T>()
}