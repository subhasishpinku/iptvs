package com.bacbpl.iptv.jetStram.data.repositories


import android.util.Log
import com.bacbpl.iptv.jetStram.data.entities.Movie
import com.bacbpl.iptv.jetStram.data.entities.ThumbnailType
import com.bacbpl.iptv.jetStram.data.models.ApiMoviesResponse
import com.bacbpl.iptv.jetStram.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiMovieDataSource @Inject constructor(
    private val apiService: ApiService
) {

    private suspend fun fetchMoviesFromApi1(): List<ApiMoviesResponse> {
        return try {
            apiService.getHomeMovies()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private suspend fun fetchMoviesFromApi(): List<ApiMoviesResponse> {
        return try {
            val response = apiService.getHomeMovies()
            Log.d("API_SUCCESS", "Movies fetched: ${response.size}")
            response
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error fetching movies: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getAllMovies(): List<Movie> {
        return fetchMoviesFromApi().map { it.toMovie() }
    }

    suspend fun getMoviesWithLongThumbnail(): List<Movie> {
        return fetchMoviesFromApi().map { it.toMovie(ThumbnailType.Long) }
    }

    fun getFeaturedMovies(): Flow<List<Movie>> = flow {
        val movies = fetchMoviesFromApi().map { it.toMovie(ThumbnailType.Long) }
        val featured = movies.filterIndexed { index, _ ->
            listOf(1, 3, 5, 7, 9).contains(index)
        }
        emit(featured)
    }

    fun getTrendingMovies(): Flow<List<Movie>> = flow {
        val movies = fetchMoviesFromApi().map { it.toMovie() }
        emit(movies.take(10))
    }

    //
    fun getTop10Movies(): Flow<List<Movie>> = flow {
        val movies = fetchMoviesFromApi().map { it.toMovie(ThumbnailType.Long) }
        emit(if (movies.size > 20) {
            movies.subList(20, minOf(30, movies.size))
        } else {
            emptyList()  // Empty list return করবে, null না
        })
    }
    fun getNowPlayingMovies(): Flow<List<Movie>> = flow {
        val movies = fetchMoviesFromApi().map { it.toMovie() }
        emit(movies.take(10))
    }

    fun getPopularFilmThisWeek(): Flow<List<Movie>> = flow {
        val movies = fetchMoviesFromApi().map { it.toMovie() }
        emit(if (movies.size > 11) movies.subList(11, minOf(20, movies.size)) else emptyList())
    }

    fun getFavoriteMovies(): Flow<List<Movie>> = flow {
        val movies = fetchMoviesFromApi().map { it.toMovie() }
        emit(movies.take(28))
    }

    suspend fun searchMovies(query: String): List<Movie> {
        val movies = fetchMoviesFromApi().map { it.toMovie() }
        return movies.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
        }
    }

    suspend fun getMovieById(movieId: String): Movie? {
        val movies = fetchMoviesFromApi().map { it.toMovie() }
        return movies.find { it.id == movieId }
    }

    private fun ApiMoviesResponse.toMovie(thumbnailType: ThumbnailType = ThumbnailType.Standard): Movie {
        val thumbnail = when (thumbnailType) {
            ThumbnailType.Standard -> image_2_3
            ThumbnailType.Long -> image_16_9
        }
        return Movie(
            id = id.toString(),
            videoUri = videoUri,
            subtitleUri = subtitleUri,
            posterUri = thumbnail,
            name = title,
            description = plot
        )
    }
}