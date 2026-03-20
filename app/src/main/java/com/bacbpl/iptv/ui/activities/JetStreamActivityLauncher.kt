package com.bacbpl.iptv.ui.activities

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.bacbpl.iptv.ui.activities.subscribescreen.SubscribeScreen

@Composable
fun JetStreamActivityLauncher() {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val intent = Intent(context, SubscribeScreen()::class.java)
        context.startActivity(intent)
    }

    // Optional: Show a loading indicator while launching
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}