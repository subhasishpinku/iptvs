package com.bacbpl.iptv.jetStram.presentation.common

// file: com/bacbpl/iptv/jetStram/presentation/common/TvChannelsRow.kt

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bacbpl.iptv.jetStram.data.entities.TvChannel
import com.bacbpl.iptv.jetStram.presentation.screens.dashboard.rememberChildPadding

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
            items(channels) { channel ->
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
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    var isItemFocused by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(180.dp)
            .height(120.dp)
            .onFocusChanged { isItemFocused = it.isFocused }
            .focusable(interactionSource = interactionSource)
            .clickable { onChannelSelected(channel) }
            .then(
                if (isFocused || isItemFocused) {
                    Modifier.border(3.dp, Color.Red, RoundedCornerShape(8.dp))
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isFocused || isItemFocused) 8.dp else 4.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Channel Logo
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(channel.logoUrl)
                        .crossfade(true)
                        .build()
                ),
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