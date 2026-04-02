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
//
package com.bacbpl.iptv.jetStram.presentation.screens.profile

import android.content.Context
import android.content.Intent
import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.bacbpl.iptv.jetStram.presentation.theme.JetStreamTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.tv.material3.ExperimentalTvMaterial3Api
import com.bacbpl.iptv.ui.activities.StartScreen
import com.bacbpl.iptv.utils.LanguageManager
import com.bacbpl.iptv.utils.UserSession
import com.bacbpl.iptv.utils.rememberLanguageState

//@OptIn(ExperimentalComposeUiApi::class, ExperimentalTvMaterial3Api::class)
//@Composable
//fun ProfileScreen(
//    @FloatRange(from = 0.0, to = 1.0)
//    sidebarWidthFraction: Float = 0.17f,
//    onLogOut: () -> Unit = {}
//) {
//    val childPadding = rememberChildPadding()
//    val profileNavController = rememberNavController()
//    val context = LocalContext.current
//
//    val backStack by profileNavController.currentBackStackEntryAsState()
//    val currentDestination =
//        remember(backStack?.destination?.route) { backStack?.destination?.route }
//    val focusRequester = remember { FocusRequester() }
//    val focusManager = LocalFocusManager.current
//    var isLeftColumnFocused by remember { mutableStateOf(false) }
//
//    // State for Privacy Policy Dialog
//    var showPrivacyPolicy by remember { mutableStateOf(false) }
//
//    // State for Contact Us Dialog
//    var showContactDialog by remember { mutableStateOf(false) }
//
//    LaunchedEffect(Unit) { focusRequester.requestFocus() }
//
//    // Logout handler
//    val handleLogout = {
//        // Clear user session
//        UserSession.clearSession(context)
//
//        // Navigate to StartScreen
//        val intent = Intent(context, StartScreen::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        context.startActivity(intent)
//
//        // Call the original onLogOut callback if needed
//        onLogOut()
//    }
//
//    // Show Privacy Policy Dialog when needed
//    if (showPrivacyPolicy) {
//        PrivacyPolicyDialog(
//            onDismiss = { showPrivacyPolicy = false }
//        )
//    }
//
//    // Show Contact Us Dialog when needed
//    if (showContactDialog) {
//        ContactUsDialog(
//            onDismiss = { showContactDialog = false },
//            onSubmit = { name, email, subject, message ->
//                // Handle form submission - send to backend or email
//                // For now, just log and close
//                android.util.Log.d("ContactUs", "Name: $name, Email: $email, Subject: $subject, Message: $message")
//                // You can add API call here to submit the contact form
//                showContactDialog = false
//            }
//        )
//    }
//
//    Row(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(horizontal = childPadding.start, vertical = childPadding.top)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth(fraction = sidebarWidthFraction)
//                .verticalScroll(rememberScrollState())
//                .fillMaxHeight()
//                .onFocusChanged {
//                    isLeftColumnFocused = it.hasFocus
//                }
//                .focusRestorer()
//                .focusGroup(),
//            verticalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            // Regular profile screens
//            ProfileScreens.entries.forEachIndexed { index, profileScreen ->
//                key(index) {
//                    ListItem(
//                        trailingContent = {
//                            Icon(
//                                profileScreen.icon,
//                                modifier = Modifier
//                                    .padding(vertical = 2.dp)
//                                    .padding(start = 4.dp)
//                                    .size(20.dp),
//                                contentDescription = stringResource(
//                                    id = R.string.profile_screen_listItem_icon_content_description,
//                                    profileScreen.tabTitle
//                                )
//                            )
//                        },
//                        headlineContent = {
//                            Text(
//                                text = profileScreen.tabTitle,
//                                style = MaterialTheme.typography.bodyMedium.copy(
//                                    fontWeight = FontWeight.Medium
//                                ),
//                                modifier = Modifier.fillMaxWidth()
//                            )
//                        },
//                        selected = currentDestination == profileScreen.name,
//                        onClick = { focusManager.moveFocus(FocusDirection.Right) },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .then(
//                                if (index == 0) Modifier.focusRequester(focusRequester)
//                                else Modifier
//                            )
//                            .onFocusChanged {
//                                if (it.isFocused && currentDestination != profileScreen.name) {
//                                    profileNavController.navigate(profileScreen()) {
//                                        currentDestination?.let { nnCurrentDestination ->
//                                            popUpTo(nnCurrentDestination) { inclusive = true }
//                                        }
//                                        launchSingleTop = true
//                                    }
//                                }
//                            },
//                        scale = ListItemDefaults.scale(focusedScale = 1f),
//                        colors = ListItemDefaults.colors(
//                            focusedContainerColor = MaterialTheme.colorScheme.inverseSurface,
//                            selectedContainerColor = MaterialTheme.colorScheme.inverseSurface
//                                .copy(alpha = 0.4f),
//                            selectedContentColor = MaterialTheme.colorScheme.surface,
//                        ),
//                        shape = ListItemDefaults.shape(shape = MaterialTheme.shapes.extraSmall)
//                    )
//                }
//            }
//
//            // Spacer to push Log Out to bottom
//            Box(modifier = Modifier.weight(1f))
//
//            // Log Out item
//            key("logout") {
//                ListItem(
//                    trailingContent = {
//                        Icon(
//                            Icons.Default.Logout,
//                            modifier = Modifier
//                                .padding(vertical = 2.dp)
//                                .padding(start = 4.dp)
//                                .size(20.dp),
//                            contentDescription = stringResource(id = R.string.log_out)
//                        )
//                    },
//                    headlineContent = {
//                        Text(
//                            text = stringResource(id = R.string.log_out),
//                            style = MaterialTheme.typography.bodyMedium.copy(
//                                fontWeight = FontWeight.Medium
//                            ),
//                            modifier = Modifier.fillMaxWidth()
//                        )
//                    },
//                    selected = false,
//                    onClick = { handleLogout() },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clip(MaterialTheme.shapes.extraSmall)
//                        .onFocusChanged { focusState ->
//                            if (focusState.isFocused) {
//                                isLeftColumnFocused = true
//                            }
//                        },
//                    scale = ListItemDefaults.scale(focusedScale = 1f),
//                    colors = ListItemDefaults.colors(
//                        focusedContainerColor = MaterialTheme.colorScheme.errorContainer,
//                        focusedContentColor = MaterialTheme.colorScheme.onErrorContainer,
//                        containerColor = Color.Transparent,
//                        contentColor = MaterialTheme.colorScheme.error
//                    ),
//                    shape = ListItemDefaults.shape(shape = MaterialTheme.shapes.extraSmall)
//                )
//            }
//        }
//
//        var selectedLanguageIndex by rememberSaveable { mutableIntStateOf(0) }
//        var isSubtitlesChecked by rememberSaveable { mutableStateOf(true) }
//
//        NavHost(
//            modifier = Modifier
//                .fillMaxSize()
//                .onPreviewKeyEvent {
//                    if (it.key == Key.Back && it.type == KeyEventType.KeyUp) {
//                        while (!isLeftColumnFocused) {
//                            focusManager.moveFocus(FocusDirection.Left)
//                        }
//                        return@onPreviewKeyEvent true
//                    }
//                    false
//                },
//            navController = profileNavController,
//            startDestination = ProfileScreens.Accounts(),
//            builder = {
//                composable(ProfileScreens.Accounts()) {
//                    AccountsSection()
//                }
//                composable(ProfileScreens.Subscribe()) {
//                    SubscribeSection(
//                        isSubtitlesChecked = isSubtitlesChecked,
//                        onSubtitleCheckChange = { isSubtitlesChecked = it }
//                    )
//                }
//                composable(ProfileScreens.Wallet()) {
//                    WalletSection(
//                        isSubtitlesChecked = isSubtitlesChecked,
//                        onSubtitleCheckChange = { isSubtitlesChecked = it }
//                    )
//                }
//                composable(ProfileScreens.Device()) {
//                    DeviceSection(
//                        isSubtitlesChecked = isSubtitlesChecked,
//                        onSubtitleCheckChange = { isSubtitlesChecked = it }
//                    )
//                }
//                composable(ProfileScreens.About()) {
//                    AboutSection()
//                }
//                composable(ProfileScreens.Subtitles()) {
//                    SubtitlesSection(
//                        isSubtitlesChecked = isSubtitlesChecked,
//                        onSubtitleCheckChange = { isSubtitlesChecked = it }
//                    )
//                }
//                composable(ProfileScreens.Language()) {
//                    LanguageSection(
//                        selectedIndex = selectedLanguageIndex,
//                        onSelectedIndexChange = { selectedLanguageIndex = it }
//                    )
//                }
//                composable(ProfileScreens.SearchHistory()) {
//                    SearchHistorySection()
//                }
//                composable(ProfileScreens.HelpAndSupport()) {
//                    HelpAndSupportSection(
//                        onNavigateToPrivacyPolicy = { showPrivacyPolicy = true },
//                        onNavigateToFAQ = {
//                            // Handle FAQ navigation - you can open a FAQ dialog or webview
//                            android.util.Log.d("ProfileScreen", "Navigate to FAQ")
//                        },
//                        onNavigateToContact = { showContactDialog = true }
//                    )
//                }
//            }
//        )
//    }
//}

