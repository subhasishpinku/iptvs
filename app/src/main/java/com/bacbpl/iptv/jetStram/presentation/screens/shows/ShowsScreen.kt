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
//package  com.bacbpl.iptv.jetStram.presentation.screens.shows
//
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.derivedStateOf
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import com.bacbpl.iptv.jetStram.data.entities.Movie
//import com.bacbpl.iptv.jetStram.data.entities.MovieList
//import com.bacbpl.iptv.data.util.StringConstants
//import com.bacbpl.iptv.jetStram.presentation.common.Loading
//import com.bacbpl.iptv.jetStram.presentation.common.MoviesRow
//import  com.bacbpl.iptv.jetStram.presentation.screens.dashboard.rememberChildPadding
//import com.bacbpl.iptv.jetStram.presentation.screens.movies.MoviesScreenMovieList
//
//@Composable
//fun ShowsScreen(
//    onTVShowClick: (movie: Movie) -> Unit,
//    onScroll: (isTopBarVisible: Boolean) -> Unit,
//    isTopBarVisible: Boolean,
//    showScreenViewModel: ShowScreenViewModel = hiltViewModel(),
//) {
//    val uiState = showScreenViewModel.uiState.collectAsStateWithLifecycle()
//    when (val currentState = uiState.value) {
//        is ShowScreenUiState.Loading -> {
//            Loading(modifier = Modifier.fillMaxSize())
//        }
//
//        is ShowScreenUiState.Ready -> {
//            Catalog(
//                tvShowList = currentState.tvShowList,
//                bingeWatchDramaList = currentState.bingeWatchDramaList,
//                onTVShowClick = onTVShowClick,
//                onScroll = onScroll,
//                isTopBarVisible = isTopBarVisible,
//                modifier = Modifier.fillMaxSize()
//            )
//        }
//    }
//}
//
//@Composable
//private fun Catalog(
//    tvShowList: MovieList,
//    bingeWatchDramaList: MovieList,
//    onTVShowClick: (movie: Movie) -> Unit,
//    onScroll: (isTopBarVisible: Boolean) -> Unit,
//    isTopBarVisible: Boolean,
//    modifier: Modifier = Modifier
//) {
//    val childPadding = rememberChildPadding()
//    val lazyListState = rememberLazyListState()
//    val shouldShowTopBar by remember {
//        derivedStateOf {
//            lazyListState.firstVisibleItemIndex == 0 &&
//                    lazyListState.firstVisibleItemScrollOffset == 0
//        }
//    }
//
//    LaunchedEffect(shouldShowTopBar) {
//        onScroll(shouldShowTopBar)
//    }
//    LaunchedEffect(isTopBarVisible) {
//        if (isTopBarVisible) lazyListState.animateScrollToItem(0)
//    }
//
//    LazyColumn(
//        modifier = modifier,
//        state = lazyListState,
//        contentPadding = PaddingValues(top = childPadding.top, bottom = 104.dp)
//    ) {
//        item {
//            MoviesScreenMovieList(
//                movieList = tvShowList,
//                onMovieClick = onTVShowClick
//            )
//        }
//        item {
//            MoviesRow(
//                modifier = Modifier.padding(top = childPadding.top),
//                title = StringConstants.Composable.BingeWatchDramasTitle,
//                movieList = bingeWatchDramaList,
//                onMovieSelected = onTVShowClick
//            )
//        }
//    }
//}
//package com.bacbpl.iptv.jetStram.presentation.screens.shows
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.foundation.*
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.foundation.lazy.grid.rememberLazyGridState
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.scale
//import androidx.compose.ui.focus.onFocusChanged
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.input.key.*
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//import com.bacbpl.iptv.R
//import com.bacbpl.iptv.jetStram.data.entities.Movie
//import com.bacbpl.iptv.jetStram.data.entities.TvChannel
//import com.bacbpl.iptv.jetStram.presentation.screens.home.TvChannelViewModel
//import com.bacbpl.iptv.jetStram.presentation.screens.dashboard.rememberChildPadding
//import com.google.android.exoplayer2.ExoPlayer
//import com.google.android.exoplayer2.MediaItem
//import com.google.android.exoplayer2.Player
//import com.google.android.exoplayer2.ui.StyledPlayerView
//import kotlinx.coroutines.delay
//@Composable
//fun ShowsScreen(
//    onTVShowClick: (movie: Movie) -> Unit,
//    onScroll: (isTopBarVisible: Boolean) -> Unit,
//    isTopBarVisible: Boolean,
//    tvChannelViewModel: TvChannelViewModel = hiltViewModel(),
//) {
//    val context = LocalContext.current
//    val channels by tvChannelViewModel.channels.collectAsStateWithLifecycle()
//    val isLoading by tvChannelViewModel.isLoading.collectAsStateWithLifecycle()
//
//    var currentChannelIndex by remember { mutableStateOf(0) }
//    var isPlayerReady by remember { mutableStateOf(false) }
//    val listState = rememberLazyListState()
//    val childPadding = rememberChildPadding()
//
//    // Handle scroll to update top bar visibility
//    LaunchedEffect(listState.isScrollInProgress) {
//        if (listState.firstVisibleItemIndex > 0) {
//            onScroll(false)
//        } else {
//            onScroll(true)
//        }
//    }
//
//    // Update current index when channels change
//    LaunchedEffect(channels) {
//        if (channels.isNotEmpty() && currentChannelIndex >= channels.size) {
//            currentChannelIndex = 0
//        }
//    }
//
//    // Auto-play functionality
//    LaunchedEffect(Unit) {
//        while (true) {
//            if (channels.isNotEmpty() && isPlayerReady) {
//                delay(30000) // 30 seconds per channel
//                currentChannelIndex = (currentChannelIndex + 1) % channels.size
//            }
//            delay(1000)
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Black)
//    ) {
//        if (channels.isNotEmpty()) {
//            LazyColumn(
//                state = listState,
//                modifier = Modifier.fillMaxSize()
//            ) {
//                // Auto-play TV Player item
//                item {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(400.dp)
//                    ) {
//                        TvAutoPlayer(
//                            channel = channels[currentChannelIndex],
//                            onPlayerReady = { isPlayerReady = true },
//                            modifier = Modifier.fillMaxSize()
//                        )
//
//                        // Channel count indicator
//                        Surface(
//                            modifier = Modifier
//                                .align(Alignment.TopEnd)
//                                .padding(16.dp)
//                                .clip(RoundedCornerShape(8.dp)),
//                            color = Color.Black.copy(alpha = 0.7f)
//                        ) {
//                            Text(
//                                text = "${currentChannelIndex + 1}/${channels.size}",
//                                color = Color.White,
//                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
//                                fontSize = 14.sp
//                            )
//                        }
//
//                        // Channel info overlay
//                        Surface(
//                            modifier = Modifier
//                                .align(Alignment.TopStart)
//                                .padding(16.dp)
//                                .clip(RoundedCornerShape(8.dp)),
//                            color = Color.Black.copy(alpha = 0.7f)
//                        ) {
//                            Row(
//                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                AsyncImage(
//                                    model = ImageRequest.Builder(LocalContext.current)
//                                        .data(channels[currentChannelIndex].logoUrl)
//                                        .crossfade(true)
//                                        .error(R.drawable.logo)
//                                        .build(),
//                                    contentDescription = channels[currentChannelIndex].name,
//                                    modifier = Modifier
//                                        .size(24.dp)
//                                        .clip(RoundedCornerShape(4.dp)),
//                                    contentScale = ContentScale.Fit
//                                )
//
//                                Spacer(modifier = Modifier.width(8.dp))
//
//                                Column {
//                                    Text(
//                                        text = channels[currentChannelIndex].name,
//                                        color = Color.White,
//                                        fontSize = 14.sp,
//                                        fontWeight = FontWeight.Bold
//                                    )
//                                    Text(
//                                        text = channels[currentChannelIndex].category,
//                                        color = Color.Gray,
//                                        fontSize = 12.sp
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//
//                // Live TV Channels Row
//                if (channels.isNotEmpty()) {
//                    item(contentType = "TvChannelsRow") {
//                        TvChannelsRow(
//                            title = "Live TV Channels",
//                            channels = channels.take(10), // Show first 10 channels
//                            onChannelSelected = { selectedChannel ->
//                                val index = channels.indexOfFirst { it.id == selectedChannel.id }
//                                if (index != -1) {
//                                    currentChannelIndex = index
//                                }
//                            },
//                            modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
//                        )
//                    }
//                }
//
//                // All Channels Section
////                item {
////                    Text(
////                        text = "All Channels",
////                        color = Color.Red,
////                        fontSize = 20.sp,
////                        fontWeight = FontWeight.Bold,
////                        modifier = Modifier.padding(
////                            start = childPadding.start,
////                            top = 16.dp,
////                            bottom = 12.dp
////                        )
////                    )
////                }
////
////                // Channels Grid
////                item {
////                    ChannelsGrid(
////                        channels = channels,
////                        currentChannelIndex = currentChannelIndex,
////                        onChannelSelected = { index ->
////                            currentChannelIndex = index
////                        },
////                        modifier = Modifier
////                            .fillMaxWidth()
////                            .padding(horizontal = childPadding.start)
////                            .height(400.dp)
////                    )
////                }
//
//                // Bottom padding
//                item {
//                    Spacer(modifier = Modifier.height(16.dp))
//                }
//            }
//        } else if (isLoading) {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator(
//                    color = Color.Red,
//                    modifier = Modifier.size(50.dp)
//                )
//            }
//        } else {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "No TV channels available",
//                    color = Color.White,
//                    fontSize = 16.sp
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun TvAutoPlayer(
//    channel: TvChannel,
//    onPlayerReady: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val context = LocalContext.current
//
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            playWhenReady = true
//            repeatMode = Player.REPEAT_MODE_OFF
//        }
//    }
//
//    LaunchedEffect(channel) {
//        try {
//            val mediaItem = MediaItem.fromUri(android.net.Uri.parse(channel.streamUrl))
//            exoPlayer.setMediaItem(mediaItem)
//            exoPlayer.prepare()
//            exoPlayer.playWhenReady = true
//            onPlayerReady()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    DisposableEffect(Unit) {
//        onDispose {
//            exoPlayer.run {
//                playWhenReady = false
//                stop()
//                release()
//            }
//        }
//    }
//
//    Box(modifier = modifier) {
//        AndroidView(
//            factory = { context ->
//                StyledPlayerView(context).apply {
//                    player = exoPlayer
//                    useController = false
//                    setShowBuffering(StyledPlayerView.SHOW_BUFFERING_WHEN_PLAYING)
//                    keepScreenOn = true
//                }
//            },
//            update = { view ->
//                view.player = exoPlayer
//            },
//            modifier = Modifier.fillMaxSize()
//        )
//
//        // Gradient overlay for better text visibility
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            Color.Transparent,
//                            Color.Black.copy(alpha = 0.7f)
//                        ),
//                        startY = 300f
//                    )
//                )
//        )
//    }
//}
//
//@Composable
//fun TvChannelsRow(
//    title: String,
//    channels: List<TvChannel>,
//    onChannelSelected: (TvChannel) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val childPadding = rememberChildPadding()
//
//    Column(modifier = modifier) {
//        Text(
//            text = title,
//            color = Color.White,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(
//                start = childPadding.start,
//                bottom = 8.dp
//            )
//        )
//
//        LazyRow(
//            horizontalArrangement = Arrangement.spacedBy(12.dp),
//            contentPadding = PaddingValues(
//                start = childPadding.start,
//                end = childPadding.end
//            )
//        ) {
//            items(
//                items = channels,
//                key = { it.id }
//            ) { channel ->
//                TvChannelItem(
//                    channel = channel,
//                    onChannelSelected = onChannelSelected
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun TvChannelItem(
//    channel: TvChannel,
//    onChannelSelected: (TvChannel) -> Unit
//) {
//    var isFocused by remember { mutableStateOf(false) }
//    val scale by animateFloatAsState(if (isFocused) 1.05f else 1f)
//
//    Card(
//        modifier = Modifier
//            .width(180.dp)
//            .height(120.dp)
//            .scale(scale)
//            .onFocusChanged { isFocused = it.isFocused }
//            .focusable()
//            .onKeyEvent { event ->
//                if (event.type == KeyEventType.KeyDown) {
//                    when (event.key) {
//                        Key.Enter, Key.NumPadEnter, Key.DirectionCenter -> {
//                            onChannelSelected(channel)
//                            true
//                        }
//                        else -> false
//                    }
//                } else {
//                    false
//                }
//            }
//            .clickable { onChannelSelected(channel) }
//            .border(
//                width = if (isFocused) 3.dp else 0.dp,
//                color = Color.Red,
//                shape = RoundedCornerShape(8.dp)
//            ),
//        shape = RoundedCornerShape(8.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = Color(0xFF2A2A2A)
//        ),
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = if (isFocused) 8.dp else 4.dp
//        )
//    ) {
//        Box(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            // Channel Logo
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(channel.logoUrl)
//                    .crossfade(true)
//                    .error(R.drawable.logo)
//                    .build(),
//                contentDescription = channel.name,
//                contentScale = ContentScale.Fit,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(8.dp)
//            )
//
//            // Channel Name at bottom
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.BottomCenter)
//                    .background(Color.Black.copy(alpha = 0.7f))
//                    .padding(vertical = 4.dp)
//            ) {
//                Text(
//                    text = channel.name,
//                    color = Color.White,
//                    fontSize = 12.sp,
//                    fontWeight = if (isFocused) FontWeight.Bold else FontWeight.Normal,
//                    maxLines = 1,
//                    modifier = Modifier
//                        .align(Alignment.Center)
//                        .padding(horizontal = 4.dp)
//                )
//            }
//
//            // Live indicator
//            Surface(
//                shape = RoundedCornerShape(4.dp),
//                color = Color.Red,
//                modifier = Modifier
//                    .align(Alignment.TopStart)
//                    .padding(4.dp)
//            ) {
//                Text(
//                    text = "LIVE",
//                    color = Color.White,
//                    fontSize = 10.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun ChannelsGrid(
//    channels: List<TvChannel>,
//    currentChannelIndex: Int,
//    onChannelSelected: (Int) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val gridState = rememberLazyGridState()
//    var focusedIndex by remember { mutableStateOf(currentChannelIndex) }
//
//    // Auto-scroll to keep current channel in view
//    LaunchedEffect(currentChannelIndex) {
//        gridState.animateScrollToItem(currentChannelIndex)
//    }
//
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(3),
//        state = gridState,
//        horizontalArrangement = Arrangement.spacedBy(12.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp),
//        modifier = modifier
//            .fillMaxWidth()
//    ) {
//        items(
//            items = channels,
//            key = { it.id }
//        ) { channel ->
//            val index = channels.indexOf(channel)
//            val isCurrentChannel = index == currentChannelIndex
//
//            ChannelGridItem(
//                channel = channel,
//                isCurrentChannel = isCurrentChannel,
//                isFocused = index == focusedIndex,
//                onFocus = { focusedIndex = index },
//                onClick = { onChannelSelected(index) }
//            )
//        }
//    }
//}
//
//@Composable
//fun ChannelGridItem(
//    channel: TvChannel,
//    isCurrentChannel: Boolean,
//    isFocused: Boolean,
//    onFocus: () -> Unit,
//    onClick: () -> Unit
//) {
//    val scale by animateFloatAsState(if (isFocused) 1.1f else 1f)
//    val borderColor = when {
//        isCurrentChannel -> Color.Red
//        isFocused -> Color.Yellow
//        else -> Color.Transparent
//    }
//    val borderWidth = when {
//        isCurrentChannel -> 3.dp
//        isFocused -> 2.dp
//        else -> 0.dp
//    }
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .aspectRatio(0.9f)
//            .scale(scale)
//            .onFocusChanged { if (it.isFocused) onFocus() }
//            .focusable()
//            .onKeyEvent { event ->
//                if (event.type == KeyEventType.KeyDown) {
//                    when (event.key) {
//                        Key.Enter, Key.NumPadEnter, Key.DirectionCenter -> {
//                            onClick()
//                            true
//                        }
//                        else -> false
//                    }
//                } else {
//                    false
//                }
//            }
//            .clickable { onClick() }
//            .border(
//                width = borderWidth,
//                color = borderColor,
//                shape = RoundedCornerShape(12.dp)
//            ),
//        colors = CardDefaults.cardColors(
//            containerColor = Color.DarkGray
//        ),
//        shape = RoundedCornerShape(12.dp)
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(8.dp)
//        ) {
//            // Playing indicator
//            if (isCurrentChannel) {
//                Surface(
//                    modifier = Modifier
//                        .size(20.dp)
//                        .align(Alignment.End),
//                    color = Color.Red,
//                    shape = RoundedCornerShape(4.dp)
//                ) {
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "▶",
//                            color = Color.White,
//                            fontSize = 10.sp
//                        )
//                    }
//                }
//            }
//
//            // Channel Logo
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(channel.logoUrl)
//                    .crossfade(true)
//                    .error(R.drawable.logo)
//                    .build(),
//                contentDescription = channel.name,
//                modifier = Modifier
//                    .size(70.dp)
//                    .clip(RoundedCornerShape(8.dp))
//                    .background(Color.DarkGray),
//                contentScale = ContentScale.Fit
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Channel Name
//            Text(
//                text = channel.name,
//                color = when {
//                    isCurrentChannel -> Color.Red
//                    isFocused -> Color.Yellow
//                    else -> Color.White
//                },
//                fontSize = 13.sp,
//                fontWeight = if (isCurrentChannel || isFocused) FontWeight.Bold else FontWeight.Normal,
//                maxLines = 2,
//                minLines = 2,
//                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
//                modifier = Modifier.padding(horizontal = 4.dp)
//            )
//
//            // Category
//            Text(
//                text = channel.category,
//                color = Color.Gray,
//                fontSize = 11.sp,
//                maxLines = 1
//            )
//        }
//    }
//}

//package com.bacbpl.iptv.jetStram.presentation.screens.shows
//import android.content.Intent
//import androidx.activity.compose.BackHandler
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.foundation.*
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.scale
//import androidx.compose.ui.focus.onFocusChanged
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.input.key.*
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//import com.bacbpl.iptv.R
//import com.bacbpl.iptv.jetStram.data.entities.Movie
//import com.bacbpl.iptv.jetStram.data.entities.TvChannel
//import com.bacbpl.iptv.jetStram.presentation.screens.home.TvChannelViewModel
//import com.bacbpl.iptv.jetStram.presentation.screens.dashboard.rememberChildPadding
//import com.bacbpl.iptv.jetStram.presentation.screens.player.TvPlayer
//import com.google.android.exoplayer2.ExoPlayer
//import com.google.android.exoplayer2.MediaItem
//import com.google.android.exoplayer2.Player
//import com.google.android.exoplayer2.ui.StyledPlayerView
//import kotlinx.coroutines.delay
//
//@Composable
//fun ShowsScreen(
//    onTVShowClick: (movie: Movie) -> Unit,
//    onScroll: (isTopBarVisible: Boolean) -> Unit,
//    isTopBarVisible: Boolean,
//    tvChannelViewModel: TvChannelViewModel = hiltViewModel(),
//) {
//    val context = LocalContext.current
//    val channels by tvChannelViewModel.channels.collectAsStateWithLifecycle()
//    val isLoading by tvChannelViewModel.isLoading.collectAsStateWithLifecycle()
//
//    var currentChannelIndex by remember { mutableStateOf(0) }
//    var isPlayerReady by remember { mutableStateOf(false) }
//    val listState = rememberLazyListState()
//    val childPadding = rememberChildPadding()
//
//
//
//    // Create a reference to the current ExoPlayer that can be accessed in navigation
//    val exoPlayerRef = remember { mutableStateOf<ExoPlayer?>(null) }
//    val activity = (LocalContext.current as? android.app.Activity)
//
//    // Handle back button press to stop video
//    BackHandler(
//        enabled = true,
//        onBack = {
//            // Stop and release the player when back is pressed
//            exoPlayerRef.value?.run {
//                playWhenReady = false
//                stop()
//                release()
//            }
//            exoPlayerRef.value = null
//            activity?.finish()
//        }
//    )
//    // Handle scroll to update top bar visibility
//    LaunchedEffect(listState.isScrollInProgress) {
//        if (listState.firstVisibleItemIndex > 0) {
//            onScroll(false)
//        } else {
//            onScroll(true)
//        }
//    }
//
//    // Update current index when channels change
//    LaunchedEffect(channels) {
//        if (channels.isNotEmpty() && currentChannelIndex >= channels.size) {
//            currentChannelIndex = 0
//        }
//    }
//
//    // Auto-play functionality
//    LaunchedEffect(Unit) {
//        while (true) {
//            if (channels.isNotEmpty() && isPlayerReady) {
//                delay(30000) // 30 seconds per channel
//                currentChannelIndex = (currentChannelIndex + 1) % channels.size
//            }
//            delay(1000)
//        }
//    }
//
//    // Function to navigate to TvPlayer and stop current playback
//    fun navigateToTvPlayer(channel: TvChannel) {
//        // Stop and release the current ExoPlayer before navigating
//        exoPlayerRef.value?.run {
//            playWhenReady = false
//            stop()
//            release()
//        }
//        exoPlayerRef.value = null
//
//        val allChannels = channels
//        val currentIndex = allChannels.indexOfFirst { it.id == channel.id }.takeIf { it >= 0 } ?: 0
//
//        val intent = Intent(context, TvPlayer::class.java).apply {
//            putExtra(TvPlayer.EXTRA_CHANNEL_ID, channel.id)
//            putExtra(TvPlayer.EXTRA_CHANNEL_NAME, channel.name)
//            putExtra(TvPlayer.EXTRA_CHANNEL_LOGO_URL, channel.logoUrl)
//            putExtra(TvPlayer.EXTRA_CHANNEL_STREAM_URL, channel.streamUrl)
//            putExtra(TvPlayer.EXTRA_CHANNEL_CATEGORY, channel.category)
//            putExtra(TvPlayer.EXTRA_CURRENT_INDEX, currentIndex)
//            putParcelableArrayListExtra(TvPlayer.EXTRA_CHANNEL_LIST, ArrayList(allChannels))
//        }
//        context.startActivity(intent)
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Black)
//    ) {
//        if (channels.isNotEmpty()) {
//            LazyColumn(
//                state = listState,
//                modifier = Modifier.fillMaxSize()
//            ) {
//                // Auto-play TV Player item - Full width
//                item {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(400.dp)
//                    ) {
//                        TvAutoPlayer(
//                            channel = channels[currentChannelIndex],
//                            onPlayerReady = { isPlayerReady = true },
//                            onPlayerCreated = { player ->
//                                exoPlayerRef.value = player
//                            },
//                            modifier = Modifier.fillMaxSize()
//                        )
//
//                        // Gradient overlay for better text visibility
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .background(
//                                    brush = Brush.verticalGradient(
//                                        colors = listOf(
//                                            Color.Transparent,
//                                            Color.Black.copy(alpha = 0.5f)
//                                        ),
//                                        startY = 300f
//                                    )
//                                )
//                        )
//
//                        // Channel count indicator
//                        Surface(
//                            modifier = Modifier
//                                .align(Alignment.TopEnd)
//                                .padding(16.dp)
//                                .clip(RoundedCornerShape(8.dp)),
//                            color = Color.Black.copy(alpha = 0.7f)
//                        ) {
//                            Text(
//                                text = "${currentChannelIndex + 1}/${channels.size}",
//                                color = Color.White,
//                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
//                                fontSize = 14.sp
//                            )
//                        }
//
//                        // Channel info overlay
//                        Surface(
//                            modifier = Modifier
//                                .align(Alignment.TopStart)
//                                .padding(16.dp)
//                                .clip(RoundedCornerShape(8.dp)),
//                            color = Color.Black.copy(alpha = 0.7f)
//                        ) {
//                            Row(
//                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                AsyncImage(
//                                    model = ImageRequest.Builder(LocalContext.current)
//                                        .data(channels[currentChannelIndex].logoUrl)
//                                        .crossfade(true)
//                                        .error(R.drawable.logo)
//                                        .build(),
//                                    contentDescription = channels[currentChannelIndex].name,
//                                    modifier = Modifier
//                                        .size(24.dp)
//                                        .clip(RoundedCornerShape(4.dp)),
//                                    contentScale = ContentScale.Fit
//                                )
//
//                                Spacer(modifier = Modifier.width(8.dp))
//
//                                Column {
//                                    Text(
//                                        text = channels[currentChannelIndex].name,
//                                        color = Color.White,
//                                        fontSize = 14.sp,
//                                        fontWeight = FontWeight.Bold
//                                    )
//                                    Text(
//                                        text = channels[currentChannelIndex].category,
//                                        color = Color.Gray,
//                                        fontSize = 12.sp
//                                    )
//                                }
//                            }
//                        }
//
//                        // Click on player to open full player
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .clickable {
//                                    navigateToTvPlayer(channels[currentChannelIndex])
//                                }
//                        )
//                    }
//                }
//
//                // Live TV Channels Row
//                if (channels.isNotEmpty()) {
//                    item(contentType = "TvChannelsRow") {
//                        TvChannelsRow(
//                            title = "Live TV Channels",
//                            channels = channels.take(10), // Show first 10 channels
//                            onChannelSelected = { selectedChannel ->
//                                // SINGLE CLICK: Open full player
//                                navigateToTvPlayer(selectedChannel)
//                            },
//                            modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
//                        )
//                    }
//                }
//
//                // Bottom padding
//                item {
//                    Spacer(modifier = Modifier.height(30.dp))
//                }
//            }
//        } else if (isLoading) {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator(
//                    color = Color.Red,
//                    modifier = Modifier.size(50.dp)
//                )
//            }
//        } else {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "No TV channels available",
//                    color = Color.White,
//                    fontSize = 16.sp
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun TvAutoPlayer(
//    channel: TvChannel,
//    onPlayerReady: () -> Unit,
//    onPlayerCreated: (ExoPlayer) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val context = LocalContext.current
//
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            playWhenReady = true
//            repeatMode = Player.REPEAT_MODE_OFF
//        }
//    }
//
//    // Pass the player reference to parent
//    LaunchedEffect(exoPlayer) {
//        onPlayerCreated(exoPlayer)
//    }
//
//    LaunchedEffect(channel) {
//        try {
//            val mediaItem = MediaItem.fromUri(android.net.Uri.parse(channel.streamUrl))
//            exoPlayer.setMediaItem(mediaItem)
//            exoPlayer.prepare()
//            exoPlayer.playWhenReady = true
//            onPlayerReady()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//    DisposableEffect(Unit) {
//        onDispose {
//            // Always release in onDispose as safety cleanup
//            // Even if already released, calling again is safe
//            exoPlayer.run {
//                playWhenReady = false
//                stop()
//                release()
//            }
//        }
//    }
//
//    Box(modifier = modifier) {
//        AndroidView(
//            factory = { context ->
//                StyledPlayerView(context).apply {
//                    player = exoPlayer
//                    useController = false
//                    setShowBuffering(StyledPlayerView.SHOW_BUFFERING_WHEN_PLAYING)
//                    keepScreenOn = true
//                    resizeMode = com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL // Changed to FILL for full width
//                }
//            },
//            update = { view ->
//                view.player = exoPlayer
//            },
//            modifier = Modifier.fillMaxSize()
//        )
//    }
//}
//
//@Composable
//fun TvChannelsRow(
//    title: String,
//    channels: List<TvChannel>,
//    onChannelSelected: (TvChannel) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val childPadding = rememberChildPadding()
//
//    Column(modifier = modifier) {
//        Text(
//            text = title,
//            color = Color.White,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(
//                start = childPadding.start,
//                bottom = 8.dp
//            )
//        )
//
//        LazyRow(
//            horizontalArrangement = Arrangement.spacedBy(12.dp),
//            contentPadding = PaddingValues(
//                start = childPadding.start,
//                end = childPadding.end
//            )
//        ) {
//            items(
//                items = channels,
//                key = { it.id }
//            ) { channel ->
//                TvChannelItem(
//                    channel = channel,
//                    onChannelSelected = onChannelSelected
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun TvChannelItem(
//    channel: TvChannel,
//    onChannelSelected: (TvChannel) -> Unit
//) {
//    var isFocused by remember { mutableStateOf(false) }
//    val scale by animateFloatAsState(if (isFocused) 1.05f else 1f)
//
//    Card(
//        modifier = Modifier
//            .width(180.dp)
//            .height(120.dp)
//            .scale(scale)
//            .onFocusChanged { isFocused = it.isFocused }
//            .focusable()
//            .onKeyEvent { event ->
//                if (event.type == KeyEventType.KeyDown) {
//                    when (event.key) {
//                        Key.Enter, Key.NumPadEnter, Key.DirectionCenter -> {
//                            onChannelSelected(channel)
//                            true
//                        }
//                        else -> false
//                    }
//                } else {
//                    false
//                }
//            }
//            .clickable {
//                onChannelSelected(channel)
//            }
//            .border(
//                width = if (isFocused) 3.dp else 0.dp,
//                color = Color.Red,
//                shape = RoundedCornerShape(8.dp)
//            ),
//        shape = RoundedCornerShape(8.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = Color(0xFF2A2A2A)
//        ),
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = if (isFocused) 8.dp else 4.dp
//        )
//    ) {
//        Box(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            // Channel Logo
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(channel.logoUrl)
//                    .crossfade(true)
//                    .error(R.drawable.logo)
//                    .build(),
//                contentDescription = channel.name,
//                contentScale = ContentScale.Fit,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(8.dp)
//            )
//
//            // Channel Name at bottom
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.BottomCenter)
//                    .background(Color.Black.copy(alpha = 0.7f))
//                    .padding(vertical = 4.dp)
//            ) {
//                Text(
//                    text = channel.name,
//                    color = Color.White,
//                    fontSize = 12.sp,
//                    fontWeight = if (isFocused) FontWeight.Bold else FontWeight.Normal,
//                    maxLines = 1,
//                    modifier = Modifier
//                        .align(Alignment.Center)
//                        .padding(horizontal = 4.dp)
//                )
//            }
//
//            // Live indicator
//            Surface(
//                shape = RoundedCornerShape(4.dp),
//                color = Color.Red,
//                modifier = Modifier
//                    .align(Alignment.TopStart)
//                    .padding(4.dp)
//            ) {
//                Text(
//                    text = "LIVE",
//                    color = Color.White,
//                    fontSize = 10.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
//                )
//            }
//        }
//    }
//}

package com.bacbpl.iptv.jetStram.presentation.screens.shows

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.key.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bacbpl.iptv.R
import com.bacbpl.iptv.jetStram.data.entities.Movie
import com.bacbpl.iptv.jetStram.data.entities.TvChannel
import com.bacbpl.iptv.jetStram.presentation.screens.home.TvChannelViewModel
import com.bacbpl.iptv.jetStram.presentation.screens.dashboard.rememberChildPadding
import com.bacbpl.iptv.jetStram.presentation.screens.player.TvPlayer
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.delay

@Composable
fun ShowsScreen(
    onTVShowClick: (movie: Movie) -> Unit,
    onScroll: (isTopBarVisible: Boolean) -> Unit,
    isTopBarVisible: Boolean,
    tvChannelViewModel: TvChannelViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val channels by tvChannelViewModel.channels.collectAsStateWithLifecycle()
    val isLoading by tvChannelViewModel.isLoading.collectAsStateWithLifecycle()

    var currentChannelIndex by remember { mutableStateOf(0) }
    var isPlayerReady by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val childPadding = rememberChildPadding()

    // Create a reference to the current ExoPlayer that can be accessed in navigation
    val exoPlayerRef = remember { mutableStateOf<ExoPlayer?>(null) }
    val activity = (LocalContext.current as? android.app.Activity)

    // Handle back button press to stop video
    BackHandler(
        enabled = true,
        onBack = {
            // Stop and release the player when back is pressed
            exoPlayerRef.value?.run {
                playWhenReady = false
                stop()
                release()
            }
            exoPlayerRef.value = null
            activity?.finish()
        }
    )

    // Handle scroll to update top bar visibility
    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.firstVisibleItemIndex > 0) {
            onScroll(false)
        } else {
            onScroll(true)
        }
    }

    // Update current index when channels change
    LaunchedEffect(channels) {
        if (channels.isNotEmpty() && currentChannelIndex >= channels.size) {
            currentChannelIndex = 0
        }
    }

    // Auto-play functionality
    LaunchedEffect(Unit) {
        while (true) {
            if (channels.isNotEmpty() && isPlayerReady) {
                delay(30000) // 30 seconds per channel
                currentChannelIndex = (currentChannelIndex + 1) % channels.size
            }
            delay(1000)
        }
    }

    // Function to navigate to TvPlayer and stop current playback
    fun navigateToTvPlayer(channel: TvChannel) {
        // Stop and release the current ExoPlayer before navigating
        exoPlayerRef.value?.run {
            playWhenReady = false
            stop()
            release()
        }
        exoPlayerRef.value = null

        val allChannels = channels
        val currentIndex = allChannels.indexOfFirst { it.id == channel.id }.takeIf { it >= 0 } ?: 0

        val intent = Intent(context, TvPlayer::class.java).apply {
            putExtra(TvPlayer.EXTRA_CHANNEL_ID, channel.id)
            putExtra(TvPlayer.EXTRA_CHANNEL_NAME, channel.name)
            putExtra(TvPlayer.EXTRA_CHANNEL_LOGO_URL, channel.logoUrl)
            putExtra(TvPlayer.EXTRA_CHANNEL_STREAM_URL, channel.streamUrl)
            putExtra(TvPlayer.EXTRA_CHANNEL_CATEGORY, channel.category)
            putExtra(TvPlayer.EXTRA_CURRENT_INDEX, currentIndex)
            putParcelableArrayListExtra(TvPlayer.EXTRA_CHANNEL_LIST, ArrayList(allChannels))
        }
        context.startActivity(intent)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (channels.isNotEmpty()) {
            // Video Player - Full Screen Background
            TvAutoPlayer(
                channel = channels[currentChannelIndex],
                onPlayerReady = { isPlayerReady = true },
                onPlayerCreated = { player ->
                    exoPlayerRef.value = player
                },
                modifier = Modifier.fillMaxSize() // This makes it full screen
            )

            // Gradient overlay for better text visibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.5f)
                            ),
                            startY = 500f
                        )
                    )
            )

            // Content Overlay (Channels List)
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                // Spacer to push content down from top
                item {
                    Spacer(modifier = Modifier.height(400.dp))
                }

                // Channel info overlay (moved to overlay)
