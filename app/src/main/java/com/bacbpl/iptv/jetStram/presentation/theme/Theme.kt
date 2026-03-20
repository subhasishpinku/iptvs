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

package com.bacbpl.iptv.jetStram.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.darkColorScheme
import com.bacbpl.iptv.R

// Define custom colors
val Black = Color(0xFF000000)
val DarkGray = Color(0xFF121212)
val MediumGray = Color(0xFF1E1E1E)
val LightGray = Color(0xFF2A2A2A)
val White = Color(0xFFFFFFFF)

private val darkColorScheme @Composable get() = darkColorScheme(
    primary = colorResource(R.color.primary),
    onPrimary = colorResource(R.color.onPrimary),
    primaryContainer = colorResource(R.color.primaryContainer),
    onPrimaryContainer = colorResource(R.color.onPrimaryContainer),
    secondary = colorResource(R.color.secondary),
    onSecondary = colorResource(R.color.onSecondary),
    secondaryContainer = colorResource(R.color.secondaryContainer),
    onSecondaryContainer = colorResource(R.color.onSecondaryContainer),
    tertiary = colorResource(R.color.tertiary),
    onTertiary = colorResource(R.color.onTertiary),
    tertiaryContainer = colorResource(R.color.tertiaryContainer),
    onTertiaryContainer = colorResource(R.color.onTertiaryContainer),

    // Background colors
    background = Black,
    onBackground = White,

    // Surface colors
    surface = DarkGray,
    onSurface = White,
    surfaceVariant = LightGray,
    onSurfaceVariant = White.copy(alpha = 0.7f),

    // Error colors
    error = Color(0xFFCF6679),
    onError = Black,
    errorContainer = Color(0xFFB00020),
    onErrorContainer = White,

    // Border
    border = White.copy(alpha = 0.12f),
)

@Composable
fun JetStreamTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = darkColorScheme,
        shapes = MaterialTheme.shapes,
        typography = Typography,
        content = content
    )
}