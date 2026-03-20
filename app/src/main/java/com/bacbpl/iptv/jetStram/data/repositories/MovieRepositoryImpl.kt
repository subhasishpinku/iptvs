package com.bacbpl.iptv.jetStram.data.repositories

// Remove the problematic import
import com.bacbpl.iptv.jetStram.data.entities.*
import com.bacbpl.iptv.data.util.StringConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val apiMovieDataSource: ApiMovieDataSource,
    private val apiCategoryDataSource: ApiCategoryDataSource,
    private val apiWidgetDataSource: ApiWidgetDataSource,
    private val movieCastDataSource: MovieCastDataSource,
    private val movieCategoryDataSource: MovieCategoryDataSource,
) : MovieRepository {

    // Cache for widget items to use in movie details
    private var cachedWidgetItems: List<OttWidgetItem> = emptyList()

    init {
        // Load widgets on initialization
        loadWidgets()
    }

    private fun loadWidgets() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val widgets = apiWidgetDataSource.getWidgetsFromApi()
                cachedWidgetItems = widgets.flatMap { it.items }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun getCategoriesFromApi(): List<MovieCategory> {
        return apiCategoryDataSource.getCategoriesFromApi()
    }

    override fun getCategoriesFlow(): Flow<List<MovieCategory>> {
        return apiCategoryDataSource.getCategoriesFlow()
    }

    override fun getFeaturedMovies(): Flow<MovieList> =
        apiMovieDataSource.getFeaturedMovies()

    override fun getTrendingMovies(): Flow<MovieList> =
        apiMovieDataSource.getTrendingMovies()

    override fun getTop10Movies(): Flow<MovieList> =
        apiMovieDataSource.getTop10Movies()

    override fun getNowPlayingMovies(): Flow<MovieList> =
        apiMovieDataSource.getNowPlayingMovies()

    override fun getMovieCategories(): Flow<MovieCategoryList> = flow {
        val list = movieCategoryDataSource.getMovieCategoryList()
        emit(list)
    }

    override suspend fun getMovieCategoryDetails(categoryId: String): MovieCategoryDetails {
        val categoryList = movieCategoryDataSource.getMovieCategoryList()
        val category = categoryList.find { categoryId == it.id } ?: categoryList.first()

        val movieList = apiMovieDataSource.getAllMovies().shuffled().take(20)

        return MovieCategoryDetails(
            id = category.id,
            name = category.name,
            movies = movieList
        )
    }

    override suspend fun getMovieDetails(movieId: String): MovieDetails {
        // First try to find in regular movies
        val allMovies = apiMovieDataSource.getAllMovies()
        val movie = allMovies.find { it.id == movieId }

        // If not found in regular movies, try to find in widget items
        val widgetItem = cachedWidgetItems.find { it.id == movieId }

        return if (movie != null) {
            // Regular movie details
            val similarMovieList = allMovies.shuffled().take(3)
            val castList = movieCastDataSource.getMovieCastList()

            MovieDetails(
                id = movie.id,
                videoUri = movie.videoUri,
                subtitleUri = movie.subtitleUri,
                posterUri = movie.posterUri,
                name = movie.name,
                description = movie.description,
                pgRating = "PG-13",
                releaseDate = "2021 (US)",
                categories = listOf("Action", "Adventure", "Fantasy", "Comedy"),
                duration = "1h 59m",
                director = "Larry Page",
                screenplay = "Sundai Pichai",
                music = "Sergey Brin",
                castAndCrew = castList,
                status = "Released",
                originalLanguage = "English",
                budget = "$15M",
                revenue = "$40M",
                similarMovies = similarMovieList,
                reviewsAndRatings = listOf(
                    MovieReviewsAndRatings(
                        reviewerName = StringConstants.Movie.Reviewer.FreshTomatoes,
                        reviewerIconUri = StringConstants.Movie.Reviewer.FreshTomatoesImageUrl,
                        reviewCount = "22",
                        reviewRating = "89%"
                    ),
                    MovieReviewsAndRatings(
                        reviewerName = StringConstants.Movie.Reviewer.ReviewerName,
                        reviewerIconUri = StringConstants.Movie.Reviewer.ImageUrl,
                        reviewCount = StringConstants.Movie.Reviewer.DefaultCount,
                        reviewRating = StringConstants.Movie.Reviewer.DefaultRating
                    ),
                ),
            )
        } else if (widgetItem != null) {
            // Create movie details from widget item
            createMovieDetailsFromWidget(widgetItem)
        } else {
            // Fallback to first movie if not found
            val fallbackMovie = allMovies.firstOrNull() ?: createDefaultMovie()
            val similarMovieList = allMovies.shuffled().take(3)
            val castList = movieCastDataSource.getMovieCastList()

            MovieDetails(
                id = fallbackMovie.id,
                videoUri = fallbackMovie.videoUri,
                subtitleUri = fallbackMovie.subtitleUri,
                posterUri = fallbackMovie.posterUri,
                name = fallbackMovie.name,
                description = fallbackMovie.description,
                pgRating = "PG-13",
                releaseDate = "2021 (US)",
                categories = listOf("Action", "Adventure", "Fantasy", "Comedy"),
                duration = "1h 59m",
                director = "Larry Page",
                screenplay = "Sundai Pichai",
                music = "Sergey Brin",
                castAndCrew = castList,
                status = "Released",
                originalLanguage = "English",
                budget = "$15M",
                revenue = "$40M",
                similarMovies = similarMovieList,
                reviewsAndRatings = listOf(
                    MovieReviewsAndRatings(
                        reviewerName = StringConstants.Movie.Reviewer.FreshTomatoes,
                        reviewerIconUri = StringConstants.Movie.Reviewer.FreshTomatoesImageUrl,
                        reviewCount = "22",
                        reviewRating = "89%"
                    ),
                    MovieReviewsAndRatings(
                        reviewerName = StringConstants.Movie.Reviewer.ReviewerName,
                        reviewerIconUri = StringConstants.Movie.Reviewer.ImageUrl,
                        reviewCount = StringConstants.Movie.Reviewer.DefaultCount,
                        reviewRating = StringConstants.Movie.Reviewer.DefaultRating
                    ),
                ),
            )
        }
    }
    private fun createMovieDetailsFromWidget(item: OttWidgetItem): MovieDetails {
        // Create cast list from widget data with images
        val castList = if (item.casts.isNotEmpty()) {
            item.casts.mapIndexed { index, castInfo ->
                MovieCast(
                    id = "${item.id}_cast_$index",
                    characterName = "Actor",
                    realName = castInfo.name,
                    avatarUrl = castInfo.posterUrl ?: ""  // Use the cast image URL
                )
            }
        } else {
            // Use default cast if none provided
            runBlocking(Dispatchers.IO) {
                movieCastDataSource.getMovieCastList().take(5)
            }
        }

        // Add debug log to see cast images
        castList.forEach { cast ->
            android.util.Log.d("MovieDetails", "Cast: ${cast.realName}, Image: ${cast.avatarUrl}")
        }

        // Construct OTTplay URL with partner token if needed
        val ottplayUrl = item.ottplayUrl.ifEmpty { null }

        return MovieDetails(
            id = item.id,
            videoUri = "",
            subtitleUri = null,
            posterUri = item.posterUrl,
            name = item.title,
            description = "${item.language} • ${item.releaseYear} • ${item.genre}",
            pgRating = "UA",
            releaseDate = item.releaseYear.toString(),
            categories = item.genre.split(",").map { it.trim() },
            duration = "N/A",
            director = "Various",
            screenplay = "Various",
            music = "Various",
            castAndCrew = castList,
            status = "Released",
            originalLanguage = item.language,
            budget = "N/A",
            revenue = "N/A",
            similarMovies = emptyList(),
            reviewsAndRatings = listOf(
                MovieReviewsAndRatings(
                    reviewerName = StringConstants.Movie.Reviewer.FreshTomatoes,
                    reviewerIconUri = StringConstants.Movie.Reviewer.FreshTomatoesImageUrl,
                    reviewCount = "22",
                    reviewRating = if (item.rating > 0) "${(item.rating * 10).toInt()}%" else "N/A"
                ),
                MovieReviewsAndRatings(
                    reviewerName = StringConstants.Movie.Reviewer.ReviewerName,
                    reviewerIconUri = StringConstants.Movie.Reviewer.ImageUrl,
                    reviewCount = StringConstants.Movie.Reviewer.DefaultCount,
                    reviewRating = if (item.rating > 0) String.format("%.1f", item.rating) else "N/A"
                ),
            ),
            ottplayUrl = ottplayUrl
        )
    }
//    private fun createMovieDetailsFromWidget1(item: OttWidgetItem): MovieDetails {
//        // Create cast list from widget data
//        val castList = if (item.casts.isNotEmpty()) {
//            item.casts.mapIndexed { index, castName ->
//                MovieCast(
//                    id = "${item.id}_cast_$index",
//                    characterName = "Actor",
//                    realName = castName,
//                    avatarUrl = ""
//                )
//            }
//        } else {
//            // Use default cast if none provided
//            runBlocking(Dispatchers.IO) {
//                movieCastDataSource.getMovieCastList().take(5)
//            }
//        }
//
//        // Use the ottplayUrl from the widget item
//        val ottplayUrl = item.ottplayUrl.ifEmpty { null }
//
//        return MovieDetails(
//            id = item.id,
//            videoUri = "",
//            subtitleUri = null,
//            posterUri = item.posterUrl,
//            name = item.title,
//            description = "${item.language} • ${item.releaseYear} • ${item.genre}",
//            pgRating = "UA",
//            releaseDate = item.releaseYear.toString(),
//            categories = item.genre.split(",").map { it.trim() },
//            duration = "N/A",
//            director = "Various",
//            screenplay = "Various",
//            music = "Various",
//            castAndCrew = castList,
//            status = "Released",
//            originalLanguage = item.language,
//            budget = "N/A",
//            revenue = "N/A",
//            similarMovies = emptyList(),
//            reviewsAndRatings = listOf(
//                MovieReviewsAndRatings(
//                    reviewerName = StringConstants.Movie.Reviewer.FreshTomatoes,
//                    reviewerIconUri = StringConstants.Movie.Reviewer.FreshTomatoesImageUrl,
//                    reviewCount = "22",
//                    reviewRating = if (item.rating > 0) "${(item.rating * 10).toInt()}%" else "N/A"
//                ),
//                MovieReviewsAndRatings(
//                    reviewerName = StringConstants.Movie.Reviewer.ReviewerName,
//                    reviewerIconUri = StringConstants.Movie.Reviewer.ImageUrl,
//                    reviewCount = StringConstants.Movie.Reviewer.DefaultCount,
//                    reviewRating = if (item.rating > 0) String.format("%.1f", item.rating) else "N/A"
//                ),
//            ),
//            ottplayUrl = ottplayUrl
//        )
//    }

    private fun createDefaultMovie(): Movie {
        return Movie(
            id = "default",
            name = "Sample Movie",
            description = "Sample Description",
            posterUri = "",
            videoUri = "",
            subtitleUri = null
        )
    }

    override suspend fun searchMovies(query: String): MovieList {
        return apiMovieDataSource.searchMovies(query)
    }

    override fun getMoviesWithLongThumbnail(): Flow<MovieList> = flow {
        emit(apiMovieDataSource.getMoviesWithLongThumbnail())
    }

    override fun getMovies(): Flow<MovieList> = flow {
        emit(apiMovieDataSource.getAllMovies())
    }

    override fun getPopularFilmsThisWeek(): Flow<MovieList> =
        apiMovieDataSource.getPopularFilmThisWeek()

    override fun getTVShows(): Flow<MovieList> = flow {
        emit(emptyList())
    }

    override fun getBingeWatchDramas(): Flow<MovieList> = flow {
        emit(emptyList())
    }

    override fun getFavouriteMovies(): Flow<MovieList> =
        apiMovieDataSource.getFavoriteMovies()
}