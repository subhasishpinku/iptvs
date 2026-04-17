/////*
//// * Copyright 2023 Google LLC
//// *
//// * Licensed under the Apache License, Version 2.0 (the "License");
//// * you may not use this file except in compliance with the License.
//// * You may obtain a copy of the License at
//// *
//// * https://www.apache.org/licenses/LICENSE-2.0
//// *
//// * Unless required by applicable law or agreed to in writing, software
//// * distributed under the License is distributed on an "AS IS" BASIS,
//// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//// * See the License for the specific language governing permissions and
//// * limitations under the License.
//// */
////
////package  com.bacbpl.iptv.jetStram.presentation.screens.profile
////
////import androidx.compose.material.icons.Icons
////import androidx.compose.material.icons.filled.Info
////import androidx.compose.material.icons.filled.Person
////import androidx.compose.material.icons.filled.Search
////import androidx.compose.material.icons.filled.Subscriptions
////import androidx.compose.material.icons.filled.Subtitles
////import androidx.compose.material.icons.filled.Support
////import androidx.compose.material.icons.filled.Translate
////import androidx.compose.material.icons.outlined.AccountBalanceWallet
////import androidx.compose.material.icons.outlined.Devices
////import androidx.compose.ui.graphics.vector.ImageVector
////
////enum class ProfileScreens(
////    val icon: ImageVector,
////    private val title: String? = null,
////) {
////    Accounts(Icons.Default.Person),
////
////    Subscribe(Icons.Default.Subscriptions),
////    Wallet(Icons.Outlined.AccountBalanceWallet, "Payment"),
////    Device(Icons.Outlined.Devices, "Device Info"),
////
////    About(Icons.Default.Info),
////    Subtitles(Icons.Default.Subtitles),
////    Language(Icons.Default.Translate),
////    SearchHistory(title = "Search history", icon = Icons.Default.Search),
////    HelpAndSupport(title = "Help and Support", icon = Icons.Default.Support);
////
////    operator fun invoke() = name
////
////    val tabTitle = title ?: name
////}
//// com/bacbpl/iptv/jetStram/presentation/screens/profile/ProfileScreens.kt
//package com.bacbpl.iptv.jetStram.presentation.screens.profile
//
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material.icons.outlined.AccountBalanceWallet
//import androidx.compose.material.icons.outlined.Devices
//import androidx.compose.ui.graphics.vector.ImageVector
//
//enum class ProfileScreens(
//    val icon: ImageVector,
//    private val title: String? = null,
//) {
//    Accounts(Icons.Default.Person),
//    Subscribe(Icons.Default.Subscriptions),
//    Wallet(Icons.Outlined.AccountBalanceWallet, "Payment"),
//    Device(Icons.Outlined.Devices, "Device Info"),
//    About(Icons.Default.Info),
//    Subtitles(Icons.Default.Subtitles),
//    Language(Icons.Default.Translate),  // This should already be here
//    SearchHistory(title = "Search history", icon = Icons.Default.Search),
//    HelpAndSupport(title = "Help and Support", icon = Icons.Default.Support);
//
//    operator fun invoke() = name
//
//    val tabTitle = title ?: name
//}

// com/bacbpl/iptv/jetStram/presentation/screens/profile/ProfileScreens.kt
package com.bacbpl.iptv.jetStram.presentation.screens.profile

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.ui.graphics.vector.ImageVector
import com.bacbpl.iptv.R

enum class ProfileScreens(
    val icon: ImageVector,
    @StringRes private val titleResId: Int? = null,
) {
    Accounts(
        icon = Icons.Default.Person,
        titleResId = R.string.profile_accounts
    ),
//    Subscribe(
//        icon = Icons.Default.Subscriptions,
//        titleResId = R.string.profile_subscribe
//    ),
    Wallet(
        icon = Icons.Outlined.AccountBalanceWallet,
        titleResId = R.string.profile_payment
    ),
    Device(
        icon = Icons.Outlined.Devices,
        titleResId = R.string.profile_device_info
    ),
    About(
        icon = Icons.Default.Info,
        titleResId = R.string.profile_about
    ),
//    Subtitles(
//        icon = Icons.Default.Subtitles,
//        titleResId = R.string.profile_subtitles
//    ),
    Language(
        icon = Icons.Default.Translate,
        titleResId = R.string.profile_language
    ),
    SearchHistory(
        icon = Icons.Default.Search,
        titleResId = R.string.profile_search_history
    ),
    HelpAndSupport(
        icon = Icons.Default.Support,
        titleResId = R.string.profile_help_support
    );

    operator fun invoke() = name

    val tabTitle: Int?
        get() = titleResId
}