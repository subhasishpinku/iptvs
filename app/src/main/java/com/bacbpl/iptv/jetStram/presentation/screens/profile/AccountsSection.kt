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
import com.bacbpl.iptv.ui.activities.StartScreen
import com.bacbpl.iptv.utils.UserSession
import com.bacbpl.iptv.jetStram.presentation.screens.dashboard.rememberChildPadding

// Add missing icon constants
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
    val useAltLcoCode: String = "0", // "1" if partner operator code should be used
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

@Composable
fun AccountsSection() {
    val context = LocalContext.current
    val isLoggedIn by UserSession.isLoggedIn.collectAsState()
    val userName by UserSession.userName.collectAsState()
    val userEmail by UserSession.userEmail.collectAsState()
    val userMobile by UserSession.userMobile.collectAsState()

    val childPadding = rememberChildPadding()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showSubscriberInfo by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

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

    if (!isLoggedIn) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(childPadding.start),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Please login to view profile",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Quick Stats Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = childPadding.start, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
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

            // Main Account Section
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
                onDismiss = { showSubscriberInfo = false },
                onSave = { updatedInfo ->
                    subscriberInfo = updatedInfo
                    showSubscriberInfo = false
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

@Composable
fun QuickStatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(0xFFE50914),
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = value,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Text(
                text = title,
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 10.sp,
                maxLines = 1
            )
        }
    }
}

@Composable
fun SubscriberInfoDialog(
    subscriberInfo: SubscriberInfo,
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
        onDismissRequest = onDismiss,
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
                // Use Alt LCO Code
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Use Alt LCO Code:",
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    Row {
                        listOf("No (0)", "Yes (1)").forEachIndexed { index, option ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .background(
                                        color = if ((index == 0 && useAltLcoCode == "0") ||
                                            (index == 1 && useAltLcoCode == "1"))
                                            Color(0xFFE50914)
                                        else
                                            Color(0xFF333333),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .clickable {
                                        useAltLcoCode = if (index == 0) "0" else "1"
                                    }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = option,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                SubscriberInfoField(
                    label = "Phone (10 digits)",
                    value = phone,
                    onValueChange = { if (it.length <= 10) phone = it },
                    isNumber = true
                )

                SubscriberInfoField(
                    label = "Email",
                    value = email,
                    onValueChange = { email = it }
                )

                SubscriberInfoField(
                    label = "First Name",
                    value = firstName,
                    onValueChange = { firstName = it }
                )

                SubscriberInfoField(
                    label = "Last Name",
                    value = lastName,
                    onValueChange = { lastName = it }
                )

                SubscriberInfoField(
                    label = "Address",
                    value = address,
                    onValueChange = { address = it },
                    isMultiline = true
                )

                SubscriberInfoField(
                    label = "Partner Reference ID",
                    value = partnerReferenceId,
                    onValueChange = { if (it.length <= 30) partnerReferenceId = it }
                )

                SubscriberInfoField(
                    label = "Zone",
                    value = zone,
                    onValueChange = { zone = it }
                )

                SubscriberInfoField(
                    label = "Service Number",
                    value = serviceNumber,
                    onValueChange = { serviceNumber = it }
                )

                SubscriberInfoField(
                    label = "State Code",
                    value = stateCode,
                    onValueChange = { stateCode = it }
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
                }
            ) {
                Text("Save", color = Color(0xFFE50914))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color.White)
            }
        },
        containerColor = Color(0xFF1A1A1A),
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
    isMultiline: Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isMultiline) 80.dp else 48.dp)
                .background(
                    color = if (isFocused) Color(0xFF333333) else Color(0xFF2A2A2A),
                    shape = RoundedCornerShape(4.dp)
                )
                .border(
                    width = 1.dp,
                    color = if (isFocused) Color(0xFFE50914) else Color.Transparent,
                    shape = RoundedCornerShape(4.dp)
                )
                .onFocusChanged { isFocused = it.isFocused }
                .focusable()
        )
        {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                textStyle = LocalTextStyle.current.copy(
                    color = Color.White,
                    fontSize = 14.sp
                ),
                keyboardOptions = if (isNumber) {
                    KeyboardOptions(keyboardType = KeyboardType.Number)
                } else {
                    KeyboardOptions.Default
                },
                maxLines = if (isMultiline) 3 else 1,
                singleLine = !isMultiline
            )
        }
    }
}