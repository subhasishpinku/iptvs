package com.bacbpl.iptv.jetStram.data.network

import com.bacbpl.iptv.jetStram.data.entities.TvChannel
import com.bacbpl.iptv.jetStram.data.models.ApiMoviesResponse
import com.bacbpl.iptv.jetStram.data.models.MovieCategoriesResponseItem
import com.bacbpl.iptv.jetStram.data.models.OttWidgetResponse
import com.bacbpl.iptv.jetStram.data.models.UpdateProfileResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("api/movies/home")
    suspend fun getHomeMovies(): List<ApiMoviesResponse>

    @GET("api/categories/list")
    suspend fun getCategories(): List<MovieCategoriesResponseItem>

    @GET("api/ottplayWidgetList")
    suspend fun getOttWidgets(): OttWidgetResponse

    @POST("api/updateProfile")
    @FormUrlEncoded
    suspend fun updateProfile(
        @Field("mobile") mobile: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("address") address: String,
        @Field("email") email: String,
        @Field("use_alt_lco_code") useAltLcoCode: String,
        @Field("zone") zone: String,
        @Field("service_number") serviceNumber: String,
        @Field("state_code") stateCode: String
    ): UpdateProfileResponse


    @GET("api/getChannels")
    suspend fun getTvChannels(): List<TvChannel>
}