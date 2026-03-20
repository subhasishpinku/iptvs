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

package com.bacbpl.iptv.jetStram.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bacbpl.iptv.jetStram.presentation.screens.Screens
import com.bacbpl.iptv.jetStram.presentation.screens.categories.CategoryMovieListScreen
import com.bacbpl.iptv.jetStram.presentation.screens.dashboard.DashboardScreen
import com.bacbpl.iptv.jetStram.presentation.screens.movies.MovieDetailsScreen
import com.bacbpl.iptv.jetStram.presentation.screens.profile.ProfileScreen  // Add this import
import com.bacbpl.iptv.jetStram.presentation.screens.videoPlayer.VideoPlayerScreen
import kotlinx.coroutines.delay

@Composable
fun App(
    onBackPressed: () -> Unit,
    navigateToProfile: Boolean = false
) {

    val navController = rememberNavController()
    var isComingBackFromDifferentScreen by remember { mutableStateOf(false) }

    LaunchedEffect(navigateToProfile) {
        if (navigateToProfile) {
            // Small delay to ensure the graph is ready
            delay(100)
            navController.navigate(Screens.Profile())
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screens.Dashboard(),
        builder = {
            // Add Profile screen first
            composable(route = Screens.Profile()) {
                ProfileScreen()
            }

            composable(
                route = Screens.CategoryMovieList(),
                arguments = listOf(
                    navArgument(CategoryMovieListScreen.CategoryIdBundleKey) {
                        type = NavType.StringType
                    }
                )
            ) {
                CategoryMovieListScreen(
                    onBackPressed = {
                        if (navController.navigateUp()) {
                            isComingBackFromDifferentScreen = true
                        }
                    },
                    onMovieSelected = { movie ->
                        navController.navigate(
                            Screens.MovieDetails.withArgs(movie.id)
                        )
                    }
                )
            }

            composable(
                route = Screens.MovieDetails(),
                arguments = listOf(
                    navArgument(MovieDetailsScreen.MovieIdBundleKey) {
                        type = NavType.StringType
                    }
                )
            ) {
                MovieDetailsScreen(
                    goToMoviePlayer = { movie ->
                        navController.navigate(
                            Screens.VideoPlayer.withArgs(movie.id)
                        )
                    },
                    refreshScreenWithNewMovie = { movie ->
                        navController.navigate(
                            Screens.MovieDetails.withArgs(movie.id)
                        ) {
                            popUpTo(Screens.MovieDetails()) {
                                inclusive = true
                            }
                        }
                    },
                    onBackPressed = {
                        if (navController.navigateUp()) {
                            isComingBackFromDifferentScreen = true
                        }
                    }
                )
            }

            composable(route = Screens.Dashboard()) {
                DashboardScreen(
                    openCategoryMovieList = { categoryId ->
                        navController.navigate(
                            Screens.CategoryMovieList.withArgs(categoryId)
                        )
                    },
                    openMovieDetailsScreen = { movieId ->
                        navController.navigate(
                            Screens.MovieDetails.withArgs(movieId)
                        )
                    },
                    openVideoPlayer = { movie ->
                        navController.navigate(
                            Screens.VideoPlayer.withArgs(movie.id)
                        )
                    },
                    onBackPressed = onBackPressed,
                    isComingBackFromDifferentScreen = isComingBackFromDifferentScreen,
                    resetIsComingBackFromDifferentScreen = {
                        isComingBackFromDifferentScreen = false
                    }
                )
            }

            // VideoPlayer screen
            composable(
                route = Screens.VideoPlayer.withArgs("{${VideoPlayerScreen.MovieIdBundleKey}}"),
                arguments = listOf(
                    navArgument(VideoPlayerScreen.MovieIdBundleKey) {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                VideoPlayerScreen(
                    onBackPressed = {
                        if (navController.navigateUp()) {
                            isComingBackFromDifferentScreen = true
                        }
                    }
                )
            }
        }
    )
}