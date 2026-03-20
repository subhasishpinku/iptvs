/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//
//package  com.bacbpl.iptv.jetStram.presentation.screens.categories
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.bacbpl.iptv.jetStram.data.entities.MovieCategoryList
//import com.bacbpl.iptv.jetStram.data.repositories.MovieRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import javax.inject.Inject
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.map
//import kotlinx.coroutines.flow.stateIn
//
//@HiltViewModel
//class CategoriesScreenViewModel @Inject constructor(
//    movieRepository: MovieRepository
//) : ViewModel() {
//
//    val uiState = movieRepository.getMovieCategories().map {
//        CategoriesScreenUiState.Ready(categoryList = it)
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5_000),
//        initialValue = CategoriesScreenUiState.Loading
//    )
//}
//
//sealed interface CategoriesScreenUiState {
//
//    data object Loading : CategoriesScreenUiState
//    data class Ready(val categoryList: MovieCategoryList) : CategoriesScreenUiState
//}

package com.bacbpl.iptv.jetStram.presentation.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bacbpl.iptv.jetStram.data.entities.MovieCategoryList
import com.bacbpl.iptv.jetStram.data.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesScreenViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoriesScreenUiState>(CategoriesScreenUiState.Loading)
    val uiState: StateFlow<CategoriesScreenUiState> = _uiState

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = CategoriesScreenUiState.Loading
            try {
                val categories = movieRepository.getCategoriesFromApi()
                _uiState.value = CategoriesScreenUiState.Ready(categoryList = categories)
            } catch (e: Exception) {
                _uiState.value = CategoriesScreenUiState.Error
            }
        }
    }
}

sealed interface CategoriesScreenUiState {
    data object Loading : CategoriesScreenUiState
    data object Error : CategoriesScreenUiState
    data class Ready(val categoryList: MovieCategoryList) : CategoriesScreenUiState
}