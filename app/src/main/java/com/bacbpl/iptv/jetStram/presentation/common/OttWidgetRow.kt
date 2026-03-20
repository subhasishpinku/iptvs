package com.bacbpl.iptv.jetStram.presentation.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bacbpl.iptv.R
import com.bacbpl.iptv.jetStram.data.entities.OttWidget
import com.bacbpl.iptv.jetStram.data.entities.OttWidgetItem
import com.bacbpl.iptv.jetStram.presentation.screens.dashboard.rememberChildPadding

@Composable
fun OttWidgetRow(
    widget: OttWidget,
    onItemClick: (OttWidgetItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val childPadding = rememberChildPadding()

    Column(modifier = modifier) {
        Text(
            text = widget.name,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            color = Color.White,
            modifier = Modifier.padding(
                start = childPadding.start,
                bottom = 12.dp,
                top = 16.dp
            )
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(
                start = childPadding.start,
                end = childPadding.end
            )
        ) {
            items(
                items = widget.items,
                key = { it.id }
            ) { item ->
                OttWidgetItemCard(
                    item = item,
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}

@Composable
fun OttWidgetItemCard(
    item: OttWidgetItem,
    onClick: (OttWidgetItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isFocused) 1.05f else 1f)

    Card(
        modifier = modifier
            .width(220.dp)
            .aspectRatio(0.7f)
            .scale(scale)
            .onFocusChanged { isFocused = it.isFocused }
            .border(
                width = if (isFocused) 2.dp else 0.dp,
                color = Color.Red,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = CardDefaults.shape(shape = RoundedCornerShape(12.dp)),
        colors = CardDefaults.colors(
            containerColor = Color(0xFF1A1A1A),
            focusedContainerColor = Color(0xFF252525)
        ),
        scale = CardDefaults.scale(focusedScale = 1f),
        onClick = { onClick(item) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            // Poster Image with top branding overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.posterUrl)
                        .crossfade(true)
                        .error(R.drawable.logos)
                        .build(),
                    contentDescription = item.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Top branding overlay (like "DC" and "WONDER" in the image)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.7f),
                                    Color.Transparent
                                )
                            )
                        )
                        .padding(8.dp)
                ) {
                    // First line of branding (e.g., "DC")
                    Text(
                        text = item.format.uppercase().take(3),
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )

                    // Second line of branding (e.g., "WONDER")
                    Text(
                        text = item.language.uppercase(),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Movie Title (like "Wonder Woman")
            Text(
                text = item.title,
                color = if (isFocused) Color.Red else Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Info Row with Length, Language, Rating, Reviews
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Length
                InfoBadge(
                    label = "Length",
                    value = "${item.releaseYear}".take(4)
                )

                // Language
                InfoBadge(
                    label = "Lang",
                    value = item.language.take(3)
                )

                // Rating
                InfoBadge(
                    label = "Rating",
                    value = if (item.rating > 0) String.format("%.1f", item.rating) else "NR"
                )

                // Reviews
                InfoBadge(
                    label = "Review",
                    value = "${(item.rating * 7).toInt()}+" // Simulated review count
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Provider/Genre info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.ottProvider.ifEmpty { "Unknown" },
                    color = Color(0xFFFFA500),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = item.genre.split(",").firstOrNull()?.trim() ?: "",
                    color = Color.Gray,
                    fontSize = 11.sp,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun InfoBadge(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 9.sp,
            maxLines = 1
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
    }
}