package com.bacbpl.iptv.jetStram.presentation.screens.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bacbpl.iptv.jetStram.data.entities.Movie
import com.bacbpl.iptv.jetStram.data.entities.OttWidgetItem
import com.bacbpl.iptv.jetStram.presentation.screens.News.NewsScreen
import com.bacbpl.iptv.jetStram.presentation.screens.Screens
import com.bacbpl.iptv.jetStram.presentation.screens.categories.CategoriesScreen
import com.bacbpl.iptv.jetStram.presentation.screens.favourites.FavouritesScreen
import com.bacbpl.iptv.jetStram.presentation.screens.home.HomeScreen
import com.bacbpl.iptv.jetStram.presentation.screens.movies.MoviesScreen
import com.bacbpl.iptv.jetStram.presentation.screens.profile.ProfileScreen
import com.bacbpl.iptv.jetStram.presentation.screens.search.SearchScreen
import com.bacbpl.iptv.jetStram.presentation.screens.shows.ShowsScreen
import com.bacbpl.iptv.jetStram.presentation.utils.Padding
import com.bacbpl.iptv.jetStram.presentation.screens.home.TvChannelViewModel
import com.bacbpl.iptv.jetStram.presentation.screens.player.TvPlayer

val ParentPadding = PaddingValues(vertical = 16.dp, horizontal = 58.dp)

@Composable
fun rememberChildPadding(direction: LayoutDirection = LocalLayoutDirection.current): Padding {
    return remember {
        Padding(
            start = ParentPadding.calculateStartPadding(direction) + 8.dp,
            top = ParentPadding.calculateTopPadding(),
            end = ParentPadding.calculateEndPadding(direction) + 8.dp,
            bottom = ParentPadding.calculateBottomPadding()
        )
    }
}

@Composable
fun DashboardScreen(
    openCategoryMovieList: (categoryId: String) -> Unit,
    openMovieDetailsScreen: (movieId: String) -> Unit,
    openVideoPlayer: (Movie) -> Unit,
    isComingBackFromDifferentScreen: Boolean,
    resetIsComingBackFromDifferentScreen: () -> Unit,
    onBackPressed: () -> Unit
) {
    val density = LocalDensity.current
    val focusManager = LocalFocusManager.current
    val navController = rememberNavController()

    var isTopBarVisible by remember { mutableStateOf(true) }
    var isTopBarFocused by remember { mutableStateOf(false) }

    var currentDestination: String? by remember { mutableStateOf(null) }
    val currentTopBarSelectedTabIndex by remember(currentDestination) {
        derivedStateOf {
            currentDestination?.let { TopBarTabs.indexOf(Screens.valueOf(it)) } ?: 0
        }
    }

    DisposableEffect(Unit) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            currentDestination = destination.route
        }

        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    BackPressHandledArea(
        onBackPressed = {
            if (!isTopBarVisible) {
                isTopBarVisible = true
                TopBarFocusRequesters[currentTopBarSelectedTabIndex + 1].requestFocus()
            } else if (currentTopBarSelectedTabIndex == 0) onBackPressed()
            else if (!isTopBarFocused) {
                TopBarFocusRequesters[currentTopBarSelectedTabIndex + 1].requestFocus()
            } else TopBarFocusRequesters[1].requestFocus()
        }
    ) {
        var wasTopBarFocusRequestedBefore by rememberSaveable { mutableStateOf(false) }

        var topBarHeightPx: Int by rememberSaveable { mutableIntStateOf(0) }

        val topBarYOffsetPx by animateIntAsState(
            targetValue = if (isTopBarVisible) 0 else -topBarHeightPx,
            animationSpec = tween(),
            label = "",
            finishedListener = {
                if (it == -topBarHeightPx && isComingBackFromDifferentScreen) {
                    focusManager.moveFocus(FocusDirection.Down)
                    resetIsComingBackFromDifferentScreen()
                }
            }
        )

        val navHostTopPaddingDp by animateDpAsState(
            targetValue = if (isTopBarVisible) with(density) { topBarHeightPx.toDp() } else 0.dp,
            animationSpec = tween(),
            label = "",
        )

        LaunchedEffect(Unit) {
            if (!wasTopBarFocusRequestedBefore) {
                TopBarFocusRequesters[currentTopBarSelectedTabIndex + 1].requestFocus()
                wasTopBarFocusRequestedBefore = true
            }
        }

        DashboardTopBar(
            modifier = Modifier
                .offset { IntOffset(x = 0, y = topBarYOffsetPx) }
                .onSizeChanged { topBarHeightPx = it.height }
                .onFocusChanged { isTopBarFocused = it.hasFocus }
                .padding(
                    horizontal = ParentPadding.calculateStartPadding(
                        LocalLayoutDirection.current
                    ) + 8.dp
                )
                .padding(
                    top = ParentPadding.calculateTopPadding(),
                    bottom = ParentPadding.calculateBottomPadding()
                ),
            selectedTabIndex = currentTopBarSelectedTabIndex,
        ) { screen ->
            val targetRoute = screen()
            if (currentDestination != targetRoute) {
                navController.navigate(targetRoute) {
                    if (screen == TopBarTabs[0]) popUpTo(TopBarTabs[0].invoke())
                    launchSingleTop = true
                }
            }
        }

        Body(
            openCategoryMovieList = openCategoryMovieList,
            openMovieDetailsScreen = openMovieDetailsScreen,
            openVideoPlayer = openVideoPlayer,
            updateTopBarVisibility = { isTopBarVisible = it },
            isTopBarVisible = isTopBarVisible,
            navController = navController,
            modifier = Modifier.offset(y = navHostTopPaddingDp),
        )
    }
}

