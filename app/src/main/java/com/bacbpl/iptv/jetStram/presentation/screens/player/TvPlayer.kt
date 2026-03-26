//package com.bacbpl.iptv.jetStram.presentation.screens.player
//
//import android.net.Uri
//import android.os.Bundle
//import android.view.KeyEvent
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowLeft
//import androidx.compose.material.icons.filled.ArrowRight
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.focus.FocusRequester
//import androidx.compose.ui.focus.focusRequester
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.key.*
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource  // Correct import for painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.viewinterop.AndroidView
//import coil.compose.rememberAsyncImagePainter
//import coil.request.ImageRequest
//import com.bacbpl.iptv.jetStram.data.entities.TvChannel
//import com.bacbpl.iptv.R  // Make sure this import exists for R.drawable
//import com.google.android.exoplayer2.ExoPlayer
//import com.google.android.exoplayer2.MediaItem
//import com.google.android.exoplayer2.Player
//import com.google.android.exoplayer2.ui.StyledPlayerView
//import kotlinx.coroutines.delay
//
//class TvPlayer : ComponentActivity() {
//
//    companion object {
//        const val EXTRA_CHANNEL_ID = "id"
//        const val EXTRA_CHANNEL_NAME = "name"
//        const val EXTRA_CHANNEL_LOGO_URL = "logoUrl"
//        const val EXTRA_CHANNEL_STREAM_URL = "streamUrl"
//        const val EXTRA_CHANNEL_CATEGORY = "category"
//        const val EXTRA_CHANNEL_LIST = "channel_list"
//        const val EXTRA_CURRENT_INDEX = "current_index"
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val channel = TvChannel(
//            id = intent.getStringExtra(EXTRA_CHANNEL_ID) ?: "",
//            name = intent.getStringExtra(EXTRA_CHANNEL_NAME) ?: "",
//            logoUrl = intent.getStringExtra(EXTRA_CHANNEL_LOGO_URL) ?: "",
//            streamUrl = intent.getStringExtra(EXTRA_CHANNEL_STREAM_URL) ?: "",
//            category = intent.getStringExtra(EXTRA_CHANNEL_CATEGORY) ?: "Live TV"
//        )
//
//        val channelsList = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
//            intent.getParcelableArrayListExtra(EXTRA_CHANNEL_LIST, TvChannel::class.java)
//        } else {
//            @Suppress("DEPRECATION")
//            intent.getParcelableArrayListExtra(EXTRA_CHANNEL_LIST)
//        } ?: arrayListOf(channel)
//
//        val currentIndex = intent.getIntExtra(EXTRA_CURRENT_INDEX, 0)
//
//        setContent {
//            TvPlayerScreen(
//                initialChannel = channel,
//                allChannels = channelsList,
//                initialIndex = currentIndex
//            )
//        }
//    }
//
//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        return when (keyCode) {
//            KeyEvent.KEYCODE_DPAD_RIGHT, KeyEvent.KEYCODE_DPAD_LEFT -> {
//                false // Let the Composable handle these keys
//            }
//            else -> super.onKeyDown(keyCode, event)
//        }
//    }
//}
//
//@Composable
//fun TvPlayerScreen(
//    initialChannel: TvChannel,
//    allChannels: List<TvChannel>,
//    initialIndex: Int
//) {
//    val context = LocalContext.current
//    var currentChannel by remember { mutableStateOf(initialChannel) }
//    var currentIndex by remember { mutableStateOf(initialIndex) }
//    val focusRequester = remember { FocusRequester() }
//
//    // Create ExoPlayer instance
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            playWhenReady = true
//            repeatMode = Player.REPEAT_MODE_OFF
//        }
//    }
//
//    // Function to load channel
//    fun loadChannel(streamUrl: String) {
//        try {
//            val mediaItem = MediaItem.fromUri(Uri.parse(streamUrl))
//            exoPlayer.setMediaItem(mediaItem)
//            exoPlayer.prepare()
//            exoPlayer.playWhenReady = true
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    // Load initial channel
//    LaunchedEffect(Unit) {
//        loadChannel(currentChannel.streamUrl)
//    }
//
//    // Handle channel changes
//    LaunchedEffect(currentChannel) {
//        loadChannel(currentChannel.streamUrl)
//    }
//
//    // Release player when composable is disposed
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
//    // Request focus when screen loads
//    LaunchedEffect(Unit) {
//        focusRequester.requestFocus()
//    }
//
//    // Check if streamUrl is empty
//    if (currentChannel.streamUrl.isEmpty()) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = "Invalid stream URL",
//                style = MaterialTheme.typography.bodyLarge,
//                color = Color.White
//            )
//        }
//        return
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .focusRequester(focusRequester)
//            .onKeyEvent { keyEvent ->
//                when {
//                    keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionRight -> {
//                        if (currentIndex < allChannels.size - 1) {
//                            currentIndex++
//                            currentChannel = allChannels[currentIndex]
//                        }
//                        true
//                    }
//                    keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionLeft -> {
//                        if (currentIndex > 0) {
//                            currentIndex--
//                            currentChannel = allChannels[currentIndex]
//                        }
//                        true
//                    }
//                    else -> false
//                }
//            }
//    ) {
//        // AndroidView for ExoPlayer
//        AndroidView(
//            factory = { context ->
//                StyledPlayerView(context).apply {
//                    player = exoPlayer
//                    useController = true
//                    resizeMode = com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
//                    setShowBuffering(StyledPlayerView.SHOW_BUFFERING_WHEN_PLAYING)
//                    keepScreenOn = true
//                    isFocusable = true
//                    isFocusableInTouchMode = true
//                }
//            },
//            update = { view ->
//                view.player = exoPlayer
//            },
//            modifier = Modifier.fillMaxSize()
//        )
//
//        // Top overlay for channel info (temporary)
//        ChannelInfoOverlay(
//            channel = currentChannel,
//            currentIndex = currentIndex,
//            totalChannels = allChannels.size,
//            onNext = {
//                if (currentIndex < allChannels.size - 1) {
//                    currentIndex++
//                    currentChannel = allChannels[currentIndex]
//                }
//            },
//            onPrevious = {
//                if (currentIndex > 0) {
//                    currentIndex--
//                    currentChannel = allChannels[currentIndex]
//                }
//            },
//            modifier = Modifier.align(Alignment.TopCenter)
//        )
//
//        // Permanent bottom right corner logo
//        PermanentChannelLogo(
//            channel = currentChannel,
//            modifier = Modifier.align(Alignment.BottomEnd)
//        )
//    }
//}
//
//@Composable
//fun PermanentChannelLogo(
//    channel: TvChannel,
//    modifier: Modifier = Modifier
//) {
//    Surface(
//        modifier = modifier
//            .padding(24.dp)
//            .width(180.dp)  // Wider to accommodate both logos
//            .height(80.dp)
//            .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp)),
//        color = Color.Black.copy(alpha = 0.6f),
//        shape = RoundedCornerShape(8.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(8.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Left logo (from URL)
////            Image(
////                painter = rememberAsyncImagePainter(
////                    model = ImageRequest.Builder(LocalContext.current)
////                        .data(channel.logoUrl)
////                        .crossfade(true)
////                        .error(R.drawable.logo)
////                        .build()
////                ),
////                contentDescription = channel.name,
////                modifier = Modifier
////                    .size(60.dp)
////                    .clip(RoundedCornerShape(4.dp)),
////                contentScale = ContentScale.Fit
////            )
//
//            // Right logo (local only)
//            Image(
//                painter = painterResource(id = R.drawable.logo),
//                contentDescription = "App Logo",
//                modifier = Modifier
//                    .size(100.dp)
//                    .clip(RoundedCornerShape(4.dp)),
//                contentScale = ContentScale.Fit
//            )
//        }
//    }
//}
//
//@Composable
//fun ChannelInfoOverlay(
//    channel: TvChannel,
//    currentIndex: Int,
//    totalChannels: Int,
//    onNext: () -> Unit,
//    onPrevious: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    var showOverlay by remember { mutableStateOf(true) }
//
//    // Show overlay when channel changes and auto-hide after 3 seconds
//    LaunchedEffect(channel) {
//        showOverlay = true
//        delay(3000)
//        showOverlay = false
//    }
//
//    if (showOverlay) {
//        Surface(
//            modifier = modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            color = Color.Black.copy(alpha = 0.7f),
//            shape = RoundedCornerShape(8.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    // Channel logo with fallback to local image
//                    Image(
//                        painter = rememberAsyncImagePainter(
//                            model = ImageRequest.Builder(LocalContext.current)
//                                .data(R.drawable.logo)
//                                .crossfade(true)
//                                .error(R.drawable.logo) // Fallback to local image
//                                .build()
//                        ),
//                        contentDescription = channel.name,
//                        modifier = Modifier
//                            .size(32.dp)
//                            .clip(RoundedCornerShape(4.dp))
//                            .background(Color.DarkGray),
//                        contentScale = ContentScale.Fit
//                    )
//
//                    Spacer(modifier = Modifier.width(12.dp))
//
//                    Column {
//                        Text(
//                            text = channel.name,
//                            color = Color.White,
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                        Text(
//                            text = channel.category,
//                            color = Color.White.copy(alpha = 0.7f),
//                            fontSize = 12.sp
//                        )
//                    }
//                }
//
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        imageVector = Icons.Default.ArrowLeft,
//                        contentDescription = "Previous",
//                        tint = Color.White,
//                        modifier = Modifier.size(40.dp)
//                    )
//
//                    Text(
//                        text = "${currentIndex + 1}/${totalChannels}",
//                        color = Color.White,
//                        fontSize = 14.sp,
//                        modifier = Modifier.padding(horizontal = 8.dp)
//                    )
//
//                    Icon(
//                        imageVector = Icons.Default.ArrowRight,
//                        contentDescription = "Next",
//                        tint = Color.White,
//                        modifier = Modifier.size(40.dp)
//                    )
//                }
//            }
//        }
//    }
//}
//
//package com.bacbpl.iptv.jetStram.presentation.screens.player
//import android.net.Uri
//import android.os.Bundle
//import android.view.KeyEvent
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.focusable
//import androidx.compose.foundation.gestures.detectTapGestures
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.focus.FocusRequester
//import androidx.compose.ui.focus.focusRequester
//import androidx.compose.ui.focus.onFocusChanged
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.input.key.*
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.viewinterop.AndroidView
//import coil.compose.rememberAsyncImagePainter
//import coil.request.ImageRequest
//import com.bacbpl.iptv.R
//import com.bacbpl.iptv.jetStram.data.entities.TvChannel
//import com.google.android.exoplayer2.ExoPlayer
//import com.google.android.exoplayer2.MediaItem
//import com.google.android.exoplayer2.Player
//import com.google.android.exoplayer2.ui.StyledPlayerView
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//
//class TvPlayer : ComponentActivity() {
//
//    companion object {
//        const val EXTRA_CHANNEL_ID = "id"
//        const val EXTRA_CHANNEL_NAME = "name"
//        const val EXTRA_CHANNEL_LOGO_URL = "logoUrl"
//        const val EXTRA_CHANNEL_STREAM_URL = "streamUrl"
//        const val EXTRA_CHANNEL_CATEGORY = "category"
//        const val EXTRA_CHANNEL_LIST = "channel_list"
//        const val EXTRA_CURRENT_INDEX = "current_index"
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val channel = TvChannel(
//            id = intent.getStringExtra(EXTRA_CHANNEL_ID) ?: "",
//            name = intent.getStringExtra(EXTRA_CHANNEL_NAME) ?: "",
//            logoUrl = intent.getStringExtra(EXTRA_CHANNEL_LOGO_URL) ?: "",
//            streamUrl = intent.getStringExtra(EXTRA_CHANNEL_STREAM_URL) ?: "",
//            category = intent.getStringExtra(EXTRA_CHANNEL_CATEGORY) ?: "Live TV"
//        )
//
//        val channelsList = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
//            intent.getParcelableArrayListExtra(EXTRA_CHANNEL_LIST, TvChannel::class.java)
//        } else {
//            @Suppress("DEPRECATION")
//            intent.getParcelableArrayListExtra(EXTRA_CHANNEL_LIST)
//        } ?: arrayListOf(channel)
//
//        val currentIndex = intent.getIntExtra(EXTRA_CURRENT_INDEX, 0)
//
//        setContent {
//            TvPlayerScreen(
//                initialChannel = channel,
//                allChannels = channelsList,
//                initialIndex = currentIndex
//            )
//        }
//    }
//
//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        return when (keyCode) {
//            KeyEvent.KEYCODE_DPAD_RIGHT, KeyEvent.KEYCODE_DPAD_LEFT,
//            KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_DPAD_DOWN,
//            KeyEvent.KEYCODE_CHANNEL_UP, KeyEvent.KEYCODE_CHANNEL_DOWN -> {
//                false // Let the Composable handle these keys
//            }
//            else -> super.onKeyDown(keyCode, event)
//        }
//    }
//}
//
//@Composable
//fun TvPlayerScreen(
//    initialChannel: TvChannel,
//    allChannels: List<TvChannel>,
//    initialIndex: Int
//) {
//    val context = LocalContext.current
//    var currentChannel by remember { mutableStateOf(initialChannel) }
//    var currentIndex by remember { mutableStateOf(initialIndex) }
//    var selectedCategory by remember { mutableStateOf<String?>(null) }
//    var showChannelList by remember { mutableStateOf(false) }
//    val focusRequester = remember { FocusRequester() }
//    val menuFocusRequester = remember { FocusRequester() }
//    val channelListFocusRequester = remember { FocusRequester() }
//
//    // Get unique categories from channels
//    val categories = remember(allChannels) {
//        allChannels.map { it.category }.distinct().sorted()
//    }
//
//    // Predefined main categories with icons
//    val mainCategories = listOf(
//        "Movies" to Icons.Default.Movie,
//        "Sports" to Icons.Default.Sports,
//        "News" to Icons.Default.Info,
//        "Entertainment" to Icons.Default.Tv,
//        "Music" to Icons.Default.MusicNote,
//        "Kids" to Icons.Default.ChildCare,
//        "Lifestyle" to Icons.Default.Favorite,
//        "Religious" to Icons.Default.Church
//    )
//
//    // Filter categories that exist in channels
////    val availableMainCategories = mainCategories.filter { (category, _) ->
////        categories.contains(category) || allChannels.any { it.category == category }
////    }
//    val availableMainCategories = mainCategories.filter { (category, _) ->
//        allChannels.any { it.category.trim().equals(category.trim(), ignoreCase = true) }
//    }
//
//    // Group channels by category
//    val channelsByCategory = remember(allChannels) {
//        allChannels.groupBy { it.category }
//    }
//
//    // Create ExoPlayer instance
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            playWhenReady = true
//            repeatMode = Player.REPEAT_MODE_OFF
//        }
//    }
//
//    // Function to load channel
//    fun loadChannel(streamUrl: String) {
//        try {
//            val mediaItem = MediaItem.fromUri(Uri.parse(streamUrl))
//            exoPlayer.setMediaItem(mediaItem)
//            exoPlayer.prepare()
//            exoPlayer.playWhenReady = true
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    // Load initial channel
//    LaunchedEffect(Unit) {
//        loadChannel(currentChannel.streamUrl)
//    }
//
//    // Handle channel changes
//    LaunchedEffect(currentChannel.streamUrl) {
//        loadChannel(currentChannel.streamUrl)
//    }
//
//    // Release player when composable is disposed
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
//    // Request focus when screen loads
//    LaunchedEffect(Unit) {
//        focusRequester.requestFocus()
//    }
//
//    // Check if streamUrl is empty
//    if (currentChannel.streamUrl.isEmpty()) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = "Invalid stream URL",
//                style = MaterialTheme.typography.bodyLarge,
//                color = Color.White
//            )
//        }
//        return
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .focusRequester(
//                when {
//                    showChannelList -> channelListFocusRequester
//                    selectedCategory != null -> menuFocusRequester
//                    else -> focusRequester
//                }
//            )
//            .onKeyEvent { keyEvent ->
//                when {
//                    // Handle CHANNEL_UP (KEYCODE_CHANNEL_UP = 166) and CHANNEL_DOWN (KEYCODE_CHANNEL_DOWN = 167) for navigation
//                    keyEvent.type == KeyEventType.KeyDown && keyEvent.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_CHANNEL_UP -> {
//                        if (currentIndex < allChannels.size - 1) {
//                            currentIndex++
//                            currentChannel = allChannels[currentIndex]
//                        }
//                        true
//                    }
//                    keyEvent.type == KeyEventType.KeyDown && keyEvent.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_CHANNEL_DOWN -> {
//                        if (currentIndex > 0) {
//                            currentIndex--
//                            currentChannel = allChannels[currentIndex]
//                        }
//                        true
//                    }
//
//                    // Handle number keys directly (0-9) for direct channel navigation
//                    keyEvent.type == KeyEventType.KeyDown && keyEvent.nativeKeyEvent.keyCode in KeyEvent.KEYCODE_0..KeyEvent.KEYCODE_9 -> {
//                        // You can implement direct channel number input here if needed
//                        // For now, just use as alternative navigation
//                        true
//                    }
//
//                    // Show channel list for selected category
//                    selectedCategory != null && keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionDown -> {
//                        showChannelList = true
//                        true
//                    }
//
//                    // Navigate categories with left/right when menu is focused
//                    selectedCategory != null && !showChannelList &&
//                            keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionRight -> {
//                        val currentIndex = availableMainCategories.indexOfFirst { it.first == selectedCategory }
//                        if (currentIndex < availableMainCategories.size - 1) {
//                            selectedCategory = availableMainCategories[currentIndex + 1].first
//                        }
//                        true
//                    }
//                    selectedCategory != null && !showChannelList &&
//                            keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionLeft -> {
//                        val currentIndex = availableMainCategories.indexOfFirst { it.first == selectedCategory }
//                        if (currentIndex > 0) {
//                            selectedCategory = availableMainCategories[currentIndex - 1].first
//                        }
//                        true
//                    }
//
//                    // Close channel list with back button
//                    keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Back -> {
//                        if (showChannelList) {
//                            showChannelList = false
//                            true
//                        } else if (selectedCategory != null) {
//                            selectedCategory = null
//                            true
//                        } else {
//                            false
//                        }
//                    }
//
//                    // Channel navigation when no menu is shown
//                    selectedCategory == null && !showChannelList &&
//                            keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionRight -> {
//                        if (currentIndex < allChannels.size - 1) {
//                            currentIndex++
//                            currentChannel = allChannels[currentIndex]
//                        }
//                        true
//                    }
//                    selectedCategory == null && !showChannelList &&
//                            keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionLeft -> {
//                        if (currentIndex > 0) {
//                            currentIndex--
//                            currentChannel = allChannels[currentIndex]
//                        }
//                        true
//                    }
//                    else -> false
//                }
//            }
//    ) {
//        // AndroidView for ExoPlayer
//        AndroidView(
//            factory = { context ->
//                StyledPlayerView(context).apply {
//                    player = exoPlayer
//                    useController = true
//                    resizeMode = com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
//                    setShowBuffering(StyledPlayerView.SHOW_BUFFERING_WHEN_PLAYING)
//                    keepScreenOn = true
//                    isFocusable = true
//                    isFocusableInTouchMode = true
//                }
//            },
//            update = { view ->
//                view.player = exoPlayer
//            },
//            modifier = Modifier.fillMaxSize()
//        )
//
//        // Top Action Bar with Categories (Always Visible)
//        TopActionBar(
//            categories = availableMainCategories,
//            selectedCategory = selectedCategory,
//            onCategorySelected = { category ->
//                selectedCategory = category
//                showChannelList = false
//            },
//            modifier = Modifier
//                .align(Alignment.TopCenter)
//                .fillMaxWidth()
//                .padding(16.dp)
//        )
//
//        // Channel List Overlay (Shows when category is selected and down pressed)
//        if (selectedCategory != null && showChannelList) {
////            val categoryChannels = channelsByCategory[selectedCategory] ?:
////            allChannels.filter { it.category == selectedCategory }
//            val categoryChannels =
//                allChannels.filter {
//                    it.category.trim().equals(selectedCategory, ignoreCase = true)
//                }
//            ChannelListOverlay(
//                category = selectedCategory!!,
//                channels = categoryChannels,
//                onChannelSelected = { channel ->
//                    val newIndex = allChannels.indexOfFirst { it.id == channel.id }
//                    if (newIndex != -1) {
//                        currentIndex = newIndex
//                        currentChannel = channel
//                        showChannelList = false
//                        selectedCategory = null
//                    }
//                },
//                onClose = {
//                    showChannelList = false
//                },
//                focusRequester = channelListFocusRequester,
//                modifier = Modifier
//                    .align(Alignment.TopCenter)
//                    .padding(top = 100.dp)
//                    .fillMaxWidth()
//                    .height(300.dp)
//            )
//        }
//
//        // Top overlay for channel info (temporary)
//        ChannelInfoOverlay(
//            channel = currentChannel,
//            currentIndex = currentIndex,
//            totalChannels = allChannels.size,
//            showMenu = selectedCategory != null || showChannelList,
//            onNext = {
//                if (currentIndex < allChannels.size - 1) {
//                    currentIndex++
//                    currentChannel = allChannels[currentIndex]
//                }
//            },
//            onPrevious = {
//                if (currentIndex > 0) {
//                    currentIndex--
//                    currentChannel = allChannels[currentIndex]
//                }
//            },
//            modifier = Modifier.align(Alignment.TopCenter)
//        )
//
//        // Permanent bottom right corner logo
//        PermanentChannelLogo(
//            channel = currentChannel,
//            modifier = Modifier.align(Alignment.BottomEnd)
//        )
//    }
//}
//
//@Composable
//fun TopActionBar(
//    categories: List<Pair<String, androidx.compose.ui.graphics.vector.ImageVector>>,
//    selectedCategory: String?,
//    onCategorySelected: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val listState = rememberLazyListState()
//    var focusedIndex by remember { mutableStateOf(0) }
//
//    // Auto-scroll to selected category
//    LaunchedEffect(selectedCategory) {
//        val index = categories.indexOfFirst { it.first == selectedCategory }
//        if (index >= 0) {
//            listState.animateScrollToItem(index)
//        }
//    }
//
//    Surface(
//        modifier = modifier
//            .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp)),
//        color = Color.Black.copy(alpha = 0.8f),
//        shape = RoundedCornerShape(8.dp)
//    ) {
//        LazyRow(
//            state = listState,
//            horizontalArrangement = Arrangement.spacedBy(8.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 8.dp, vertical = 12.dp)
//        ) {
//            items(categories.size) { index ->
//                val (category, icon) = categories[index]
//                ActionBarItem(
//                    title = category,
//                    icon = icon,
//                    isSelected = category == selectedCategory,
//                    isFocused = index == focusedIndex,
//                    onFocus = { focusedIndex = index },
//                    onClick = { onCategorySelected(category) }
//                )
//            }
//        }
//    }
//}
////
////@Composable
////fun ActionBarItem(
////    title: String,
////    icon: androidx.compose.ui.graphics.vector.ImageVector,
////    isSelected: Boolean,
////    isFocused: Boolean,
////    onFocus: () -> Unit,
////    onClick: () -> Unit
////) {
////    Surface(
////        modifier = Modifier
////            .onFocusChanged { if (it.isFocused) onFocus() }
////            .focusable()
////            .clickable { onClick() }
////            .then(
////                if (isSelected || isFocused) {
////                    Modifier.border(2.dp, Color.Red, RoundedCornerShape(20.dp))
////                } else {
////                    Modifier
////                }
////            ),
////        color = if (isSelected) Color.Red.copy(alpha = 0.3f) else Color.Transparent,
////        shape = RoundedCornerShape(20.dp)
////    ) {
////        Row(
////            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
////            verticalAlignment = Alignment.CenterVertically
////        ) {
////            Icon(
////                imageVector = icon,
////                contentDescription = title,
////                tint = if (isSelected || isFocused) Color.Red else Color.White,
////                modifier = Modifier.size(20.dp)
////            )
////
////            Spacer(modifier = Modifier.width(8.dp))
////
////            Text(
////                text = title,
////                color = if (isSelected || isFocused) Color.Red else Color.White,
////                fontSize = 14.sp,
////                fontWeight = if (isSelected || isFocused) FontWeight.Bold else FontWeight.Normal
////            )
////        }
////    }
////}
//
//@Composable
//fun ActionBarItem(
//    title: String,
//    icon: ImageVector,
//    isSelected: Boolean,
//    isFocused: Boolean,
//    onFocus: () -> Unit,
//    onClick: () -> Unit
//) {
//
//    Surface(
//        modifier = Modifier
//            .onFocusChanged {
//                if (it.isFocused) onFocus()
//            }
//
//            .focusable()
//
//            // Remote OK support
//            .onKeyEvent { event ->
//                if (event.type == KeyEventType.KeyDown &&
//                    event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DPAD_CENTER
//                ) {
//                    onClick()
//                    true
//                } else {
//                    false
//                }
//            }
//
//            // Mouse click support
//            .pointerInput(Unit) {
//                detectTapGestures(
//                    onTap = {
//                        onClick()
//                    }
//                )
//            }
//
//            .then(
//                if (isSelected || isFocused) {
//                    Modifier.border(2.dp, Color.Red, RoundedCornerShape(20.dp))
//                } else {
//                    Modifier
//                }
//            ),
//
//        color = if (isSelected) Color.Red.copy(alpha = 0.3f) else Color.Transparent,
//        shape = RoundedCornerShape(20.dp)
//    ) {
//
//        Row(
//            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//
//            Icon(
//                imageVector = icon,
//                contentDescription = title,
//                tint = if (isSelected || isFocused) Color.Red else Color.White,
//                modifier = Modifier.size(20.dp)
//            )
//
//            Spacer(modifier = Modifier.width(8.dp))
//
//            Text(
//                text = title,
//                color = if (isSelected || isFocused) Color.Red else Color.White,
//                fontSize = 14.sp,
//                fontWeight = if (isSelected || isFocused) FontWeight.Bold else FontWeight.Normal
//            )
//        }
//    }
//}
//@Composable
//fun ChannelListOverlay(
//    category: String,
//    channels: List<TvChannel>,
//    onChannelSelected: (TvChannel) -> Unit,
//    onClose: () -> Unit,
//    focusRequester: FocusRequester,
//    modifier: Modifier = Modifier
//) {
//
//    val listState = rememberLazyListState()
//    var focusedIndex by remember { mutableStateOf(0) }
//
//    LaunchedEffect(Unit) {
//        focusRequester.requestFocus()
//    }
//
//    Surface(
//        modifier = modifier,
//        color = Color.Black.copy(alpha = 0.9f),
//        shape = RoundedCornerShape(8.dp)
//    ) {
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//
//                Text(
//                    text = "$category Channels",
//                    color = Color.Red,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold
//                )
//
//                IconButton(onClick = onClose) {
//                    Icon(Icons.Default.Close, "", tint = Color.White)
//                }
//            }
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            LazyRow(
//                state = listState,
//                horizontalArrangement = Arrangement.spacedBy(12.dp),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .focusRequester(focusRequester)
//                    .weight(1f)
//            ) {
//
//                items(channels.size) { index ->
//
//                    val channel = channels[index]
//
//                    ChannelListItem(
//                        channel = channel,
//                        isFocused = index == focusedIndex,
//                        onFocus = { focusedIndex = index },
//                        onClick = { onChannelSelected(channel) }
//                    )
//                }
//            }
//        }
//    }
//}
//@Composable
//fun ChannelListItem(
//    channel: TvChannel,
//    isFocused: Boolean,
//    onFocus: () -> Unit,
//    onClick: () -> Unit
//) {
//
//    Surface(
//        modifier = Modifier
//            .width(140.dp)
//
//            // Focus detect
//            .onFocusChanged {
//                if (it.isFocused) onFocus()
//            }
//
//            .focusable()
//
//            // Remote OK button
//            .onKeyEvent { event ->
//                if (event.type == KeyEventType.KeyDown &&
//                    event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DPAD_CENTER
//                ) {
//                    onClick()
//                    true
//                } else {
//                    false
//                }
//            }
//
//            // Mouse click support
//            .clickable {
//                onClick()
//            }
//
//            .then(
//                if (isFocused) {
//                    Modifier.border(2.dp, Color.Red, RoundedCornerShape(6.dp))
//                } else {
//                    Modifier
//                }
//            ),
//
//        color = Color.DarkGray,
//        shape = RoundedCornerShape(6.dp)
//    ) {
//
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.padding(12.dp)
//        ) {
//
//            Image(
//                painter = rememberAsyncImagePainter(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(channel.logoUrl)
//                        .crossfade(true)
//                        .error(R.drawable.logo)
//                        .build()
//                ),
//                contentDescription = channel.name,
//                modifier = Modifier
//                    .size(80.dp)
//                    .clip(RoundedCornerShape(4.dp))
//                    .background(Color.DarkGray),
//                contentScale = ContentScale.Fit
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = channel.name,
//                color = if (isFocused) Color.Red else Color.White,
//                fontSize = 14.sp,
//                fontWeight = if (isFocused) FontWeight.Bold else FontWeight.Normal,
//                maxLines = 2
//            )
//
//            Text(
//                text = channel.category,
//                color = Color.Gray,
//                fontSize = 12.sp
//            )
//        }
//    }
//}
////@Composable
////fun ChannelListItem(
////    channel: TvChannel,
////    isFocused: Boolean,
////    onFocus: () -> Unit,
////    onClick: () -> Unit
////) {
////
////    Surface(
////        modifier = Modifier
////            .width(140.dp)
////            .onFocusChanged { if (it.isFocused) onFocus() }
////            .focusable()
////
////            // Remote OK button support
////            .onKeyEvent {
////
////                if (it.type == KeyEventType.KeyDown &&
////                    (it.key == Key.Enter ||
////                            it.key == Key.NumPadEnter ||
////                            it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
////                ) {
////
////                    onClick()
////                    true
////
////                } else {
////                    false
////                }
////            }
////
////            .clickable { onClick() }
////
////            .then(
////                if (isFocused) {
////                    Modifier.border(2.dp, Color.Red, RoundedCornerShape(6.dp))
////                } else {
////                    Modifier
////                }
////            ),
////
////        color = Color.DarkGray,
////        shape = RoundedCornerShape(6.dp)
////    ) {
////
////        Column(
////            horizontalAlignment = Alignment.CenterHorizontally,
////            modifier = Modifier.padding(12.dp)
////        ) {
////
////            Image(
////                painter = rememberAsyncImagePainter(
////                    model = ImageRequest.Builder(LocalContext.current)
////                        .data(channel.logoUrl)
////                        .crossfade(true)
////                        .error(R.drawable.logo)
////                        .build()
////                ),
////
////                contentDescription = channel.name,
////
////                modifier = Modifier
////                    .size(80.dp)
////                    .clip(RoundedCornerShape(4.dp))
////                    .background(Color.DarkGray),
////
////                contentScale = ContentScale.Fit
////            )
////
////            Spacer(modifier = Modifier.height(8.dp))
////
////            Text(
////                text = channel.name,
////                color = if (isFocused) Color.Red else Color.White,
////                fontSize = 14.sp,
////                fontWeight = if (isFocused) FontWeight.Bold else FontWeight.Normal,
////                maxLines = 2
////            )
////
////            Text(
////                text = channel.category,
////                color = Color.Gray,
////                fontSize = 12.sp
////            )
////        }
////    }
////}
//
//
//@Composable
//fun PermanentChannelLogo(
//    channel: TvChannel,
//    modifier: Modifier = Modifier
//) {
//    Surface(
//        modifier = modifier
//            .padding(24.dp)
//            .width(150.dp)
//            .height(80.dp)
//            .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp)),
//        color = Color.Black.copy(alpha = 0.6f),
//        shape = RoundedCornerShape(8.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(8.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//
//
//            // Right logo (local only)
//            Image(
//                painter = painterResource(id = R.drawable.logo),
//                contentDescription = "App Logo",
//                modifier = Modifier
//                    .size(150.dp)
//                    .clip(RoundedCornerShape(4.dp)),
//                contentScale = ContentScale.Fit
//            )
//        }
//    }
//}
//
//@Composable
//fun ChannelInfoOverlay(
//    channel: TvChannel,
//    currentIndex: Int,
//    totalChannels: Int,
//    showMenu: Boolean,
//    onNext: () -> Unit,
//    onPrevious: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    var showOverlay by remember { mutableStateOf(true) }
//
//    // Show overlay when channel changes and auto-hide after 3 seconds
//    LaunchedEffect(channel) {
//        showOverlay = true
//        delay(3000)
//        showOverlay = false
//    }
//
//    if (showOverlay && !showMenu) {
//        Surface(
//            modifier = modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            color = Color.Black.copy(alpha = 0.7f),
//            shape = RoundedCornerShape(8.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    // Channel logo with fallback to local image
//                    Image(
//                        painter = rememberAsyncImagePainter(
//                            model = ImageRequest.Builder(LocalContext.current)
//                                .data(channel.logoUrl)
//                                .crossfade(true)
//                                .error(R.drawable.logo)
//                                .build()
//                        ),
//                        contentDescription = channel.name,
//                        modifier = Modifier
//                            .size(32.dp)
//                            .clip(RoundedCornerShape(4.dp))
//                            .background(Color.DarkGray),
//                        contentScale = ContentScale.Fit
//                    )
//
//                    Spacer(modifier = Modifier.width(12.dp))
//
//                    Column {
//                        Text(
//                            text = channel.name,
//                            color = Color.White,
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                        Text(
//                            text = channel.category,
//                            color = Color.White.copy(alpha = 0.7f),
//                            fontSize = 12.sp
//                        )
//                    }
//                }
//
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        imageVector = Icons.Default.ArrowLeft,
//                        contentDescription = "Previous",
//                        tint = Color.White,
//                        modifier = Modifier.size(20.dp)
//                    )
//
//                    Text(
//                        text = "${currentIndex + 1}/${totalChannels}",
//                        color = Color.White,
//                        fontSize = 14.sp,
//                        modifier = Modifier.padding(horizontal = 8.dp)
//                    )
//
//                    Icon(
//                        imageVector = Icons.Default.ArrowRight,
//                        contentDescription = "Next",
//                        tint = Color.White,
//                        modifier = Modifier.size(20.dp)
//                    )
//                }
//            }
//        }
//    }
//}
//
package com.bacbpl.iptv.jetStram.presentation.screens.player
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bacbpl.iptv.R
import com.bacbpl.iptv.jetStram.data.entities.TvChannel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.delay

class TvPlayer : ComponentActivity() {
    companion object {
        const val EXTRA_CHANNEL_ID = "id"
        const val EXTRA_CHANNEL_NAME = "name"
        const val EXTRA_CHANNEL_LOGO_URL = "logoUrl"
        const val EXTRA_CHANNEL_STREAM_URL = "streamUrl"
        const val EXTRA_CHANNEL_CATEGORY = "category"
        const val EXTRA_CHANNEL_LIST = "channel_list"
        const val EXTRA_CURRENT_INDEX = "current_index"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val channel = TvChannel(
            id = intent.getStringExtra(EXTRA_CHANNEL_ID) ?: "",
            name = intent.getStringExtra(EXTRA_CHANNEL_NAME) ?: "",
            logoUrl = intent.getStringExtra(EXTRA_CHANNEL_LOGO_URL) ?: "",
            streamUrl = intent.getStringExtra(EXTRA_CHANNEL_STREAM_URL) ?: "",
            category = intent.getStringExtra(EXTRA_CHANNEL_CATEGORY) ?: "Live TV"
        )

        val channelsList = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra(EXTRA_CHANNEL_LIST, TvChannel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra(EXTRA_CHANNEL_LIST)
        } ?: arrayListOf(channel)

        val currentIndex = intent.getIntExtra(EXTRA_CURRENT_INDEX, 0)
        setContent {
            TvPlayerScreen(
                initialChannel = channel,
                allChannels = channelsList,
                initialIndex = currentIndex
            )
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_DPAD_RIGHT, KeyEvent.KEYCODE_DPAD_LEFT,
            KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_DPAD_DOWN,
            KeyEvent.KEYCODE_CHANNEL_UP, KeyEvent.KEYCODE_CHANNEL_DOWN -> {
                false // Let the Composable handle these keys
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }
}
@Composable
fun TvPlayerScreen(
    initialChannel: TvChannel,
    allChannels: List<TvChannel>,
    initialIndex: Int
) {
    val context = LocalContext.current
    var currentChannel by remember { mutableStateOf(initialChannel) }
    var currentIndex by remember { mutableStateOf(initialIndex) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var showGrid by remember { mutableStateOf(false) }
    var showChannelPreview by remember { mutableStateOf(false) }
    var previewDirection by remember { mutableStateOf("") }
    var showMenuBar by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val menuFocusRequester = remember { FocusRequester() }
    val gridFocusRequester = remember { FocusRequester() }

    // Get next and previous channels
    val nextChannel = remember(currentIndex, allChannels) {
        if (currentIndex < allChannels.size - 1) allChannels[currentIndex + 1] else null
    }

    val previousChannel = remember(currentIndex, allChannels) {
        if (currentIndex > 0) allChannels[currentIndex - 1] else null
    }

    // Predefined main categories with icons
    val mainCategories = listOf(
        "Movies" to Icons.Default.Movie,
        "Sports" to Icons.Default.SportsSoccer,
        "News" to Icons.Default.Info,
        "Entertainment" to Icons.Default.Tv,
        "Music" to Icons.Default.MusicNote,
        "Kids" to Icons.Default.ChildCare,
        "Lifestyle" to Icons.Default.Favorite,
        "Religious" to Icons.Default.Church
    )

    // Filter categories that exist in channels
    val availableMainCategories = mainCategories.filter { (category, _) ->
        allChannels.any { it.category.trim().equals(category.trim(), ignoreCase = true) }
    }

    // Add focus states for menu items
    var menuFocusedIndex by remember { mutableStateOf(0) }
    val menuItemsFocusRequesters = remember { List(availableMainCategories.size) { FocusRequester() } }

    // Create ExoPlayer instance
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            playWhenReady = true
            repeatMode = Player.REPEAT_MODE_OFF
        }
    }

    // Function to load channel
    fun loadChannel(streamUrl: String) {
        try {
            val mediaItem = MediaItem.fromUri(Uri.parse(streamUrl))
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Load initial channel
    LaunchedEffect(Unit) {
        loadChannel(currentChannel.streamUrl)
    }

    // Handle channel changes
    LaunchedEffect(currentChannel.streamUrl) {
        loadChannel(currentChannel.streamUrl)
    }

    // Release player when composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.run {
                playWhenReady = false
                stop()
                release()
            }
        }
    }

    // Request focus when screen loads
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    // Auto-hide channel preview
    LaunchedEffect(showChannelPreview) {
        if (showChannelPreview) {
            delay(2000)
            showChannelPreview = false
        }
    }

    // Auto-hide menu bar after inactivity
    LaunchedEffect(showMenuBar) {
        if (showMenuBar) {
            delay(5000) // Hide after 5 seconds of inactivity
            showMenuBar = false
            selectedCategory = null
            showGrid = false
        }
    }

    // Handle menu item focus
    LaunchedEffect(selectedCategory) {
        if (selectedCategory != null && !showGrid) {
            val index = availableMainCategories.indexOfFirst { it.first == selectedCategory }
            if (index >= 0) {
                menuFocusedIndex = index
                menuItemsFocusRequesters.getOrNull(index)?.requestFocus()
            }
        }
    }

    // Check if streamUrl is empty
    if (currentChannel.streamUrl.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Invalid stream URL",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .focusRequester(
                when {
                    showGrid -> gridFocusRequester
                    selectedCategory != null -> menuItemsFocusRequesters.getOrNull(menuFocusedIndex) ?: menuFocusRequester
                    else -> focusRequester
                }
            )
            .onPreviewKeyEvent { keyEvent ->
                // Handle key events at preview level for better focus management
                when {
                    // Show menu bar when UP is pressed
                    keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionUp -> {
                        if (!showMenuBar) {
                            showMenuBar = true
                            // If there are available categories, select the first one
                            if (availableMainCategories.isNotEmpty()) {
                                selectedCategory = availableMainCategories[0].first
                                menuFocusedIndex = 0
                                menuItemsFocusRequesters[0].requestFocus()
                            }
                            true
                        } else if (showGrid) {
                            // If grid is showing, move focus to menu and hide grid
                            showGrid = false
                            selectedCategory = availableMainCategories.getOrNull(menuFocusedIndex)?.first
                            menuItemsFocusRequesters.getOrNull(menuFocusedIndex)?.requestFocus()
                            true
                        } else if (selectedCategory != null) {
                            // Already have menu focused, keep it
                            menuItemsFocusRequesters.getOrNull(menuFocusedIndex)?.requestFocus()
                            true
                        } else {
                            // No menu selected, select the first category and focus it
                            if (availableMainCategories.isNotEmpty()) {
                                selectedCategory = availableMainCategories[0].first
                                menuFocusedIndex = 0
                                menuItemsFocusRequesters[0].requestFocus()
                                true
                            } else {
                                false
                            }
                        }
                    }
                    keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionDown -> {
                        if (selectedCategory != null && !showGrid) {
                            // Show grid when down pressed
                            showGrid = true
                            true
                        } else {
                            false
                        }
                    }
                    // Hide menu bar when pressing BACK while menu is visible
                    keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Back -> {
                        if (showMenuBar && !showGrid && selectedCategory == null) {
                            showMenuBar = false
                            true
                        } else {
                            false
                        }
                    }
                    else -> false
                }
            }
            .onKeyEvent { keyEvent ->
                when {
                    // Handle back button to stop video and exit
                    keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Back -> {
                        if (showGrid) {
                            showGrid = false
                            // Return focus to menu
                            menuItemsFocusRequesters.getOrNull(menuFocusedIndex)?.requestFocus()
                            true
                        } else if (selectedCategory != null) {
                            selectedCategory = null
                            focusRequester.requestFocus()
                            true
                        } else if (showMenuBar) {
                            showMenuBar = false
                            true
                        } else {
                            // Stop video and finish activity when back is pressed
                            exoPlayer.run {
                                playWhenReady = false
                                stop()
                                release()
                            }
                            (context as? android.app.Activity)?.finish()
                            true
                        }
                    }

                    // Handle CHANNEL_UP and CHANNEL_DOWN
                    keyEvent.type == KeyEventType.KeyDown && keyEvent.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_CHANNEL_UP -> {
                        if (currentIndex < allChannels.size - 1) {
                            previewDirection = "next"
                            showChannelPreview = true
                            currentIndex++
                            currentChannel = allChannels[currentIndex]
                            // Hide menu bar when changing channels
                            if (showMenuBar) {
                                showMenuBar = false
                                selectedCategory = null
                                showGrid = false
                            }
                        }
                        true
                    }
                    keyEvent.type == KeyEventType.KeyDown && keyEvent.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_CHANNEL_DOWN -> {
                        if (currentIndex > 0) {
                            previewDirection = "previous"
                            showChannelPreview = true
                            currentIndex--
                            currentChannel = allChannels[currentIndex]
                            // Hide menu bar when changing channels
                            if (showMenuBar) {
                                showMenuBar = false
                                selectedCategory = null
                                showGrid = false
                            }
                        }
                        true
                    }

                    // Navigate categories with left/right when a category is selected
                    selectedCategory != null && !showGrid &&
                            keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionRight -> {
                        val currentCatIndex = availableMainCategories.indexOfFirst { it.first == selectedCategory }
                        if (currentCatIndex < availableMainCategories.size - 1) {
                            val newIndex = currentCatIndex + 1
                            selectedCategory = availableMainCategories[newIndex].first
                            menuFocusedIndex = newIndex
                            menuItemsFocusRequesters.getOrNull(newIndex)?.requestFocus()
                        }
                        true
                    }
                    selectedCategory != null && !showGrid &&
                            keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionLeft -> {
                        val currentCatIndex = availableMainCategories.indexOfFirst { it.first == selectedCategory }
                        if (currentCatIndex > 0) {
                            val newIndex = currentCatIndex - 1
                            selectedCategory = availableMainCategories[newIndex].first
                            menuFocusedIndex = newIndex
                            menuItemsFocusRequesters.getOrNull(newIndex)?.requestFocus()
                        }
                        true
                    }

                    // Channel navigation with left/right when:
                    // 1. No category selected AND
                    // 2. Either menu bar is hidden OR menu bar is visible but no category selected (menu bar just showing)
                    (selectedCategory == null && !showGrid && (!showMenuBar || (showMenuBar && selectedCategory == null))) &&
                            keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionRight -> {
                        if (currentIndex < allChannels.size - 1) {
                            previewDirection = "next"
                            showChannelPreview = true
                            currentIndex++
                            currentChannel = allChannels[currentIndex]
                            // If menu bar is visible, keep it visible but don't let it block channel change
                        }
                        true
                    }
                    (selectedCategory == null && !showGrid && (!showMenuBar || (showMenuBar && selectedCategory == null))) &&
                            keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionLeft -> {
                        if (currentIndex > 0) {
                            previewDirection = "previous"
                            showChannelPreview = true
                            currentIndex--
                            currentChannel = allChannels[currentIndex]
                            // If menu bar is visible, keep it visible but don't let it block channel change
                        }
                        true
                    }
                    else -> false
                }
            }
//            .onKeyEvent { keyEvent ->
//                when {
//                    // Handle back button to stop video and exit
//                    keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Back -> {
//                        if (showGrid) {
//                            showGrid = false
//                            // Return focus to menu
//                            menuItemsFocusRequesters.getOrNull(menuFocusedIndex)?.requestFocus()
//                            true
//                        } else if (selectedCategory != null) {
//                            selectedCategory = null
//                            focusRequester.requestFocus()
//                            true
//                        } else if (showMenuBar) {
//                            showMenuBar = false
//                            true
//                        } else {
//                            // Stop video and finish activity when back is pressed
//                            exoPlayer.run {
//                                playWhenReady = false
//                                stop()
//                                release()
//                            }
//                            (context as? android.app.Activity)?.finish()
//                            true
//                        }
//                    }
//
//                    // Handle CHANNEL_UP and CHANNEL_DOWN
//                    keyEvent.type == KeyEventType.KeyDown && keyEvent.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_CHANNEL_UP -> {
//                        if (currentIndex < allChannels.size - 1) {
//                            previewDirection = "next"
//                            showChannelPreview = true
//                            currentIndex++
//                            currentChannel = allChannels[currentIndex]
//                            // Hide menu bar when changing channels
//                            if (showMenuBar) {
//                                showMenuBar = false
//                                selectedCategory = null
//                                showGrid = false
//                            }
//                        }
//                        true
//                    }
//                    keyEvent.type == KeyEventType.KeyDown && keyEvent.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_CHANNEL_DOWN -> {
//                        if (currentIndex > 0) {
//                            previewDirection = "previous"
//                            showChannelPreview = true
//                            currentIndex--
//                            currentChannel = allChannels[currentIndex]
//                            // Hide menu bar when changing channels
//                            if (showMenuBar) {
//                                showMenuBar = false
//                                selectedCategory = null
//                                showGrid = false
//                            }
//                        }
//                        true
//                    }
//
//                    // Navigate categories with left/right when menu is focused
//                    selectedCategory != null && !showGrid &&
//                            keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionRight -> {
//                        val currentCatIndex = availableMainCategories.indexOfFirst { it.first == selectedCategory }
//                        if (currentCatIndex < availableMainCategories.size - 1) {
//                            val newIndex = currentCatIndex + 1
//                            selectedCategory = availableMainCategories[newIndex].first
//                            menuFocusedIndex = newIndex
//                            menuItemsFocusRequesters.getOrNull(newIndex)?.requestFocus()
//                        }
//                        true
//                    }
//                    selectedCategory != null && !showGrid &&
//                            keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionLeft -> {
//                        val currentCatIndex = availableMainCategories.indexOfFirst { it.first == selectedCategory }
//                        if (currentCatIndex > 0) {
//                            val newIndex = currentCatIndex - 1
//                            selectedCategory = availableMainCategories[newIndex].first
//                            menuFocusedIndex = newIndex
//                            menuItemsFocusRequesters.getOrNull(newIndex)?.requestFocus()
//                        }
//                        true
//                    }
//
//                    // Channel navigation when no menu is shown
//                    selectedCategory == null && !showGrid && !showMenuBar &&
//                            keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionRight -> {
//                        if (currentIndex < allChannels.size - 1) {
//                            previewDirection = "next"
//                            showChannelPreview = true
//                            currentIndex++
//                            currentChannel = allChannels[currentIndex]
//                        }
//                        true
//                    }
//                    selectedCategory == null && !showGrid && !showMenuBar &&
//                            keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionLeft -> {
//                        if (currentIndex > 0) {
//                            previewDirection = "previous"
//                            showChannelPreview = true
//                            currentIndex--
//                            currentChannel = allChannels[currentIndex]
//                        }
//                        true
//                    }
//                    else -> false
//                }
//            }
    ) {
        // AndroidView for ExoPlayer
        AndroidView(
            factory = { context ->
                StyledPlayerView(context).apply {
                    player = exoPlayer
                    useController = true
                    resizeMode = com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
                    setShowBuffering(StyledPlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                    keepScreenOn = true
                    isFocusable = true
                    isFocusableInTouchMode = true
                }
            },
            update = { view ->
                view.player = exoPlayer
            },
            modifier = Modifier.fillMaxSize()
        )

        // Top Action Bar with Categories - Only visible when showMenuBar is true
        if (showMenuBar) {
            TopActionBar(
                categories = availableMainCategories,
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    selectedCategory = category
                    showGrid = false
                    val index = availableMainCategories.indexOfFirst { it.first == category }
                    if (index >= 0) {
                        menuFocusedIndex = index
                    }
                },
                focusRequesters = menuItemsFocusRequesters,
                focusedIndex = menuFocusedIndex,
                onFocusChanged = { index ->
                    menuFocusedIndex = index
                    if (index >= 0 && index < availableMainCategories.size) {
                        selectedCategory = availableMainCategories[index].first
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(5.dp)
            )
        }

        // Channel Grid (Shows when category is selected and down pressed)
        if (selectedCategory != null && showGrid) {
            val categoryChannels = allChannels.filter {
                it.category.trim().equals(selectedCategory, ignoreCase = true)
            }

            ChannelGrid(
                channels = categoryChannels,
                onChannelSelected = { channel ->
                    val newIndex = allChannels.indexOfFirst { it.id == channel.id }
                    if (newIndex != -1) {
                        currentIndex = newIndex
                        currentChannel = channel
                        showGrid = false
                        selectedCategory = null
                        showMenuBar = false // Hide menu bar after selection
                        focusRequester.requestFocus()
                    }
                },
                onClose = {
                    showGrid = false
                    // Return focus to menu
                    menuItemsFocusRequesters.getOrNull(menuFocusedIndex)?.requestFocus()
                },
                focusRequester = gridFocusRequester,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 100.dp)
                    .fillMaxWidth()
                    .height(500.dp)
            )
        }

        // Top overlay for channel info (temporary)
        ChannelInfoOverlay(
            channel = currentChannel,
            currentIndex = currentIndex,
            totalChannels = allChannels.size,
            showMenu = selectedCategory != null || showGrid || showMenuBar,
            onNext = {
                if (currentIndex < allChannels.size - 1) {
                    previewDirection = "next"
                    showChannelPreview = true
                    currentIndex++
                    currentChannel = allChannels[currentIndex]
                }
            },
            onPrevious = {
                if (currentIndex > 0) {
                    previewDirection = "previous"
                    showChannelPreview = true
                    currentIndex--
                    currentChannel = allChannels[currentIndex]
                }
            },
            modifier = Modifier.align(Alignment.TopCenter)
        )

        // Channel Preview Overlay
        if (showChannelPreview) {
            ChannelPreviewOverlay(
                currentChannel = currentChannel,
                nextChannel = nextChannel,
                previousChannel = previousChannel,
                direction = previewDirection,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

        // Show swipe overlay only when menu/grid is not visible
        if (selectedCategory == null && !showGrid && !showChannelPreview && !showMenuBar) {
            SwipeChannelOverlay(
                nextChannel = nextChannel,
                previousChannel = previousChannel,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

        // Permanent bottom right corner logo
        PermanentChannelLogo(
            channel = currentChannel,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}
//@Composable
//fun TvPlayerScreen(
//    initialChannel: TvChannel,
//    allChannels: List<TvChannel>,
//    initialIndex: Int
//) {
//    val context = LocalContext.current
//    var currentChannel by remember { mutableStateOf(initialChannel) }
//    var currentIndex by remember { mutableStateOf(initialIndex) }
//    var selectedCategory by remember { mutableStateOf<String?>(null) }
//    var showGrid by remember { mutableStateOf(false) }
//    var showChannelPreview by remember { mutableStateOf(false) }
//    var previewDirection by remember { mutableStateOf("") } // "next" or "previous"
//    val focusRequester = remember { FocusRequester() }
//    val menuFocusRequester = remember { FocusRequester() }
//    val gridFocusRequester = remember { FocusRequester() }
//
//    // Get next and previous channels
//    val nextChannel = remember(currentIndex, allChannels) {
//        if (currentIndex < allChannels.size - 1) allChannels[currentIndex + 1] else null
//    }
//
//    val previousChannel = remember(currentIndex, allChannels) {
//        if (currentIndex > 0) allChannels[currentIndex - 1] else null
//    }
//
//    // Predefined main categories with icons
//    val mainCategories = listOf(
//        "Movies" to Icons.Default.Movie,
//        "Sports" to Icons.Default.SportsSoccer,
//        "News" to Icons.Default.Info,
//        "Entertainment" to Icons.Default.Tv,
//        "Music" to Icons.Default.MusicNote,
//        "Kids" to Icons.Default.ChildCare,
//        "Lifestyle" to Icons.Default.Favorite,
//        "Religious" to Icons.Default.Church
//    )
//
//    // Filter categories that exist in channels
//    val availableMainCategories = mainCategories.filter { (category, _) ->
//        allChannels.any { it.category.trim().equals(category.trim(), ignoreCase = true) }
//    }
//
//    // Add focus states for menu items
//    var menuFocusedIndex by remember { mutableStateOf(0) }
//    val menuItemsFocusRequesters = remember { List(availableMainCategories.size) { FocusRequester() } }
//
//    // Create ExoPlayer instance
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            playWhenReady = true
//            repeatMode = Player.REPEAT_MODE_OFF
//        }
//    }
//
//    // Function to load channel
//    fun loadChannel(streamUrl: String) {
//        try {
//            val mediaItem = MediaItem.fromUri(Uri.parse(streamUrl))
//            exoPlayer.setMediaItem(mediaItem)
//            exoPlayer.prepare()
//            exoPlayer.playWhenReady = true
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    // Load initial channel
//    LaunchedEffect(Unit) {
//        loadChannel(currentChannel.streamUrl)
//    }
//
//    // Handle channel changes
//    LaunchedEffect(currentChannel.streamUrl) {
//        loadChannel(currentChannel.streamUrl)
//    }
//
//    // Release player when composable is disposed
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
//    // Request focus when screen loads
//    LaunchedEffect(Unit) {
//        focusRequester.requestFocus()
//    }
//
//    // Auto-hide channel preview
//    LaunchedEffect(showChannelPreview) {
//        if (showChannelPreview) {
//            delay(2000)
//            showChannelPreview = false
//        }
//    }
//
//    // Handle menu item focus
//    LaunchedEffect(selectedCategory) {
//        if (selectedCategory != null && !showGrid) {
//            val index = availableMainCategories.indexOfFirst { it.first == selectedCategory }
//            if (index >= 0) {
//                menuFocusedIndex = index
//                menuItemsFocusRequesters.getOrNull(index)?.requestFocus()
//            }
//        }
//    }
//
//    // Check if streamUrl is empty
//    if (currentChannel.streamUrl.isEmpty()) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = "Invalid stream URL",
//                style = MaterialTheme.typography.bodyLarge,
//                color = Color.White
//            )
//        }
//        return
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .focusRequester(
//                when {
//                    showGrid -> gridFocusRequester
//                    selectedCategory != null -> menuItemsFocusRequesters.getOrNull(menuFocusedIndex) ?: menuFocusRequester
//                    else -> focusRequester
//                }
//            )
//            .onPreviewKeyEvent { keyEvent ->
//                // Handle key events at preview level for better focus management
//                when {
//                    keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionUp -> {
//                        if (showGrid) {
//                            // If grid is showing, move focus to menu and hide grid
//                            showGrid = false
//                            selectedCategory = availableMainCategories.getOrNull(menuFocusedIndex)?.first
//                            menuItemsFocusRequesters.getOrNull(menuFocusedIndex)?.requestFocus()
//                            true
//                        } else if (selectedCategory != null) {
//                            // Already have menu focused, keep it
//                            menuItemsFocusRequesters.getOrNull(menuFocusedIndex)?.requestFocus()
//                            true
//                        } else {
//                            // No menu selected, select the first category and focus it
//                            if (availableMainCategories.isNotEmpty()) {
//                                selectedCategory = availableMainCategories[0].first
//                                menuFocusedIndex = 0
//                                menuItemsFocusRequesters[0].requestFocus()
//                                true
//                            } else {
//                                false
//                            }
//                        }
//                    }
//                    keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionDown -> {
//                        if (selectedCategory != null && !showGrid) {
//                            // Show grid when down pressed
//                            showGrid = true
//                            true
//                        } else {
//                            false
//                        }
//                    }
//                    else -> false
//                }
//            }
//            .onKeyEvent { keyEvent ->
//                when {
//                    // Handle back button to stop video and exit
//                    keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Back -> {
//                        if (showGrid) {
//                            showGrid = false
//                            // Return focus to menu
//                            menuItemsFocusRequesters.getOrNull(menuFocusedIndex)?.requestFocus()
//                            true
//                        } else if (selectedCategory != null) {
//                            selectedCategory = null
//                            focusRequester.requestFocus()
//                            true
//                        } else {
//                            // Stop video and finish activity when back is pressed
//                            exoPlayer.run {
//                                playWhenReady = false
//                                stop()
//                                release()
//                            }
//                            (context as? android.app.Activity)?.finish()
//                            true
//                        }
//                    }
//
//                    // Handle CHANNEL_UP and CHANNEL_DOWN
//                    keyEvent.type == KeyEventType.KeyDown && keyEvent.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_CHANNEL_UP -> {
//                        if (currentIndex < allChannels.size - 1) {
//                            previewDirection = "next"
//                            showChannelPreview = true
//                            currentIndex++
//                            currentChannel = allChannels[currentIndex]
//                        }
//                        true
//                    }
//                    keyEvent.type == KeyEventType.KeyDown && keyEvent.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_CHANNEL_DOWN -> {
//                        if (currentIndex > 0) {
//                            previewDirection = "previous"
//                            showChannelPreview = true
//                            currentIndex--
//                            currentChannel = allChannels[currentIndex]
//                        }
//                        true
//                    }
//
//                    // Navigate categories with left/right when menu is focused
//                    selectedCategory != null && !showGrid &&
//                            keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionRight -> {
//                        val currentCatIndex = availableMainCategories.indexOfFirst { it.first == selectedCategory }
//                        if (currentCatIndex < availableMainCategories.size - 1) {
//                            val newIndex = currentCatIndex + 1
//                            selectedCategory = availableMainCategories[newIndex].first
//                            menuFocusedIndex = newIndex
//                            menuItemsFocusRequesters.getOrNull(newIndex)?.requestFocus()
//                        }
//                        true
//                    }
//                    selectedCategory != null && !showGrid &&
//                            keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionLeft -> {
//                        val currentCatIndex = availableMainCategories.indexOfFirst { it.first == selectedCategory }
//                        if (currentCatIndex > 0) {
//                            val newIndex = currentCatIndex - 1
//                            selectedCategory = availableMainCategories[newIndex].first
//                            menuFocusedIndex = newIndex
//                            menuItemsFocusRequesters.getOrNull(newIndex)?.requestFocus()
//                        }
//                        true
//                    }
//
//                    // Channel navigation when no menu is shown
//                    selectedCategory == null && !showGrid &&
//                            keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionRight -> {
//                        if (currentIndex < allChannels.size - 1) {
//                            previewDirection = "next"
//                            showChannelPreview = true
//                            currentIndex++
//                            currentChannel = allChannels[currentIndex]
//                        }
//                        true
//                    }
//                    selectedCategory == null && !showGrid &&
//                            keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionLeft -> {
//                        if (currentIndex > 0) {
//                            previewDirection = "previous"
//                            showChannelPreview = true
//                            currentIndex--
//                            currentChannel = allChannels[currentIndex]
//                        }
//                        true
//                    }
//                    else -> false
//                }
//            }
//    ) {
//        // AndroidView for ExoPlayer
//        AndroidView(
//            factory = { context ->
//                StyledPlayerView(context).apply {
//                    player = exoPlayer
//                    useController = true
//                    resizeMode = com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
//                    setShowBuffering(StyledPlayerView.SHOW_BUFFERING_WHEN_PLAYING)
//                    keepScreenOn = true
//                    isFocusable = true
//                    isFocusableInTouchMode = true
//                }
//            },
//            update = { view ->
//                view.player = exoPlayer
//            },
//            modifier = Modifier.fillMaxSize()
//        )
//
//        // Top Action Bar with Categories (Always Visible)
//        TopActionBar(
//            categories = availableMainCategories,
//            selectedCategory = selectedCategory,
//            onCategorySelected = { category ->
//                selectedCategory = category
//                showGrid = false
//                val index = availableMainCategories.indexOfFirst { it.first == category }
//                if (index >= 0) {
//                    menuFocusedIndex = index
//                }
//            },
//            focusRequesters = menuItemsFocusRequesters,
//            focusedIndex = menuFocusedIndex,
//            onFocusChanged = { index ->
//                menuFocusedIndex = index
//                if (index >= 0 && index < availableMainCategories.size) {
//                    selectedCategory = availableMainCategories[index].first
//                }
//            },
//            modifier = Modifier
//                .align(Alignment.TopCenter)
//                .fillMaxWidth()
//                .padding(5.dp)
//        )
//
//        // Channel Grid (Shows when category is selected and down pressed)
//        if (selectedCategory != null && showGrid) {
//            val categoryChannels = allChannels.filter {
//                it.category.trim().equals(selectedCategory, ignoreCase = true)
//            }
//
//            ChannelGrid(
//                channels = categoryChannels,
//                onChannelSelected = { channel ->
//                    val newIndex = allChannels.indexOfFirst { it.id == channel.id }
//                    if (newIndex != -1) {
//                        currentIndex = newIndex
//                        currentChannel = channel
//                        showGrid = false
//                        selectedCategory = null
//                        focusRequester.requestFocus()
//                    }
//                },
//                onClose = {
//                    showGrid = false
//                    // Return focus to menu
//                    menuItemsFocusRequesters.getOrNull(menuFocusedIndex)?.requestFocus()
//                },
//                focusRequester = gridFocusRequester,
//                modifier = Modifier
//                    .align(Alignment.Center)
//                    .padding(top = 100.dp)
//                    .fillMaxWidth()
//                    .height(500.dp)
//            )
//        }
//
//        // Top overlay for channel info (temporary)
//        ChannelInfoOverlay(
//            channel = currentChannel,
//            currentIndex = currentIndex,
//            totalChannels = allChannels.size,
//            showMenu = selectedCategory != null || showGrid,
//            onNext = {
//                if (currentIndex < allChannels.size - 1) {
//                    previewDirection = "next"
//                    showChannelPreview = true
//                    currentIndex++
//                    currentChannel = allChannels[currentIndex]
//                }
//            },
//            onPrevious = {
//                if (currentIndex > 0) {
//                    previewDirection = "previous"
//                    showChannelPreview = true
//                    currentIndex--
//                    currentChannel = allChannels[currentIndex]
//                }
//            },
//            modifier = Modifier.align(Alignment.TopCenter)
//        )
//
//        // Channel Preview Overlay
//        if (showChannelPreview) {
//            ChannelPreviewOverlay(
//                currentChannel = currentChannel,
//                nextChannel = nextChannel,
//                previousChannel = previousChannel,
//                direction = previewDirection,
//                modifier = Modifier.align(Alignment.BottomCenter)
//            )
//        }
//
//        // Show swipe overlay only when menu/grid is not visible
//        if (selectedCategory == null && !showGrid && !showChannelPreview) {
//            SwipeChannelOverlay(
//                nextChannel = nextChannel,
//                previousChannel = previousChannel,
//                modifier = Modifier.align(Alignment.BottomCenter)
//            )
//        }
//        // Permanent bottom right corner logo
//        PermanentChannelLogo(
//            channel = currentChannel,
//            modifier = Modifier.align(Alignment.BottomEnd)
//        )
//    }
//}

@Composable
fun TopActionBar(
    categories: List<Pair<String, ImageVector>>,
    selectedCategory: String?,
    onCategorySelected: (String) -> Unit,
    focusRequesters: List<FocusRequester>,
    focusedIndex: Int,
    onFocusChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    // Auto-scroll to selected category
    LaunchedEffect(selectedCategory) {
        val index = categories.indexOfFirst { it.first == selectedCategory }
        if (index >= 0) {
            listState.animateScrollToItem(index)
        }
    }

    Surface(
        modifier = modifier
            .wrapContentWidth() // This will make the width fit the content
            .padding(horizontal = 4.dp) // 👈 add this
            .shadow(elevation = 0.dp, shape = RoundedCornerShape(20.dp)),
        color = Color.Black.copy(alpha = 0.8f),
        shape = RoundedCornerShape(20.dp) // 👈 MUST match
    ) {
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp), // 👈 important
            modifier = Modifier
                .wrapContentWidth() // LazyRow will also wrap to content
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            items(categories.size) { index ->
                val (category, icon) = categories[index]
                ActionBarItem(
                    title = category,
                    icon = icon,
                    isSelected = category == selectedCategory,
                    isFocused = index == focusedIndex,
                    onFocus = {
                        onFocusChanged(index)
                    },
                    onClick = {
                        onCategorySelected(category)
                        onFocusChanged(index)
                    },
                    focusRequester = focusRequesters.getOrNull(index) ?: FocusRequester()
                )
            }
        }
    }
}

@Composable
fun ActionBarItem(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    isFocused: Boolean,
    onFocus: () -> Unit,
    onClick: () -> Unit,
    focusRequester: FocusRequester
) {
    val scale by animateFloatAsState(if (isFocused) 1.1f else 1f)

    Surface(
        modifier = Modifier
            .scale(scale)
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.isFocused) {
                    onFocus()
                }
            }
            .focusable()
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown &&
                    (event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DPAD_CENTER ||
                            event.key == Key.Enter)
                ) {
                    onClick()
                    true
                } else {
                    false
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onClick()
                    }
                )
            }
            .then(
                if (isSelected || isFocused) {
                    Modifier.border(2.dp, Color.White, RoundedCornerShape(20.dp))
                } else {
                    Modifier
                }
            ),
        color = if (isSelected) Color.White.copy(alpha = 0.3f) else Color.Transparent,
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            Modifier
                .border(1.dp, Color.White, RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isSelected || isFocused) Color.White else Color.White,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = title,
                color = if (isSelected || isFocused) Color.White else Color.White,
                fontSize = 14.sp,
                fontWeight = if (isSelected || isFocused) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

@Composable
fun ChannelGrid(
    channels: List<TvChannel>,
    onChannelSelected: (TvChannel) -> Unit,
    onClose: () -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyGridState()
    var focusedIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Surface(
        modifier = modifier,
        color = Color.Black.copy(alpha = 0.9f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${channels.firstOrNull()?.category ?: "Channel"} Channels",
                    color = Color.Red,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                IconButton(
                    onClick = onClose,
                    modifier = Modifier.focusable()
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(6),
                state = gridState,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .weight(1f)
            ) {
                items(channels.size) { index ->
                    val channel = channels[index]
                    ChannelGridItem(
                        channel = channel,
                        isFocused = index == focusedIndex,
                        onFocus = { focusedIndex = index },
                        onClick = { onChannelSelected(channel) }
                    )
                }
            }
        }
    }
}

@Composable
fun ChannelGridItem(
    channel: TvChannel,
    isFocused: Boolean,
    onFocus: () -> Unit,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(if (isFocused) 1.15f else 1f)

    Surface(
        modifier = Modifier
            .width(140.dp)
            .scale(scale)
            .onFocusChanged {
                if (it.isFocused) onFocus()
            }
            .focusable()
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown &&
                    event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DPAD_CENTER
                ) {
                    onClick()
                    true
                } else {
                    false
                }
            }
            .clickable { onClick() }
            .then(
                if (isFocused) {
                    Modifier.border(3.dp, Color.Red, RoundedCornerShape(10.dp))
                } else {
                    Modifier
                }
            ),
        color = Color.DarkGray,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(channel.logoUrl)
                    .crossfade(true)
                    .error(R.drawable.logos)
                    .build(),
                contentDescription = channel.name,
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.DarkGray),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = channel.name,
                color = if (isFocused) Color.Red else Color.White,
                fontSize = 14.sp,
                fontWeight = if (isFocused) FontWeight.Bold else FontWeight.Normal,
                maxLines = 2,
                minLines = 2
            )

            Text(
                text = channel.category,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun ChannelPreviewOverlay(
    currentChannel: TvChannel,
    nextChannel: TvChannel?,
    previousChannel: TvChannel?,
    direction: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 20.dp),
        color = Color.Black.copy(alpha = 0.8f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,

            ) {
            // Previous Channel (if available)
            if (previousChannel != null) {
                ChannelPreviewItem(
                    channel = previousChannel,
                    label = "Previous",
                    isCurrent = false,
                    direction = "previous",
                    highlightDirection = direction
                )
            } else {
                Box(modifier = Modifier.width(120.dp))
            }

            // Current Channel
            ChannelPreviewItem(
                channel = currentChannel,
                label = "Now Playing",
                isCurrent = true,
                direction = "",
                highlightDirection = direction
            )

            // Next Channel (if available)
            if (nextChannel != null) {
                ChannelPreviewItem(
                    channel = nextChannel,
                    label = "Next",
                    isCurrent = false,
                    direction = "next",
                    highlightDirection = direction
                )
            } else {
                Box(modifier = Modifier.width(120.dp))
            }
        }
    }
}

@Composable
fun ChannelPreviewItem(
    channel: TvChannel,
    label: String,
    isCurrent: Boolean,
    direction: String,
    highlightDirection: String
) {
    val scale by animateFloatAsState(
        targetValue = if ((direction == "next" && highlightDirection == "next") ||
            (direction == "previous" && highlightDirection == "previous") ||
            isCurrent) 1.1f else 0.9f
    )

    val borderColor = when {
        isCurrent -> Color.Red
        direction == "next" && highlightDirection == "next" -> Color.Green
        direction == "previous" && highlightDirection == "previous" -> Color.Yellow
        else -> Color.Transparent
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .size(if (isCurrent) 90.dp else 60.dp)
                .scale(scale)
                .then(
                    if (borderColor != Color.Transparent) {
                        Modifier.border(3.dp, borderColor, RoundedCornerShape(12.dp))
                    } else {
                        Modifier
                    }
                ),
            color = if (isCurrent) Color.DarkGray else Color.Gray.copy(alpha = 0.5f),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(channel.logoUrl)
                        .crossfade(true)
                        .error(R.drawable.logos)
                        .build(),
                    contentDescription = channel.name,
                    modifier = Modifier
                        .size(if (isCurrent) 70.dp else 50.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.DarkGray),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = channel.name,
                    color = Color.White,
                    fontSize = if (isCurrent) 14.sp else 12.sp,
                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = if (isCurrent) Color.Red else Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun PermanentChannelLogo(
    channel: TvChannel,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .padding(24.dp)
            .width(150.dp)
            .height(80.dp)
            .shadow(elevation = 0.dp, shape = RoundedCornerShape(8.dp)),
        color = Color.Black.copy(alpha = 0.0f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logos),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun ChannelInfoOverlay(
    channel: TvChannel,
    currentIndex: Int,
    totalChannels: Int,
    showMenu: Boolean,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showOverlay by remember { mutableStateOf(true) }

    // Show overlay when channel changes and auto-hide after 3 seconds
    LaunchedEffect(channel) {
        showOverlay = true
        delay(3000)
        showOverlay = false
    }

    if (showOverlay && !showMenu) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = Color.Black.copy(alpha = 0.0f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Channel logo with fallback to local image
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(channel.logoUrl)
                            .crossfade(true)
                            .error(R.drawable.logos)
                            .build(),
                        contentDescription = channel.name,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.DarkGray),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = channel.name,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = channel.category,
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ArrowLeft,
                        contentDescription = "Previous",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = "${currentIndex + 1}/$totalChannels",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Icon(
                        imageVector = Icons.Default.ArrowRight,
                        contentDescription = "Next",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
@Composable
    fun SwipeChannelOverlay(
    nextChannel: TvChannel?,
    previousChannel: TvChannel?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(32.dp)),
            color = Color.Black.copy(alpha = 0.85f),
            shape = RoundedCornerShape(32.dp)
        ) {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Previous Channel Section
                if (previousChannel != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.wrapContentWidth()
                    ) {
//                        Icon(
//                            imageVector = Icons.Default.KeyboardArrowLeft,
//                            contentDescription = "Previous",
//                            tint = Color.White,
//                            modifier = Modifier.size(24.dp)
//                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.DarkGray)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(previousChannel.logoUrl)
                                    .crossfade(true)
                                    .error(R.drawable.logos)
                                    .build(),
                                contentDescription = previousChannel.name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = previousChannel.name.take(12) + if (previousChannel.name.length > 12) "..." else "",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp)) // Add spacing between sections
                }

                // Center Text
                // Center Section with Icons
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowCircleLeft,
                        contentDescription = "Swipe Left",
                        tint = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "SWIPE TO CHANGE",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector = Icons.Default.ArrowCircleRight,
                        contentDescription = "Swipe Right",
                        tint = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.size(20.dp)
                    )
                }


                // Next Channel Section
                if (nextChannel != null) {
                    Spacer(modifier = Modifier.width(16.dp)) // Add spacing between sections

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        Text(
                            text = nextChannel.name.take(12) + if (nextChannel.name.length > 12) "..." else "",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.DarkGray)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(nextChannel.logoUrl)
                                    .crossfade(true)
                                    .error(R.drawable.logos)
                                    .build(),
                                contentDescription = nextChannel.name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

//                        Icon(
//                            imageVector = Icons.Default.KeyboardArrowRight,
//                            contentDescription = "Next",
//                            tint = Color.White,
//                            modifier = Modifier.size(24.dp)
//                        )
                    }
                }
            }
        }
    }
}