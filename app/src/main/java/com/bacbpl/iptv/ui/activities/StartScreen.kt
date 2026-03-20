package com.bacbpl.iptv.ui.activities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bacbpl.iptv.ui.activities.navigation.AppNavigation
import com.bacbpl.iptv.ui.theme.IPTVTheme
class StartScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IPTVTheme {
                AppNavigation()
            }
        }
    }
}