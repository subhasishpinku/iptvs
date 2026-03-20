package com.bacbpl.iptv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.tv.material3.LocalContentColor
import androidx.tv.material3.MaterialTheme
import com.bacbpl.iptv.jetStram.presentation.App
import com.bacbpl.iptv.jetStram.presentation.theme.JetStreamTheme
import dagger.hilt.android.AndroidEntryPoint

    @AndroidEntryPoint
// In JetStreamActivity.kt
    class JetStreamActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            installSplashScreen()
            super.onCreate(savedInstanceState)

            val navigateToProfile = intent.getBooleanExtra("navigate_to_profile", false)

            setContent {
                JetStreamTheme {
                    Box(
                        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)
                    ) {
                        CompositionLocalProvider(
                            LocalContentColor provides MaterialTheme.colorScheme.onSurface
                        ) {
                            App(
                                onBackPressed = onBackPressedDispatcher::onBackPressed,
                                navigateToProfile = navigateToProfile
                            )
                        }
                    }
                }
            }
        }
    }
