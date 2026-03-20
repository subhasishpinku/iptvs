package com.bacbpl.iptv.jetStram.data.repositories

import com.bacbpl.iptv.jetStram.data.entities.MovieCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val apiCategoryDataSource: ApiCategoryDataSource,
    private val movieCategoryDataSource: MovieCategoryDataSource  // Local JSON source
) {

    // Get from API first, fallback to local JSON
    suspend fun getCategories(): List<MovieCategory> {
        return try {
            val apiCategories = apiCategoryDataSource.getCategoriesFromApi()
            if (apiCategories.isNotEmpty()) {
                apiCategories
            } else {
                movieCategoryDataSource.getMovieCategoryList()
            }
        } catch (e: Exception) {
            // Fallback to local JSON on error
            movieCategoryDataSource.getMovieCategoryList()
        }
    }

    fun getCategoriesFlow(): Flow<List<MovieCategory>> = flow {
        emit(getCategories())
    }
}