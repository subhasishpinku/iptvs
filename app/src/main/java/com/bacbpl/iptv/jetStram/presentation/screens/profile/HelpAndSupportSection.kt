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

package com.bacbpl.iptv.jetStram.presentation.screens.profile

import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.ListItem
import androidx.tv.material3.ListItemDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import androidx.tv.material3.surfaceColorAtElevation
import com.bacbpl.iptv.R
import com.bacbpl.iptv.jetStram.data.util.StringConstants
import com.bacbpl.iptv.jetStram.presentation.theme.JetStreamCardShape

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun HelpAndSupportSection(
    onNavigateToPrivacyPolicy: () -> Unit = {},
    onNavigateToFAQ: () -> Unit = {},
    onNavigateToContact: () -> Unit = {}
) {
    // Get string resources
    val helpAndSupportTitle = stringResource(id = StringConstants.Composable.Placeholders.HelpAndSupportSectionTitle)
    val privacyPolicyItem = stringResource(id = StringConstants.Composable.Placeholders.HelpAndSupportSectionPrivacyItem)
    val contactItem = stringResource(id = StringConstants.Composable.Placeholders.HelpAndSupportSectionContactItem)
    val contactValue = stringResource(id = StringConstants.Composable.Placeholders.HelpAndSupportSectionContactValue)

    Column(modifier = Modifier.padding(horizontal = 72.dp)) {
        Text(
            text = helpAndSupportTitle,
            style = MaterialTheme.typography.headlineSmall
        )

        HelpAndSupportSectionItem(
            title = privacyPolicyItem,
            onClick = onNavigateToPrivacyPolicy
        )

        HelpAndSupportSectionItem(
            title = contactItem,
            value = contactValue,
            onClick = onNavigateToContact
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun HelpAndSupportSectionItem(
    title: String,
    value: String? = null,
    onClick: () -> Unit = {}
) {
    val iconDescription = stringResource(id = StringConstants.Composable.Placeholders.HelpAndSupportSectionListItemIconDescription)

    ListItem(
        modifier = Modifier.padding(top = 16.dp),
        selected = false,
        onClick = onClick,
        trailingContent = {
            value?.let { nnValue ->
                Text(
                    text = nnValue,
                    style = MaterialTheme.typography.titleMedium
                )
            } ?: run {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForwardIos,
                    modifier = Modifier.size(ListItemDefaults.IconSizeDense),
                    contentDescription = iconDescription
                )
            }
        },
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedContentColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = ListItemDefaults.shape(shape = JetStreamCardShape)
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun PrivacyPolicyDialog(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.99f),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Text(
                    text = stringResource(R.string.privacy_policy),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = stringResource(R.string.privacy_policy_content),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 20.sp
                        )
                    }
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 16.dp),
                    colors = ButtonDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = stringResource(R.string.back),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ContactUsDialog(
    onDismiss: () -> Unit,
    onSubmit: (name: String, email: String, subject: String, message: String) -> Unit = { _, _, _, _ -> }
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val context = LocalContext.current
    var mapLoadingError by remember { mutableStateOf(false) }
    var webViewReady by remember { mutableStateOf(false) }

    // Get localized strings
    val contactInfo = stringResource(R.string.contact_info)
    val phone = stringResource(R.string.contact_phone)
    val emailText = stringResource(R.string.contact_email_address)
    val website = stringResource(R.string.contact_website)
    val companyName = stringResource(R.string.company_name)
    val companyAddress = stringResource(R.string.company_address)
    val openInGoogleMaps = stringResource(R.string.open_in_google_maps)
    val sendUsMessage = stringResource(R.string.send_us_message)
    val yourName = stringResource(R.string.your_name)
    val yourEmail = stringResource(R.string.your_email)
    val subjectText = stringResource(R.string.subject)
    val messageText = stringResource(R.string.message)
    val sendMessage = stringResource(R.string.send_message)
    val back = stringResource(R.string.back)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.95f),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {

                // ================= LEFT SIDE (MAP + CONTACT INFO) =================
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp)
                ) {

                    Text(
                        text = contactInfo,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(phone)
                    Text(emailText)
                    Text(website)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = companyName,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(companyAddress)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Map Button (TV friendly)
                    Button(
                        onClick = {
                            val url = "https://www.google.com/maps?q=22.5350378,88.3434308"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(openInGoogleMaps)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Map Preview - with fallback to static image
                    androidx.tv.material3.Card(
                        onClick = {
                            val url = "https://www.google.com/maps?q=22.5350378,88.3434308"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        shape = androidx.tv.material3.CardDefaults.shape(
                            shape = MaterialTheme.shapes.medium
                        ),
                        colors = androidx.tv.material3.CardDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        if (mapLoadingError || !webViewReady) {
                            // Show static map image from drawable
                            Image(
                                painter = painterResource(id = R.drawable.map),
                                contentDescription = "Location Map",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            AndroidView(
                                factory = { ctx ->
                                    WebView(ctx).apply {
                                        settings.apply {
                                            javaScriptEnabled = true
                                            loadWithOverviewMode = true
                                            useWideViewPort = true
                                            setSupportZoom(true)
                                            builtInZoomControls = true
                                            displayZoomControls = false
                                            domStorageEnabled = true
                                        }
                                        webViewClient = object : WebViewClient() {
                                            override fun onPageFinished(view: WebView?, url: String?) {
                                                super.onPageFinished(view, url)
                                                webViewReady = true
                                            }

                                            override fun onReceivedError(
                                                view: WebView?,
                                                errorCode: Int,
                                                description: String?,
                                                failingUrl: String?
                                            ) {
                                                super.onReceivedError(view, errorCode, description, failingUrl)
                                                mapLoadingError = true
                                                webViewReady = false
                                            }
                                        }

                                        // Google Maps Iframe with API key
                                        val apiKey = "AIzaSyC-ppC-01qqiKhVO66MT6UM_b74a83zMe4"
                                        val latitude = 22.5350378
                                        val longitude = 88.3434308

                                        val htmlContent = """
                                            <!DOCTYPE html>
                                            <html>
                                            <head>
                                                <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=yes">
                                                <style>
                                                    * {
                                                        margin: 0;
                                                        padding: 0;
                                                        box-sizing: border-box;
                                                    }
                                                    body {
                                                        margin: 0;
                                                        padding: 0;
                                                        height: 100%;
                                                        width: 100%;
                                                        overflow: hidden;
                                                        background-color: #f0f0f0;
                                                    }
                                                    .map-container {
                                                        height: 100%;
                                                        width: 100%;
                                                        position: relative;
                                                    }
                                                    iframe {
                                                        width: 100%;
                                                        height: 100%;
                                                        border: 0;
                                                    }
                                                </style>
                                            </head>
                                            <body>
                                                <div class="map-container">
                                                    <iframe
                                                        src="https://www.google.com/maps/embed/v1/place?key=$apiKey&q=$latitude,$longitude&zoom=16&maptype=roadmap"
                                                        allowfullscreen>
                                                    </iframe>
                                                </div>
                                            </body>
                                            </html>
                                        """.trimIndent()

                                        loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)
                                    }
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }

                // ================= RIGHT SIDE (FORM) =================
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Text(
                        text = sendUsMessage,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(yourName) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(yourEmail) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = subject,
                        onValueChange = { subject = it },
                        label = { Text(subjectText) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        label = { Text(messageText) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        maxLines = 4
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            onSubmit(name, email, subject, message)
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(sendMessage)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(back)
                    }
                }
            }
        }
    }
}