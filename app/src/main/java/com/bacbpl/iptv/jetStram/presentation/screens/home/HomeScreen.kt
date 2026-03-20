// Update your existing HomeScreen.kt
package com.bacbpl.iptv.jetStram.presentation.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bacbpl.iptv.jetStram.data.entities.Movie
import com.bacbpl.iptv.jetStram.data.entities.MovieList
import com.bacbpl.iptv.jetStram.data.entities.TvChannel
import com.bacbpl.iptv.data.util.StringConstants
import com.bacbpl.iptv.jetStram.data.entities.OttWidget
import com.bacbpl.iptv.jetStram.data.entities.OttWidgetItem
import com.bacbpl.iptv.jetStram.presentation.common.Error
import com.bacbpl.iptv.jetStram.presentation.common.Loading
import com.bacbpl.iptv.jetStram.presentation.common.MoviesRow
import com.bacbpl.iptv.jetStram.presentation.common.OttWidgetRow
import com.bacbpl.iptv.jetStram.presentation.common.TvChannelsRow
import com.bacbpl.iptv.jetStram.presentation.screens.dashboard.rememberChildPadding

@Composable
fun HomeScreen(
    onMovieClick: (movie: Movie) -> Unit,
    goToVideoPlayer: (movie: Movie) -> Unit,
    onTvChannelClick: (channel: TvChannel) -> Unit,
    onWidgetItemClick: (OttWidgetItem) -> Unit, // Add this
    onScroll: (isTopBarVisible: Boolean) -> Unit,
    isTopBarVisible: Boolean,
    homeScreeViewModel: HomeScreeViewModel = hiltViewModel(),
    tvChannelViewModel: TvChannelViewModel = hiltViewModel(),
) {
    val uiState by homeScreeViewModel.uiState.collectAsStateWithLifecycle()
    val tvChannels by tvChannelViewModel.channels.collectAsStateWithLifecycle()

    when (val s = uiState) {
        is HomeScreenUiState.Ready -> {
            Catalog(
                featuredMovies = s.featuredMovieList,
                trendingMovies = s.trendingMovieList,
                top10Movies = s.top10MovieList,
                nowPlayingMovies = s.nowPlayingMovieList,
                widgets = s.widgets, // Pass widgets
                tvChannels = tvChannels,
                onMovieClick = onMovieClick,
                onTvChannelClick = onTvChannelClick,
                onWidgetItemClick = onWidgetItemClick, // Pass click handler
                onScroll = onScroll,
                goToVideoPlayer = goToVideoPlayer,
                isTopBarVisible = isTopBarVisible,
                modifier = Modifier.fillMaxSize(),
            )
        }

        is HomeScreenUiState.Loading -> Loading(modifier = Modifier.fillMaxSize())
        is HomeScreenUiState.Error -> Error(modifier = Modifier.fillMaxSize())
    }
}
// Update the Catalog function in HomeScreen.kt
@Composable
private fun Catalog(
    featuredMovies: MovieList,
    trendingMovies: MovieList,
    top10Movies: MovieList,
    nowPlayingMovies: MovieList,
    tvChannels: List<TvChannel>,
    widgets: List<OttWidget>, // Add this parameter
    onMovieClick: (movie: Movie) -> Unit,
    onTvChannelClick: (channel: TvChannel) -> Unit,
    onWidgetItemClick: (OttWidgetItem) -> Unit, // Add this
    onScroll: (isTopBarVisible: Boolean) -> Unit,
    goToVideoPlayer: (movie: Movie) -> Unit,
    modifier: Modifier = Modifier,
    isTopBarVisible: Boolean = true,
) {
    val lazyListState = rememberLazyListState()
    val childPadding = rememberChildPadding()
    var immersiveListHasFocus by remember { mutableStateOf(false) }

    val shouldShowTopBar by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0 &&
                    lazyListState.firstVisibleItemScrollOffset < 300
        }
    }

    LaunchedEffect(shouldShowTopBar) {
        onScroll(shouldShowTopBar)
    }

    LaunchedEffect(isTopBarVisible) {
        if (isTopBarVisible) lazyListState.animateScrollToItem(0)
    }

    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(bottom = 108.dp),
        modifier = modifier,
    ) {
        // Featured Movies Carousel
        item(contentType = "FeaturedMoviesCarousel") {
            FeaturedMoviesCarousel(
                movies = featuredMovies,
                padding = childPadding,
                goToVideoPlayer = goToVideoPlayer,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(324.dp)
            )
        }
        // Live TV Channels Row
        if (tvChannels.isNotEmpty()) {
            item(contentType = "TvChannelsRow") {
                TvChannelsRow(
                    title = "Live TV Channels",
                    channels = tvChannels.take(10),
                    onChannelSelected = onTvChannelClick,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

        // OTT Widgets - Dynamic rows from API
        widgets.forEach { widget ->
            item(contentType = widget.type) {
                OttWidgetRow(
                    widget = widget,
                    onItemClick = onWidgetItemClick,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }



        // Trending Movies
        item(contentType = "MoviesRow") {
            MoviesRow(
                modifier = Modifier.padding(top = 16.dp),
                movieList = trendingMovies,
                title = StringConstants.Composable.HomeScreenTrendingTitle,
                onMovieSelected = onMovieClick
            )
        }

        // Top 10 Movies
        item(contentType = "Top10MoviesList") {
            Top10MoviesList(
                movieList = top10Movies,
                onMovieClick = onMovieClick,
                modifier = Modifier.onFocusChanged {
                    immersiveListHasFocus = it.hasFocus
                },
            )
        }
        // Add More TV Channels by Category
        if (tvChannels.size > 10) {
//            item(contentType = "TvChannelsRowBangla") {
//                val banglaChannels = tvChannels.filter { it.category == "Bangla" }
//                if (banglaChannels.isNotEmpty()) {
//                    TvChannelsRow(
//                        title = "Bangla TV Channels",
//                        channels = banglaChannels,
//                        onChannelSelected = onTvChannelClick,
//                        modifier = Modifier.padding(top = 16.dp)
//                    )
//                }
//            }

//            item(contentType = "TvChannelsRowNews") {
//                val newsChannels = tvChannels.filter { it.category == "News" }
//                if (newsChannels.isNotEmpty()) {
//                    TvChannelsRow(
//                        title = "News Channels",
//                        channels = newsChannels,
//                        onChannelSelected = onTvChannelClick,
//                        modifier = Modifier.padding(top = 16.dp)
//                    )
//                }
//            }
//
//            item(contentType = "TvChannelsRowMovies") {
//                val movieChannels = tvChannels.filter { it.category == "Movies" }
//                if (movieChannels.isNotEmpty()) {
//                    TvChannelsRow(
//                        title = "Movie Channels",
//                        channels = movieChannels,
//                        onChannelSelected = onTvChannelClick,
//                        modifier = Modifier.padding(top = 16.dp)
//                    )
//                }
//            }
        }
        // Now Playing Movies
        item(contentType = "MoviesRow") {
            MoviesRow(
                modifier = Modifier.padding(top = 16.dp),
                movieList = nowPlayingMovies,
                title = StringConstants.Composable.HomeScreenNowPlayingMoviesTitle,
                onMovieSelected = onMovieClick
            )
        }
    }
}
