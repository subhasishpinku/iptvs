package com.bacbpl.iptv.jetStram.data.network

import com.bacbpl.iptv.jetStram.data.models.ApiMoviesResponse
import com.bacbpl.iptv.jetStram.data.models.MovieCategoriesResponseItem
import com.bacbpl.iptv.jetStram.data.models.OttWidgetResponse
import retrofit2.http.GET

interface ApiService {
    @GET("api/movies/home")
    suspend fun getHomeMovies(): List<ApiMoviesResponse>

    @GET("api/categories/list")
    suspend fun getCategories(): List<MovieCategoriesResponseItem>

    @GET("api/ottplayWidgetList")
    suspend fun getOttWidgets(): OttWidgetResponse
}