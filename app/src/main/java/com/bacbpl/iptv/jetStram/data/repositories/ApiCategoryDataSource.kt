package com.bacbpl.iptv.jetStram.data.repositories

import com.bacbpl.iptv.jetStram.data.entities.MovieCategory
import com.bacbpl.iptv.jetStram.data.entities.toMovieCategory
import com.bacbpl.iptv.jetStram.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiCategoryDataSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getCategoriesFromApi(): List<MovieCategory> {
        return try {
            apiService.getCategories().map { it.toMovieCategory() }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun getCategoriesFlow(): Flow<List<MovieCategory>> = flow {
        emit(getCategoriesFromApi())
    }
}