//                item {
//                    Surface(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 16.dp)
//                            .clip(RoundedCornerShape(8.dp)),
//                        color = Color.Black.copy(alpha = 0.7f)
//                    ) {
//                        Row(
//                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            AsyncImage(
//                                model = ImageRequest.Builder(LocalContext.current)
//                                    .data(channels[currentChannelIndex].logoUrl)
//                                    .crossfade(true)
//                                    .error(R.drawable.logo)
//                                    .build(),
//                                contentDescription = channels[currentChannelIndex].name,
//                                modifier = Modifier
//                                    .size(32.dp)
//                                    .clip(RoundedCornerShape(4.dp)),
//                                contentScale = ContentScale.Fit
//                            )
//
//                            Spacer(modifier = Modifier.width(12.dp))
//
//                            Column {
//                                Text(
//                                    text = channels[currentChannelIndex].name,
//                                    color = Color.White,
//                                    fontSize = 16.sp,
//                                    fontWeight = FontWeight.Bold
//                                )
//                                Text(
//                                    text = channels[currentChannelIndex].category,
//                                    color = Color.LightGray,
//                                    fontSize = 14.sp
//                                )
//                            }
//                        }
//                    }
//                }

                // Live TV Channels Row
                if (channels.isNotEmpty()) {
                    item(contentType = "TvChannelsRow") {
                        TvChannelsRow(
                            title = "Live TV Channels",
                            channels = channels.take(100),
                            onChannelSelected = { selectedChannel ->
                                navigateToTvPlayer(selectedChannel)
                            },
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                        )
                    }
                }

                // Bottom padding
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }

            // Channel count indicator (top right)
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp)),
                color = Color.Black.copy(alpha = 0.7f)
            ) {
                Text(
                    text = "${currentChannelIndex + 1}/${channels.size}",
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontSize = 14.sp
                )
            }

            // Click on full screen to open full player
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        navigateToTvPlayer(channels[currentChannelIndex])
                    }
            )
        } else if (isLoading) {
            // Loading indicator
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.Red,
                    modifier = Modifier.size(50.dp)
                )
            }
        } else {
            // No channels message
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No TV channels available",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun TvAutoPlayer(
    channel: TvChannel,
    onPlayerReady: () -> Unit,
    onPlayerCreated: (ExoPlayer) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            playWhenReady = true
            repeatMode = Player.REPEAT_MODE_OFF
        }
    }

    // Pass the player reference to parent
    LaunchedEffect(exoPlayer) {
        onPlayerCreated(exoPlayer)
    }

    LaunchedEffect(channel) {
        try {
            val mediaItem = MediaItem.fromUri(android.net.Uri.parse(channel.streamUrl))
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
            onPlayerReady()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            // Always release in onDispose as safety cleanup
            exoPlayer.run {
                playWhenReady = false
                stop()
                release()
            }
        }
    }

    Box(modifier = modifier) {
        AndroidView(
            factory = { context ->
                StyledPlayerView(context).apply {
                    player = exoPlayer
                    useController = false
                    setShowBuffering(StyledPlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                    keepScreenOn = true
                    resizeMode = com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL
                }
            },
            update = { view ->
                view.player = exoPlayer
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun TvChannelsRow(
    title: String,
    channels: List<TvChannel>,
    onChannelSelected: (TvChannel) -> Unit,
    modifier: Modifier = Modifier
) {
    val childPadding = rememberChildPadding()

    Column(modifier = modifier) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                start = childPadding.start,
                bottom = 8.dp
            )
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(
                start = childPadding.start,
                end = childPadding.end
            )
        ) {
            items(
                items = channels,
                key = { it.id }
            ) { channel ->
                TvChannelItem(
                    channel = channel,
                    onChannelSelected = onChannelSelected
                )
            }
        }
    }
}

@Composable
fun TvChannelItem(
    channel: TvChannel,
    onChannelSelected: (TvChannel) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isFocused) 1.05f else 1f)

    Card(
        modifier = Modifier
            .width(180.dp)
            .height(120.dp)
            .scale(scale)
            .onFocusChanged { isFocused = it.isFocused }
            .focusable()
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown) {
                    when (event.key) {
                        Key.Enter, Key.NumPadEnter, Key.DirectionCenter -> {
                            onChannelSelected(channel)
                            true
                        }
                        else -> false
                    }
                } else {
                    false
                }
            }
            .clickable {
                onChannelSelected(channel)
            }
            .border(
                width = if (isFocused) 3.dp else 0.dp,
                color = Color.Red,
                shape = RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isFocused) 8.dp else 4.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Channel Logo
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(channel.logoUrl)
                    .crossfade(true)
                    .error(R.drawable.logo)
                    .build(),
                contentDescription = channel.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )

            // Channel Name at bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.7f))
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = channel.name,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = if (isFocused) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 4.dp)
                )
            }

            // Live indicator
            Surface(
                shape = RoundedCornerShape(4.dp),
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(4.dp)
            ) {
                Text(
                    text = "LIVE",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }
        }
    }
}