@Composable
private fun BackPressHandledArea(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) =
    Box(
        modifier = Modifier
            .onPreviewKeyEvent {
                if (it.key == Key.Back && it.type == KeyEventType.KeyUp) {
                    onBackPressed()
                    true
                } else {
                    false
                }
            }
            .then(modifier),
        content = content
    )

@Composable
private fun Body(
    openCategoryMovieList: (categoryId: String) -> Unit,
    openMovieDetailsScreen: (movieId: String) -> Unit,
    openVideoPlayer: (Movie) -> Unit,
    updateTopBarVisibility: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    isTopBarVisible: Boolean = true,
    tvChannelViewModel: TvChannelViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.Home(),
    ) {
        composable(Screens.Profile()) {
            ProfileScreen()
        }
        composable(Screens.Home()) {
            HomeScreen(
                onMovieClick = { selectedMovie ->
                    openMovieDetailsScreen(selectedMovie.id)
                },
                goToVideoPlayer = openVideoPlayer,
                onTvChannelClick = { channel ->
                    val allChannels = tvChannelViewModel.channels.value
                    val currentIndex = allChannels.indexOfFirst { it.id == channel.id }

                    val intent = Intent(context, TvPlayer::class.java).apply {
                        putExtra("id", channel.id)
                        putExtra("name", channel.name)
                        putExtra("logoUrl", channel.logoUrl)
                        putExtra("streamUrl", channel.streamUrl)
                        putExtra("category", channel.category)
                        putExtra("current_index", currentIndex)
                        putParcelableArrayListExtra("channel_list", ArrayList(allChannels))
                    }
                    context.startActivity(intent)
                },
                onWidgetItemClick = { widgetItem: OttWidgetItem ->
                    // Handle widget item click - navigate to movie details
                    val movie = Movie(
                        id = widgetItem.id,
                        name = widgetItem.title,
                        description = "${widgetItem.language} • ${widgetItem.releaseYear} • ${widgetItem.genre}",
                        posterUri = widgetItem.posterUrl,
                        videoUri = "", // You might need to map this from your data
                        subtitleUri = null
                    )
                    openMovieDetailsScreen(movie.id)
                },
                onScroll = updateTopBarVisibility,
                isTopBarVisible = isTopBarVisible
            )
        }
        composable(Screens.Categories()) {
            CategoriesScreen(
                onCategoryClick = openCategoryMovieList,
                onScroll = updateTopBarVisibility
            )
        }
        composable(Screens.Movies()) {
            MoviesScreen(
                onMovieClick = { movie -> openMovieDetailsScreen(movie.id) },
                onScroll = updateTopBarVisibility,
                isTopBarVisible = isTopBarVisible
            )
        }
        composable(Screens.LiveTv()) {
            ShowsScreen(
                onTVShowClick = { movie -> openMovieDetailsScreen(movie.id) },
                onScroll = updateTopBarVisibility,
                isTopBarVisible = isTopBarVisible
            )
        }
        composable(Screens.Favourites()) {
            FavouritesScreen(
                onMovieClick = openMovieDetailsScreen,
                onScroll = updateTopBarVisibility,
                isTopBarVisible = isTopBarVisible
            )
        }
        composable(Screens.News()) {
            NewsScreen(
                onScroll = updateTopBarVisibility,
                isTopBarVisible = isTopBarVisible
            )
        }
        composable(Screens.Search()) {
            SearchScreen(
                onMovieClick = { movie -> openMovieDetailsScreen(movie.id) },
                onScroll = updateTopBarVisibility
            )
        }
    }
}