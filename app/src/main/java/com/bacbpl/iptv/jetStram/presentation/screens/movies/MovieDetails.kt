package com.bacbpl.iptv.jetStram.presentation.screens.movies

import android.content.Context
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
import com.bacbpl.iptv.data.SharedPrefManager
import com.bacbpl.iptv.data.util.StringConstants
import com.bacbpl.iptv.jetStram.data.entities.MovieDetails
import com.bacbpl.iptv.jetStram.presentation.screens.dashboard.rememberChildPadding
import com.bacbpl.iptv.jetStram.presentation.theme.JetStreamButtonShape
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONObject
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

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
    var isLoadingAuthUrl by remember { mutableStateOf(false) }

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
                                coroutineScope.launch {
                                    isLoadingAuthUrl = true
                                    try {
                                        val authorizedUrl = withContext(Dispatchers.IO) {
                                            getAuthorizedOttplayUrl(ottplayUrl, context)
                                        }
                                        if (authorizedUrl != null) {
                                            println("Opening authorized URL: $authorizedUrl")
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(authorizedUrl))
                                            context.startActivity(intent)
                                        } else {
                                            // Fallback to original URL if authorization fails
                                            println("Failed to get authorized URL, using original: $ottplayUrl")
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ottplayUrl))
                                            context.startActivity(intent)
                                        }
                                    } catch (e: Exception) {
                                        println("Error getting authorized URL: ${e.message}")
                                        e.printStackTrace()
                                        // Fallback to original URL
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ottplayUrl))
                                        context.startActivity(intent)
                                    } finally {
                                        isLoadingAuthUrl = false
                                    }
                                }
                            },
                            modifier = Modifier.weight(1f),
                            isLoading = isLoadingAuthUrl
                        )
                    }
                }
            }
        }
    }
}

// Function to get authorized OTTplay URL with Bearer Token using POST
//private suspend fun getAuthorizedOttplayUrl(ottplayUrl: String, context: Context): String? {
//    return try {
//        val url = "https://iptv.yogayog.net/api/get-ottplay-url-authorized/"
//        val sharedPrefManager = SharedPrefManager(context)
//        val token = sharedPrefManager.getToken()
//        println("Original ottplayUrl: $ottplayUrl")
//
//        // Create OkHttpClient with timeout configurations
//        val client = OkHttpClient.Builder()
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(30, TimeUnit.SECONDS)
//            .build()
//
//        // Create form body with ottplay_url parameter
//        val formBody = FormBody.Builder()
//            .add("ottplay_url", ottplayUrl)
//            .build()
//
//        // Build POST request with Bearer token
//        val request = Request.Builder()
//            .url(url)
//            .addHeader("Authorization", "Bearer 132|ClUBpdoHQ4VpWhwhOdcz1Jc2rbbIb2bcG1YMzFGs9c6c4fa5")
//            .addHeader("Content-Type", "application/x-www-form-urlencoded")
//            .addHeader("Accept", "application/json")
//            .post(formBody)
//            .build()
//
//        println("Making POST request to: $url")
//        println("Request body: ottplay_url=$ottplayUrl")
//
//        val response = client.newCall(request).execute()
//        val responseBody = response.body?.string()
//
//        println("Response code: ${response.code}")
//        println("Response body: $responseBody")
//
//        if (response.isSuccessful && responseBody != null) {
//            // Parse the JSON response
//            val jsonObject = JSONObject(responseBody)
//            val success = jsonObject.optBoolean("success", false)
//            val authorizedUrl = jsonObject.optString("url", null)
//            val message = jsonObject.optString("message", "")
//
//            if (success && !authorizedUrl.isNullOrEmpty() && authorizedUrl != "null") {
//                println("Successfully got authorized URL: $authorizedUrl")
//                authorizedUrl
//            } else {
//                println("Authorization failed - Success: $success, Message: $message")
//                // Try to see if there's any URL in the response anyway
//                if (!authorizedUrl.isNullOrEmpty() && authorizedUrl != "null") {
//                    println("Found URL even though success was false: $authorizedUrl")
//                    authorizedUrl
//                } else {
//                    null
//                }
//            }
//        } else {
//            println("HTTP request failed with code: ${response.code}")
//            println("Error response: $responseBody")
//            null
//        }
//    } catch (e: Exception) {
//        println("Exception in getAuthorizedOttplayUrl: ${e.message}")
//        e.printStackTrace()
//        null
//    }
//}

private suspend fun getAuthorizedOttplayUrl(ottplayUrl: String, context: Context): String? {
    return try {
        val url = "https://iptv.yogayog.net/api/get-ottplay-url-authorized/"

        // Get token from SharedPrefManager - NOW context is available
        val sharedPrefManager = SharedPrefManager(context)
        val token = sharedPrefManager.getToken()

        println("=== Getting Authorized URL ===")
        println("Original ottplayUrl: $ottplayUrl")
        println("Token from SharedPref: ${if (token != null) "Found (${token.take(20)}...)" else "NOT FOUND"}")

        // Check if token exists
        if (token.isNullOrEmpty()) {
            println("ERROR: No authentication token found. User may not be logged in.")
            return null
        }

        // Create OkHttpClient with timeout configurations
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        // Create form body with ottplay_url parameter
        val formBody = FormBody.Builder()
            .add("ottplay_url", ottplayUrl)
            .build()

        // Build POST request with Bearer token from SharedPreferences
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $token")  // USING DYNAMIC TOKEN
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("Accept", "application/json")
            .post(formBody)
            .build()

        println("Making POST request to: $url")
        println("Request body: ottplay_url=$ottplayUrl")

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()

        println("Response code: ${response.code}")
        println("Response body: $responseBody")

        if (response.isSuccessful && responseBody != null) {
            // Parse the JSON response
            val jsonObject = JSONObject(responseBody)
            val success = jsonObject.optBoolean("success", false)
            val authorizedUrl = jsonObject.optString("url", null)
            val message = jsonObject.optString("message", "")

            if (success && !authorizedUrl.isNullOrEmpty() && authorizedUrl != "null") {
                println("Successfully got authorized URL: $authorizedUrl")
                authorizedUrl
            } else {
                println("Authorization failed - Success: $success, Message: $message")
                // Try to see if there's any URL in the response anyway
                if (!authorizedUrl.isNullOrEmpty() && authorizedUrl != "null") {
                    println("Found URL even though success was false: $authorizedUrl")
                    authorizedUrl
                } else {
                    null
                }
            }
        } else {
            println("HTTP request failed with code: ${response.code}")
            println("Error response: $responseBody")

            // Handle specific error codes
            when (response.code) {
                401 -> {
                    println("UNAUTHORIZED - Token may be expired. Clearing session.")
                    // Clear the session on unauthorized
                    sharedPrefManager.clearUserSession()
                }
                403 -> println("FORBIDDEN - Token doesn't have permission")
                404 -> println("API endpoint not found")
                else -> println("Unknown error: ${response.code}")
            }
            null
        }
    } catch (e: Exception) {
        println("Exception in getAuthorizedOttplayUrl: ${e.message}")
        e.printStackTrace()
        null
    }
}

@Composable
private fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
        shape = ButtonDefaults.shape(shape = JetStreamButtonShape),
        enabled = !isLoading
    ) {
        if (isLoading) {
            // Loading indicator
            androidx.compose.material3.CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(Modifier.size(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall
            )
        } else {
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