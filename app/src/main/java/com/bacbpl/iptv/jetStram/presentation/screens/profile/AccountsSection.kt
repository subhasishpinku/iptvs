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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bacbpl.iptv.ui.activities.StartScreen
import com.bacbpl.iptv.utils.UserSession
import com.bacbpl.iptv.jetStram.presentation.screens.dashboard.rememberChildPadding
import com.bacbpl.iptv.jetStram.presentation.viewmodel.ProfileViewModel
import com.bacbpl.iptv.ui.activities.signupscreen.data.repository.Resource

// Icon constants
val QrCode = Icons.Default.QrCodeScanner
val ConfirmationNumber = Icons.Default.ConfirmationNumber
val LocationOn = Icons.Default.LocationOn
val PersonOutline = Icons.Default.PersonOutline
val Logout = Icons.Default.ExitToApp
val Subscriptions = Icons.Default.Subscriptions
val Map = Icons.Default.Map

@Immutable
data class AccountsSectionData(
    val title: String,
    val value: String? = null,
    val icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    val onClick: () -> Unit = {}
)

// Data class for subscriber information
data class SubscriberInfo(
    val useAltLcoCode: String = "0",
    val phone: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val address: String = "",
    val partnerReferenceId: String = "",
    val zone: String = "",
    val serviceNumber: String = "",
    val stateCode: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsSection(
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val isLoggedIn by UserSession.isLoggedIn.collectAsState()
    val userName by UserSession.userName.collectAsState()
    val userEmail by UserSession.userEmail.collectAsState()
    val userMobile by UserSession.userMobile.collectAsState()

    val childPadding = rememberChildPadding()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showSubscriberInfo by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    // Collect update profile state
    val updateProfileState by profileViewModel.updateProfileState.collectAsState()
    val isUpdating by profileViewModel.isUpdating.collectAsState()

    // Show snackbar for update result
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(updateProfileState) {
        when (updateProfileState) {
            is Resource.Success -> {
                snackbarHostState.showSnackbar(
                    (updateProfileState as Resource.Success).data.message,
                    duration = SnackbarDuration.Short
                )
                profileViewModel.resetUpdateState()
                showSubscriberInfo = false
            }
            is Resource.Error -> {
                snackbarHostState.showSnackbar(
                    (updateProfileState as Resource.Error).message,
                    duration = SnackbarDuration.Short
                )
                profileViewModel.resetUpdateState()
            }
            else -> {}
        }
    }

    // Subscriber info state
    var subscriberInfo by remember {
        mutableStateOf(
            SubscriberInfo(
                phone = userMobile?.replace("+91", "") ?: "",
                email = userEmail ?: "",
                firstName = userName?.split(" ")?.firstOrNull() ?: "",
                lastName = userName?.split(" ")?.drop(1)?.joinToString(" ") ?: ""
            )
        )
    }

    // Update session when composable is first composed
    LaunchedEffect(Unit) {
        UserSession.updateSession(context)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Black  // Set scaffold background to black
    ) { paddingValues ->
        if (!isLoggedIn) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.Black),  // Black background
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Please login to view profile",
                    color = Color.White,  // White text on black background
                    fontSize = 18.sp
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)  // Black background for main column
                    .padding(paddingValues)
            ) {
                // Quick Stats Row - Reduced height
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = childPadding.start, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickStatCard(
                        title = "Partner Ref ID",
                        value = subscriberInfo.partnerReferenceId.ifEmpty { "Not Set" },
                        icon = QrCode,
                        modifier = Modifier.weight(1f)
                    )
                    QuickStatCard(
                        title = "Zone",
                        value = subscriberInfo.zone.ifEmpty { "Not Set" },
                        icon = LocationOn,
                        modifier = Modifier.weight(1f)
                    )
                    QuickStatCard(
                        title = "Service No",
                        value = subscriberInfo.serviceNumber.ifEmpty { "Not Set" },
                        icon = ConfirmationNumber,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Main Account Section - Adjusted grid columns
                val accountsSectionListItems = remember(userName, userEmail, userMobile, subscriberInfo) {
                    listOf(
                        // Basic Info
                        AccountsSectionData(
                            title = "Name",
                            value = userName ?: "User",
                            icon = Icons.Default.Person
                        ),
                        AccountsSectionData(
                            title = "Email",
                            value = userEmail ?: "Email not set",
                            icon = Icons.Default.Email
                        ),
                        AccountsSectionData(
                            title = "Mobile",
                            value = userMobile ?: "Mobile not set",
                            icon = Icons.Default.Phone
                        ),

                        // Partner/Subscriber Info
                        AccountsSectionData(
                            title = "Use Alt LCO Code",
                            value = if (subscriberInfo.useAltLcoCode == "1") "Yes" else "No",
                            icon = Icons.Default.Settings
                        ),
                        AccountsSectionData(
                            title = "First Name",
                            value = subscriberInfo.firstName.ifEmpty { "Not set" },
                            icon = PersonOutline
                        ),
                        AccountsSectionData(
                            title = "Last Name",
                            value = subscriberInfo.lastName.ifEmpty { "Not set" },
                            icon = PersonOutline
                        ),
                        AccountsSectionData(
                            title = "Address",
                            value = subscriberInfo.address.ifEmpty { "Not set" },
                            icon = Icons.Default.Home
                        ),
                        AccountsSectionData(
                            title = "Partner Reference ID",
                            value = subscriberInfo.partnerReferenceId.ifEmpty { "Not set" },
                            icon = QrCode
                        ),
                        AccountsSectionData(
                            title = "Zone",
                            value = subscriberInfo.zone.ifEmpty { "Not set" },
                            icon = LocationOn
                        ),
                        AccountsSectionData(
                            title = "Service Number",
                            value = subscriberInfo.serviceNumber.ifEmpty { "Not set" },
                            icon = ConfirmationNumber
                        ),
                        AccountsSectionData(
                            title = "State Code",
                            value = subscriberInfo.stateCode.ifEmpty { "Not set" },
                            icon = Map
                        ),

                        // Actions
                        AccountsSectionData(
                            title = "Edit Subscriber Info",
                            icon = Icons.Default.Edit,
                            onClick = { showSubscriberInfo = true }
                        ),
                        AccountsSectionData(
                            title = "Change Password",
                            value = "Change",
                            icon = Icons.Default.Lock,
                            onClick = { /* Navigate to change password */ }
                        ),
                        AccountsSectionData(
                            title = "View Subscriptions",
                            icon = Subscriptions,
                            onClick = { /* Navigate to subscriptions */ }
                        ),
                        AccountsSectionData(
                            title = "Log Out",
                            icon = Logout,
                            onClick = {
                                UserSession.clearSession(context)
                                val intent = Intent(context, StartScreen::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                context.startActivity(intent)
                            }
                        ),
                        AccountsSectionData(
                            title = "Delete Account",
                            icon = Icons.Default.Delete,
                            onClick = { showDeleteDialog = true }
                        )
                    )
                }

                LazyVerticalGrid(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = childPadding.start),
                    columns = GridCells.Fixed(3),
                    content = {
                        items(accountsSectionListItems.size) { index ->
                            AccountsSelectionItem(
                                modifier = Modifier
                                    .focusRequester(if (index == 0) focusRequester else FocusRequester())
                                    .padding(4.dp),
                                key = index,
                                accountsSectionData = accountsSectionListItems[index]
                            )
                        }
                    }
                )
            }

            // Subscriber Info Edit Dialog
            if (showSubscriberInfo) {
                SubscriberInfoDialog(
                    subscriberInfo = subscriberInfo,
                    isUpdating = isUpdating,
                    onDismiss = {
                        showSubscriberInfo = false
                        profileViewModel.resetUpdateState()
                    },
                    onSave = { updatedInfo ->
                        subscriberInfo = updatedInfo
                        profileViewModel.updateSubscriberInfo(updatedInfo)
                    }
                )
            }

            AccountsSectionDeleteDialog(
                showDialog = showDeleteDialog,
                onDismissRequest = { showDeleteDialog = false },
                modifier = Modifier.width(428.dp)
            )
        }
    }
}

