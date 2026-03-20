package com.bacbpl.iptv.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Color definitions
val PrimaryColor = Color(0xFF6200EE)      // Your primary purple color
val PrimaryDarkColor = Color(0xFF3700B3)  // Darker purple
val PrimaryLightColor = Color(0xFFBB86FC) // Lighter purple
val SecondaryColor = Color(0xFF03DAC6)    // Teal color
val RedColor = Color(0xFFFF0000)          // Red for secondary

// Background colors
val BackgroundBlack = Color(0xFF000000)
val SurfaceDark = Color(0xFF121212)
val OffWhite = Color(0xFFB3B3B3)          // For secondary text
val White = Color(0xFFFFFFFF)

@Composable
fun IPTVTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Auto-detect system theme
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = PrimaryColor,
            onPrimary = White,
            primaryContainer = PrimaryDarkColor,
            onPrimaryContainer = White,

            secondary = RedColor,
            onSecondary = White,
            secondaryContainer = PrimaryDarkColor,
            onSecondaryContainer = White,

            background = BackgroundBlack,
            onBackground = White,

            surface = SurfaceDark,
            onSurface = White,

            surfaceVariant = PrimaryDarkColor,
            onSurfaceVariant = OffWhite,

            error = RedColor,
            onError = White
        )
    } else {
        // Light theme variant (if you need it)
        lightColorScheme(
            primary = PrimaryLightColor,
            onPrimary = BackgroundBlack,
            primaryContainer = PrimaryLightColor,
            onPrimaryContainer = BackgroundBlack,

            secondary = SecondaryColor,
            onSecondary = BackgroundBlack,

            background = White,
            onBackground = BackgroundBlack,

            surface = OffWhite,
            onSurface = BackgroundBlack,

            surfaceVariant = PrimaryLightColor,
            onSurfaceVariant = BackgroundBlack
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(), // You can customize typography here
        content = content
    )
}

// Optional: Custom typography
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

// Optional: Theme extension functions for easy access
object IPTVThemeColors {
    val primary: Color
        @Composable
        get() = MaterialTheme.colorScheme.primary

    val background: Color
        @Composable
        get() = MaterialTheme.colorScheme.background

    val surface: Color
        @Composable
        get() = MaterialTheme.colorScheme.surface

    val textPrimary: Color
        @Composable
        get() = MaterialTheme.colorScheme.onBackground

    val textSecondary: Color
        @Composable
        get() = MaterialTheme.colorScheme.onSurfaceVariant
}