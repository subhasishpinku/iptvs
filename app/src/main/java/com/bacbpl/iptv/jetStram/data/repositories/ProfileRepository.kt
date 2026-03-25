package com.bacbpl.iptv.jetStram.data.repositories

import com.bacbpl.iptv.jetStram.data.models.UpdateProfileResponse
import com.bacbpl.iptv.jetStram.data.network.ApiService
import com.bacbpl.iptv.ui.activities.signupscreen.data.repository.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun updateProfile(
        mobile: String,
        firstName: String,
        lastName: String,
        address: String,
        email: String,
        useAltLcoCode: String,
        zone: String,
        serviceNumber: String,
        stateCode: String
    ): Flow<Resource<UpdateProfileResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.updateProfile(
                mobile = mobile,
                firstName = firstName,
                lastName = lastName,
                address = address,
                email = email,
                useAltLcoCode = useAltLcoCode,
                zone = zone,
                serviceNumber = serviceNumber,
                stateCode = stateCode
            )
            if (response.success) {
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error(response.message))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Network error: ${e.message()}"))
        } catch (e: IOException) {
            emit(Resource.Error("Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error("An error occurred: ${e.message}"))
        }
    }
}