@Composable
fun QuickStatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(70.dp), // Reduced from 80dp to 70dp
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A)  // Dark gray, not pure black for contrast
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp), // Reduced padding from 8dp to 6dp
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(0xFFE50914),
                modifier = Modifier.size(18.dp) // Reduced from 20dp to 18dp
            )
            Text(
                text = value,
                color = Color.White,
                fontSize = 11.sp, // Reduced from 12sp to 11sp
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Text(
                text = title,
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 9.sp, // Reduced from 10sp to 9sp
                maxLines = 1
            )
        }
    }
}

@Composable
fun SubscriberInfoDialog(
    subscriberInfo: SubscriberInfo,
    isUpdating: Boolean = false,
    onDismiss: () -> Unit,
    onSave: (SubscriberInfo) -> Unit
) {
    var useAltLcoCode by remember { mutableStateOf(subscriberInfo.useAltLcoCode) }
    var phone by remember { mutableStateOf(subscriberInfo.phone) }
    var email by remember { mutableStateOf(subscriberInfo.email) }
    var firstName by remember { mutableStateOf(subscriberInfo.firstName) }
    var lastName by remember { mutableStateOf(subscriberInfo.lastName) }
    var address by remember { mutableStateOf(subscriberInfo.address) }
    var partnerReferenceId by remember { mutableStateOf(subscriberInfo.partnerReferenceId) }
    var zone by remember { mutableStateOf(subscriberInfo.zone) }
    var serviceNumber by remember { mutableStateOf(subscriberInfo.serviceNumber) }
    var stateCode by remember { mutableStateOf(subscriberInfo.stateCode) }

    AlertDialog(
        onDismissRequest = {
            if (!isUpdating) onDismiss()
        },
        title = {
            Text(
                text = "Edit Subscriber Information",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .heightIn(max = 400.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Use Alt LCO Code - Made more compact
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp), // Reduced from 8dp to 6dp
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Use Alt LCO Code:",
                        color = Color.White,
                        fontSize = 13.sp, // Added explicit font size
                        modifier = Modifier.weight(1f)
                    )
                    Row {
                        listOf("No (0)", "Yes (1)").forEachIndexed { index, option ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 3.dp) // Reduced from 4dp to 3dp
                                    .background(
                                        color = if ((index == 0 && useAltLcoCode == "0") ||
                                            (index == 1 && useAltLcoCode == "1"))
                                            Color(0xFFE50914)
                                        else
                                            Color(0xFF333333),
                                        shape = RoundedCornerShape(3.dp) // Reduced from 4dp to 3dp
                                    )
                                    .clickable(enabled = !isUpdating) {
                                        useAltLcoCode = if (index == 0) "0" else "1"
                                    }
                                    .padding(horizontal = 10.dp, vertical = 5.dp) // Reduced padding
                            ) {
                                Text(
                                    text = option,
                                    color = Color.White,
                                    fontSize = 12.sp // Added explicit font size
                                )
                            }
                        }
                    }
                }

                SubscriberInfoField(
                    label = "Phone (10 digits)",
                    value = phone,
                    onValueChange = { if (it.length <= 10) phone = it },
                    isNumber = true,
                    enabled = !isUpdating
                )

                SubscriberInfoField(
                    label = "Email",
                    value = email,
                    onValueChange = { email = it },
                    enabled = !isUpdating
                )

                SubscriberInfoField(
                    label = "First Name",
                    value = firstName,
                    onValueChange = { firstName = it },
                    enabled = !isUpdating
                )

                SubscriberInfoField(
                    label = "Last Name",
                    value = lastName,
                    onValueChange = { lastName = it },
                    enabled = !isUpdating
                )

                SubscriberInfoField(
                    label = "Address",
                    value = address,
                    onValueChange = { address = it },
                    isMultiline = true,
                    enabled = !isUpdating
                )

                SubscriberInfoField(
                    label = "Partner Reference ID",
                    value = partnerReferenceId,
                    onValueChange = { if (it.length <= 30) partnerReferenceId = it },
                    enabled = !isUpdating
                )

                SubscriberInfoField(
                    label = "Zone",
                    value = zone,
                    onValueChange = { zone = it },
                    enabled = !isUpdating
                )

                SubscriberInfoField(
                    label = "Service Number",
                    value = serviceNumber,
                    onValueChange = { serviceNumber = it },
                    enabled = !isUpdating
                )

                SubscriberInfoField(
                    label = "State Code",
                    value = stateCode,
                    onValueChange = { stateCode = it },
                    enabled = !isUpdating
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(
                        SubscriberInfo(
                            useAltLcoCode = useAltLcoCode,
                            phone = phone,
                            email = email,
                            firstName = firstName,
                            lastName = lastName,
                            address = address,
                            partnerReferenceId = partnerReferenceId,
                            zone = zone,
                            serviceNumber = serviceNumber,
                            stateCode = stateCode
                        )
                    )
                },
                enabled = !isUpdating
            ) {
                if (isUpdating) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp), // Reduced from 20dp to 18dp
                        color = Color(0xFFE50914)
                    )
                } else {
                    Text("Save", color = Color(0xFFE50914))
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isUpdating
            ) {
                Text("Cancel", color = Color.White)
            }
        },
        containerColor = Color(0xFF1A1A1A),  // Dark background for dialog
        titleContentColor = Color.White,
        textContentColor = Color.White
    )
}

