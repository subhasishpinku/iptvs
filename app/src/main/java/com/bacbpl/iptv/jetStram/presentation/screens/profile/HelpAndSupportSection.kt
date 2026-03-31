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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.bacbpl.iptv.data.util.StringConstants
import com.bacbpl.iptv.jetStram.presentation.theme.JetStreamCardShape
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material3.Card
import androidx.compose.ui.viewinterop.AndroidView

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun HelpAndSupportSection(
    onNavigateToPrivacyPolicy: () -> Unit = {},
    onNavigateToFAQ: () -> Unit = {},
    onNavigateToContact: () -> Unit = {}
) {
    with(StringConstants.Composable.Placeholders) {
        Column(modifier = Modifier.padding(horizontal = 72.dp)) {
            Text(
                text = HelpAndSupportSectionTitle,
                style = MaterialTheme.typography.headlineSmall
            )
            HelpAndSupportSectionItem(
                title = HelpAndSupportSectionFAQItem,
                onClick = onNavigateToFAQ
            )
            HelpAndSupportSectionItem(
                title = HelpAndSupportSectionPrivacyItem,
                onClick = onNavigateToPrivacyPolicy
            )
            HelpAndSupportSectionItem(
                title = HelpAndSupportSectionContactItem,
                value = HelpAndSupportSectionContactValue,
                onClick = onNavigateToContact
            )
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun HelpAndSupportSectionItem(
    title: String,
    value: String? = null,
    onClick: () -> Unit = {}
) {
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
                    contentDescription = StringConstants
                        .Composable
                        .Placeholders
                        .HelpAndSupportSectionListItemIconDescription
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
                    text = "Privacy Policy",
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
                            text = """
                                BACBPL is committed to protecting any personal information that You may provide to Us. 
                                
                                In particular, We believe it is important for You to know how We treat information about You that We may receive from this Website "www.bacbpl.in" ("Site").
                                
                                In general, You can visit this site without telling Us who You are or revealing any information about Yourself. Our web servers collect the domain names, not the e-mail addresses of visitors.
                                
                                There are portions of this Site where We may need to collect personal information from You for a specific purpose. The information collected from You may include Your name, address, telephone, fax number, or e-mail address, etc. 
                                
                                This Privacy Policy is applicable to any personal information, which is given by You to Us ("User Information") via this Site and is devised to help You feel more confident about the privacy and security of Your personal details. When You leave Your contact details please note that We are not bound to reply.
                                
                                This Site is not intended for persons under 13 years of age. We do not knowingly solicit or collect personal information from or about children, and We do not knowingly market Our products or services to children.
                                
                                "You" shall mean You, the User of the Site and "Yourself" interpreted accordingly. "We" / "Us" means BACBPL and "Our" interpreted accordingly. "Users" means the Users of the Site collectively and/or individually as the context allows.
                                
                                For any questions regarding this privacy policy, please contact us at:
                                support@bacbpl.in
                            """.trimIndent(),
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
                        text = "Close",
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
    val context = androidx.compose.ui.platform.LocalContext.current

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
                        text = "Contact Info",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Call: 03324548681 / 6289364699")
                    Text("Email: sales@bacbpl.com")
                    Text("Web: www.bacbpl.in")

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "BHORER ALO CABLE AND BROADBAND PRIVATE",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "29/2C, Chandra Nath Chatterjee Street\nBhowanipore, Kolkata 700025"
                    )

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
                        Text("Open in Google Maps")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Map Preview using WebView with Google Maps Iframe and API Key
                    androidx.tv.material3.Card(
                        onClick = {
                            // Open Google Maps when card is clicked
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
                                    webViewClient = WebViewClient()

                                    // Google Maps Iframe with API key from your Firebase config
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

                // ================= RIGHT SIDE (FORM) =================
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Text(
                        text = "Send us a message",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Your Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Your Email") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = subject,
                        onValueChange = { subject = it },
                        label = { Text("Subject") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        label = { Text("Message") },
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
                        Text("SEND MESSAGE")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}