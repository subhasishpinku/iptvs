// Update HomeScreeViewModel.kt
package com.bacbpl.iptv.jetStram.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bacbpl.iptv.jetStram.data.entities.MovieList
import com.bacbpl.iptv.jetStram.data.entities.OttWidget
import com.bacbpl.iptv.jetStram.data.repositories.MovieRepository
import com.bacbpl.iptv.jetStram.data.repositories.WidgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreeViewModel @Inject constructor(
    movieRepository: MovieRepository,
    private val widgetRepository: WidgetRepository
) : ViewModel() {

    private val _widgets = MutableStateFlow<List<OttWidget>>(emptyList())

    init {
        loadWidgets()
    }

    private fun loadWidgets() {
        viewModelScope.launch {
            try {
                val widgets = widgetRepository.getWidgets()
                _widgets.value = widgets
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val uiState: StateFlow<HomeScreenUiState> = combine(
        movieRepository.getFeaturedMovies(),
        movieRepository.getTrendingMovies(),
        movieRepository.getTop10Movies(),
        movieRepository.getNowPlayingMovies(),
        _widgets
    ) { featuredMovieList, trendingMovieList, top10MovieList, nowPlayingMovieList, widgets ->
        HomeScreenUiState.Ready(
            featuredMovieList = featuredMovieList,
            trendingMovieList = trendingMovieList,
            top10MovieList = top10MovieList,
            nowPlayingMovieList = nowPlayingMovieList,
            widgets = widgets
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeScreenUiState.Loading
    )
}

sealed interface HomeScreenUiState {
    data object Loading : HomeScreenUiState
    data object Error : HomeScreenUiState
    data class Ready(
        val featuredMovieList: MovieList,
        val trendingMovieList: MovieList,
        val top10MovieList: MovieList,
        val nowPlayingMovieList: MovieList,
        val widgets: List<OttWidget>
    ) : HomeScreenUiState
}