package com.bacbpl.iptv.jetStram.presentation.screens.movies

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.OpenInBrowser
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bacbpl.iptv.R
import com.bacbpl.iptv.data.util.StringConstants
import com.bacbpl.iptv.jetStram.data.entities.MovieDetails
import com.bacbpl.iptv.jetStram.presentation.screens.dashboard.rememberChildPadding
import com.bacbpl.iptv.jetStram.presentation.theme.JetStreamButtonShape
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetails(
    movieDetails: MovieDetails,
    goToMoviePlayer: () -> Unit,
    ottplayUrl: String? = null
) {
    val childPadding = rememberChildPadding()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var imageLoadingError by remember { mutableStateOf(false) }

    // Move gradientColor inside @Composable function
    val gradientColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(432.dp)
            .bringIntoViewRequester(bringIntoViewRequester)
    ) {
        // Background Image with Gradient Overlay
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Check if posterUri is not empty and no loading error
            if (movieDetails.posterUri.isNotEmpty() && !imageLoadingError) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movieDetails.posterUri)
                        .crossfade(true)
                        .error(R.drawable.logos)
                        .build(),
                    contentDescription = StringConstants
                        .Composable
                        .ContentDescription
                        .moviePoster(movieDetails.name),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    onError = {
                        imageLoadingError = true
                    }
                )
            } else {
                // Fallback background with gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF1a1a2e),
                                    Color(0xFF16213e),
                                    Color(0xFF0f0f1f)
                                )
                            )
                        )
                ) {
                    // Show title as text on fallback
                    Text(
                        text = movieDetails.name,
                        color = Color.White.copy(alpha = 0.3f),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
            }

            // Gradient Overlay (always visible)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, gradientColor),
                                startY = 600f
                            )
                        )
                        drawRect(
                            Brush.horizontalGradient(
                                colors = listOf(gradientColor, Color.Transparent),
                                endX = 1000f,
                                startX = 300f
                            )
                        )
                        drawRect(
                            Brush.linearGradient(
                                colors = listOf(gradientColor, Color.Transparent),
                                start = Offset(x = 500f, y = 500f),
                                end = Offset(x = 1000f, y = 0f)
                            )
                        )
                    }
            )
        }

        Column(modifier = Modifier.fillMaxWidth(0.55f)) {
            Spacer(modifier = Modifier.height(108.dp))
            Column(
                modifier = Modifier.padding(start = childPadding.start)
            ) {
                MovieLargeTitle(movieTitle = movieDetails.name)

                Column(
                    modifier = Modifier.alpha(0.75f)
                ) {
                    MovieDescription(description = movieDetails.description)
                    DotSeparatedRow(
                        modifier = Modifier.padding(top = 20.dp),
                        texts = listOf(
                            movieDetails.pgRating,
                            movieDetails.releaseDate,
                            movieDetails.categories.joinToString(", "),
                            movieDetails.duration
                        )
                    )
                    DirectorScreenplayMusicRow(
                        director = movieDetails.director,
                        screenplay = movieDetails.screenplay,
                        music = movieDetails.music
                    )
                }

                // Row with two buttons
                Row(
                    modifier = Modifier.padding(top = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Watch Trailer / Play Button
                    ActionButton(
                        icon = Icons.Outlined.PlayArrow,
                        text = stringResource(R.string.watch_trailer),
                        onClick = goToMoviePlayer,
                        modifier = Modifier
                            .weight(1f)
                            .onFocusChanged {
                                if (it.isFocused) {
                                    coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
                                }
                            }
                    )

                    // Now Playing / OTTplay Button
                    if (!ottplayUrl.isNullOrEmpty()) {
                        ActionButton(
                            icon = Icons.Outlined.OpenInBrowser,
                            text = stringResource(R.string.now_playing),
                            onClick = {
                                println("ottplayUrl+$ottplayUrl");
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ottplayUrl))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
        shape = ButtonDefaults.shape(shape = JetStreamButtonShape)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Spacer(Modifier.size(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
private fun DirectorScreenplayMusicRow(
    director: String,
    screenplay: String,
    music: String
) {
    Row(modifier = Modifier.padding(top = 32.dp)) {
        TitleValueText(
            modifier = Modifier
                .padding(end = 32.dp)
                .weight(1f),
            title = stringResource(R.string.director),
            value = director
        )

        TitleValueText(
            modifier = Modifier
                .padding(end = 32.dp)
                .weight(1f),
            title = stringResource(R.string.screenplay),
            value = screenplay
        )

        TitleValueText(
            modifier = Modifier.weight(1f),
            title = stringResource(R.string.music),
            value = music
        )
    }
}

@Composable
private fun MovieDescription(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.titleSmall.copy(
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal
        ),
        modifier = Modifier.padding(top = 8.dp),
        maxLines = 2
    )
}

@Composable
private fun MovieLargeTitle(movieTitle: String) {
    Text(
        text = movieTitle,
        style = MaterialTheme.typography.displayMedium.copy(
            fontWeight = FontWeight.Bold
        ),
        maxLines = 1
    )
}