@Composable
fun SubscriberInfoField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isNumber: Boolean = false,
    isMultiline: Boolean = false,
    enabled: Boolean = true
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp) // Reduced from 4dp to 3dp
    ) {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 11.sp, // Reduced from 12sp to 11sp
            modifier = Modifier.padding(bottom = 3.dp) // Reduced from 4dp to 3dp
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isMultiline) 65.dp else 42.dp) // Reduced from 80dp/48dp to 65dp/42dp
                .background(
                    color = if (isFocused) Color(0xFF333333) else Color(0xFF2A2A2A),
                    shape = RoundedCornerShape(3.dp) // Reduced from 4dp to 3dp
                )
                .border(
                    width = 1.dp,
                    color = if (isFocused) Color(0xFFE50914) else Color.Transparent,
                    shape = RoundedCornerShape(3.dp)
                )
                .onFocusChanged { isFocused = it.isFocused }
                .focusable(enabled = enabled)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 6.dp), // Reduced padding
                textStyle = LocalTextStyle.current.copy(
                    color = Color.White,
                    fontSize = 13.sp // Reduced from 14sp to 13sp
                ),
                keyboardOptions = if (isNumber) {
                    KeyboardOptions(keyboardType = KeyboardType.Number)
                } else {
                    KeyboardOptions.Default
                },
                maxLines = if (isMultiline) 3 else 1,
                singleLine = !isMultiline,
                enabled = enabled
            )
        }
    }
}