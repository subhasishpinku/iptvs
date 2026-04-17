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

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.FloatRange
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.Icon
import androidx.tv.material3.ListItem
import androidx.tv.material3.ListItemDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.bacbpl.iptv.R
import com.bacbpl.iptv.jetStram.presentation.screens.Device.DeviceSection
import com.bacbpl.iptv.jetStram.presentation.screens.dashboard.rememberChildPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.tv.material3.ExperimentalTvMaterial3Api
import com.bacbpl.iptv.ui.activities.StartScreen
import com.bacbpl.iptv.utils.LanguageManager
import com.bacbpl.iptv.utils.UserSession
import com.bacbpl.iptv.utils.rememberLanguageState
import androidx.compose.ui.platform.LocalConfiguration

@OptIn(ExperimentalComposeUiApi::class, ExperimentalTvMaterial3Api::class)
@Composable
fun ProfileScreen(
    @FloatRange(from = 0.0, to = 1.0)
    sidebarWidthFraction: Float = 0.19f,
    onLogOut: () -> Unit = {}
) {
    val childPadding = rememberChildPadding()
    val profileNavController = rememberNavController()
    val context = LocalContext.current

    val backStack by profileNavController.currentBackStackEntryAsState()
    val currentDestination = remember(backStack?.destination?.route) { backStack?.destination?.route }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var isLeftColumnFocused by remember { mutableStateOf(false) }

    // State for Privacy Policy Dialog
    var showPrivacyPolicy by remember { mutableStateOf(false) }

    // State for Contact Us Dialog
    var showContactDialog by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp
    val height = configuration.screenHeightDp

    Log.d("ScreenSize", "Width: $width dp, Height: $height dp")

    // Screen detection
    val is540p = width >= 960 && height >= 540 && width < 1280
    val is720p = width >= 1280 && height >= 720

    // EXTREMELY COMPACT sizes for 540p to fit all items including logout
    val listItemHeight = when {
        is720p -> 52.dp
        is540p -> 42.dp  // Even smaller to ensure logout fits
        else -> 56.dp
    }

    val verticalSpacing = when {
        is720p -> 4.dp
        is540p -> 2.dp
        else -> 6.dp
    }

    // Very small font for 540p
    val fontSize = when {
        is720p -> 13.sp
        is540p -> 10.sp  // Small but readable on TV
        else -> 16.sp
    }

    // Very small icon for 540p
    val iconSize = when {
        is720p -> 16.dp
        is540p -> 12.dp
        else -> 20.dp
    }

    // Calculate if all items will fit
    val totalItems = ProfileScreens.entries.size + 1 // 9 + 1 = 10 items
    val totalHeightNeeded = (listItemHeight * totalItems) + (verticalSpacing * (totalItems - 1))
    val availableHeight = height.dp

    Log.d("ProfileScreen", "=== SIZING INFO ===")
    Log.d("ProfileScreen", "Total items: $totalItems")
    Log.d("ProfileScreen", "Item height: $listItemHeight")
    Log.d("ProfileScreen", "Spacing: $verticalSpacing")
    Log.d("ProfileScreen", "Total needed: $totalHeightNeeded")
    Log.d("ProfileScreen", "Available: $availableHeight")
    Log.d("ProfileScreen", "Will fit: ${totalHeightNeeded <= availableHeight}")

    // Get language state
    val languageState = rememberLanguageState()

    // Get current language index for the LanguageSection
    var selectedLanguageIndex by rememberSaveable {
        mutableIntStateOf(
            when (languageState.currentLanguage) {
                LanguageManager.LANGUAGE_BENGALI -> 1
                LanguageManager.LANGUAGE_HINDI -> 2
                else -> 0
            }
        )
    }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    // Logout handler
    val handleLogout = {
        UserSession.clearSession(context)
        val intent = Intent(context, StartScreen::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
        onLogOut()
    }

    // Show Privacy Policy Dialog when needed
    if (showPrivacyPolicy) {
        PrivacyPolicyDialog(
            onDismiss = { showPrivacyPolicy = false }
        )
    }

    // Show Contact Us Dialog when needed
    if (showContactDialog) {
        ContactUsDialog(
            onDismiss = { showContactDialog = false },
            onSubmit = { name, email, subject, message ->
                android.util.Log.d("ContactUs", "Name: $name, Email: $email, Subject: $subject, Message: $message")
                showContactDialog = false
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = childPadding.start, vertical = childPadding.top)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(fraction = sidebarWidthFraction)
                .fillMaxHeight()
                // Keep scroll just in case, but with compact sizes it should fit
                .verticalScroll(rememberScrollState())
                .onFocusChanged {
                    isLeftColumnFocused = it.hasFocus
                }
                .focusRestorer()
                .focusGroup(),
            verticalArrangement = Arrangement.spacedBy(verticalSpacing)
        ) {
            // ALL profile screens including Help and Support
            ProfileScreens.entries.forEachIndexed { index, profileScreen ->
                key(index) {
                    val titleText = profileScreen.tabTitle?.let {
                        stringResource(id = it)
                    } ?: profileScreen.name

                    ListItem(
                        trailingContent = {
                            Icon(
                                profileScreen.icon,
                                modifier = Modifier
                                    .padding(vertical = if (is540p) 0.dp else 2.dp)
                                    .padding(start = 4.dp)
                                    .size(iconSize),
                                contentDescription = stringResource(
                                    id = R.string.profile_screen_listItem_icon_content_description,
                                    titleText
                                )
                            )
                        },
                        headlineContent = {
                            Text(
                                text = titleText,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = fontSize
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1,
                                letterSpacing = if (is540p) 0.sp else 0.5.sp  // Tighter spacing for 540p
                            )
                        },
                        selected = currentDestination == profileScreen.name,
                        onClick = {
                            focusManager.moveFocus(FocusDirection.Right)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(listItemHeight)
                            .then(
                                if (index == 0) Modifier.focusRequester(focusRequester)
                                else Modifier
                            )
                            .onFocusChanged {
                                if (it.isFocused && currentDestination != profileScreen.name) {
                                    profileNavController.navigate(profileScreen()) {
                                        currentDestination?.let { nnCurrentDestination ->
                                            popUpTo(nnCurrentDestination) { inclusive = true }
                                        }
                                        launchSingleTop = true
                                    }
                                }
                            },
                        scale = ListItemDefaults.scale(focusedScale = 1f),
                        colors = ListItemDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.inverseSurface,
                            selectedContainerColor = MaterialTheme.colorScheme.inverseSurface
                                .copy(alpha = 0.4f),
                            selectedContentColor = MaterialTheme.colorScheme.surface,
                        ),
                        shape = ListItemDefaults.shape(shape = MaterialTheme.shapes.extraSmall)
                    )
                }
            }

            // LOGOUT ITEM - Must be visible
            // Remove the spacer Box that was pushing it down
            key("logout") {
                ListItem(
                    trailingContent = {
                        Icon(
                            Icons.Default.Logout,
                            modifier = Modifier
                                .padding(vertical = if (is540p) 0.dp else 2.dp)
                                .padding(start = 4.dp)
                                .size(iconSize),
                            contentDescription = stringResource(id = R.string.log_out)
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(id = R.string.log_out),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = fontSize
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    selected = false,
                    onClick = { handleLogout() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(listItemHeight)
                        .clip(MaterialTheme.shapes.extraSmall)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                isLeftColumnFocused = true
                            }
                        },
                    scale = ListItemDefaults.scale(focusedScale = 1f),
                    colors = ListItemDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.errorContainer,
                        focusedContentColor = MaterialTheme.colorScheme.onErrorContainer,
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    shape = ListItemDefaults.shape(shape = MaterialTheme.shapes.extraSmall)
                )
            }
        }

        var isSubtitlesChecked by rememberSaveable { mutableStateOf(true) }

        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .onPreviewKeyEvent {
                    if (it.key == Key.Back && it.type == KeyEventType.KeyUp) {
                        while (!isLeftColumnFocused) {
                            focusManager.moveFocus(FocusDirection.Left)
                        }
                        return@onPreviewKeyEvent true
                    }
                    false
                },
            navController = profileNavController,
            startDestination = ProfileScreens.Accounts(),
            builder = {
                composable(ProfileScreens.Accounts()) {
                    AccountsSection(
                        onNavigateToLeft = {
                            focusManager.moveFocus(FocusDirection.Left)
                        }
                    )
                }
//                composable(ProfileScreens.Subscribe()) {
//                    SubscribeSection(
//                        isSubtitlesChecked = isSubtitlesChecked,
//                        onSubtitleCheckChange = { isSubtitlesChecked = it }
//                    )
//                }
                composable(ProfileScreens.Wallet()) {
                    WalletSection(
                        isSubtitlesChecked = isSubtitlesChecked,
                        onSubtitleCheckChange = { isSubtitlesChecked = it }
                    )
                }
                composable(ProfileScreens.Device()) {
                    DeviceSection(
                        isSubtitlesChecked = isSubtitlesChecked,
                        onSubtitleCheckChange = { isSubtitlesChecked = it }
                    )
                }
                composable(ProfileScreens.About()) {
                    AboutSection()
                }
//                composable(ProfileScreens.Subtitles()) {
//                    SubtitlesSection(
//                        isSubtitlesChecked = isSubtitlesChecked,
//                        onSubtitleCheckChange = { isSubtitlesChecked = it }
//                    )
//                }
                composable(ProfileScreens.Language()) {
                    LanguageSection(
                        selectedIndex = selectedLanguageIndex,
                        onSelectedIndexChange = { index ->
                            selectedLanguageIndex = index
                            val languageCode = when (index) {
                                1 -> LanguageManager.LANGUAGE_BENGALI
                                2 -> LanguageManager.LANGUAGE_HINDI
                                else -> LanguageManager.LANGUAGE_ENGLISH
                            }
                            languageState.updateLanguage(languageCode)
                        }
                    )
                }
                composable(ProfileScreens.SearchHistory()) {
                    SearchHistorySection()
                }
                composable(ProfileScreens.HelpAndSupport()) {
                    HelpAndSupportSection(
                        onNavigateToPrivacyPolicy = { showPrivacyPolicy = true },
                        onNavigateToFAQ = {
                            android.util.Log.d("ProfileScreen", "Navigate to FAQ")
                        },
                        onNavigateToContact = { showContactDialog = true }
                    )
                }
            }
        )
    }
}