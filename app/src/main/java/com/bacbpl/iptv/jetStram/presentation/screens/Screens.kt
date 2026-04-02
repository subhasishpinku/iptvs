///*
// * Copyright 2023 Google LLC
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * https://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package  com.bacbpl.iptv.jetStram.presentation.screens
//
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material.icons.outlined.AccountBalanceWallet
//import androidx.compose.ui.graphics.vector.ImageVector
//import com.bacbpl.iptv.jetStram.presentation.screens.categories.CategoryMovieListScreen
//import com.bacbpl.iptv.jetStram.presentation.screens.movies.MovieDetailsScreen
//import com.bacbpl.iptv.jetStram.presentation.screens.videoPlayer.VideoPlayerScreen
//
//
//enum class Screens(
//    private val args: List<String>? = null,
//    val isTabItem: Boolean = false,
//    val tabIcon: ImageVector? = null
//) {
//    Profile,
//    Home(isTabItem = true),
//    Categories(isTabItem = true),
//    Movies(isTabItem = true),
//    LiveTv(isTabItem = true),
//
//    Favourites(isTabItem = true),
//    News(isTabItem = true),
//    Search(isTabItem = true, tabIcon = Icons.Default.Search),
//    CategoryMovieList(listOf(CategoryMovieListScreen.CategoryIdBundleKey)),
//    MovieDetails(listOf(MovieDetailsScreen.MovieIdBundleKey)),
//    Dashboard,
//    VideoPlayer(listOf(VideoPlayerScreen.MovieIdBundleKey));
//
//    operator fun invoke(): String {
//        val argList = StringBuilder()
//        args?.let { nnArgs ->
//            nnArgs.forEach { arg -> argList.append("/{$arg}") }
//        }
//        return name + argList
//    }
//
//    fun withArgs(vararg args: Any): String {
//        val destination = StringBuilder()
//        args.forEach { arg -> destination.append("/$arg") }
//        return name + destination
//    }
//}
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

package com.bacbpl.iptv.jetStram.presentation.screens
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.bacbpl.iptv.R
import com.bacbpl.iptv.jetStram.presentation.screens.categories.CategoryMovieListScreen
import com.bacbpl.iptv.jetStram.presentation.screens.movies.MovieDetailsScreen
import com.bacbpl.iptv.jetStram.presentation.screens.videoPlayer.VideoPlayerScreen

enum class Screens(
    private val args: List<String>? = null,
    val isTabItem: Boolean = false,
    val tabIcon: ImageVector? = null,
    @StringRes val titleResId: Int? = null
) {
    Profile(
        isTabItem = false,
        titleResId = R.string.screen_profile
    ),
    Home(
        isTabItem = true,
        titleResId = R.string.screen_home
    ),
    Categories(
        isTabItem = true,
        titleResId = R.string.screen_categories
    ),
    Movies(
        isTabItem = true,
        titleResId = R.string.screen_movies
    ),
    LiveTv(
        isTabItem = true,
        titleResId = R.string.screen_live_tv
    ),
    Favourites(
        isTabItem = true,
        titleResId = R.string.screen_favourites
    ),
    News(
        isTabItem = true,
        titleResId = R.string.screen_news
    ),
    Search(
        isTabItem = true,
        tabIcon = Icons.Default.Search,
        titleResId = R.string.screen_search
    ),
    CategoryMovieList(
        args = listOf(CategoryMovieListScreen.CategoryIdBundleKey),
        isTabItem = false
    ),
    MovieDetails(
        args = listOf(MovieDetailsScreen.MovieIdBundleKey),
        isTabItem = false
    ),
    Dashboard(
        isTabItem = false
    ),
    VideoPlayer(
        args = listOf(VideoPlayerScreen.MovieIdBundleKey),
        isTabItem = false
    );

    operator fun invoke(): String {
        val argList = StringBuilder()
        args?.let { nnArgs ->
            nnArgs.forEach { arg -> argList.append("/{$arg}") }
        }
        return name + argList
    }

    fun withArgs(vararg args: Any): String {
        val destination = StringBuilder()
        args.forEach { arg -> destination.append("/$arg") }
        return name + destination
    }
}