// com/bacbpl/iptv/jetStram/presentation/screens/profile/ProfileScreen.kt
// Add language selection handling

// In your ProfileScreen composable, use it like this:

@OptIn(ExperimentalComposeUiApi::class, ExperimentalTvMaterial3Api::class)
@Composable
fun ProfileScreen(
    @FloatRange(from = 0.0, to = 1.0)
    sidebarWidthFraction: Float = 0.17f,
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

    // ✅ CORRECT USAGE: Get language state
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
                .verticalScroll(rememberScrollState())
                .fillMaxHeight()
                .onFocusChanged {
                    isLeftColumnFocused = it.hasFocus
                }
                .focusRestorer()
                .focusGroup(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Regular profile screens
            ProfileScreens.entries.forEachIndexed { index, profileScreen ->
                key(index) {
                    // Get the title text - handle null case
                    val titleText = profileScreen.tabTitle?.let {
                        stringResource(id = it)
                    } ?: profileScreen.name

                    ListItem(
                        trailingContent = {
                            Icon(
                                profileScreen.icon,
                                modifier = Modifier
                                    .padding(vertical = 2.dp)
                                    .padding(start = 4.dp)
                                    .size(20.dp),
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
                                    fontWeight = FontWeight.Medium
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        selected = currentDestination == profileScreen.name,
                        onClick = { focusManager.moveFocus(FocusDirection.Right) },
                        modifier = Modifier
                            .fillMaxWidth()
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

            // Spacer to push Log Out to bottom
            Box(modifier = Modifier.weight(1f))

            // Log Out item
            key("logout") {
                ListItem(
                    trailingContent = {
                        Icon(
                            Icons.Default.Logout,
                            modifier = Modifier
                                .padding(vertical = 2.dp)
                                .padding(start = 4.dp)
                                .size(20.dp),
                            contentDescription = stringResource(id = R.string.log_out)
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(id = R.string.log_out),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    selected = false,
                    onClick = { handleLogout() },
                    modifier = Modifier
                        .fillMaxWidth()
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
                    AccountsSection()
                }
                composable(ProfileScreens.Subscribe()) {
                    SubscribeSection(
                        isSubtitlesChecked = isSubtitlesChecked,
                        onSubtitleCheckChange = { isSubtitlesChecked = it }
                    )
                }
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
                composable(ProfileScreens.Subtitles()) {
                    SubtitlesSection(
                        isSubtitlesChecked = isSubtitlesChecked,
                        onSubtitleCheckChange = { isSubtitlesChecked = it }
                    )
                }
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