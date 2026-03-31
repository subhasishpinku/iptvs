package com.bacbpl.iptv.jetStram.presentation.screens.profile

import android.content.Intent
import android.util.Log
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
import com.bacbpl.iptv.jetStram.presentation.viewmodel.LogoutViewModel
import com.bacbpl.iptv.ui.activities.signupscreen.data.repository.Resource

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
    profileViewModel: ProfileViewModel = hiltViewModel(),
    logoutViewModel: LogoutViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val isLoggedIn by UserSession.isLoggedIn.collectAsState()
    val userName by UserSession.userName.collectAsState()
    val userEmail by UserSession.userEmail.collectAsState()
    val userMobile by UserSession.userMobile.collectAsState()
    val deviceId by UserSession.deviceId.collectAsState()

    val childPadding = rememberChildPadding()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showSubscriberInfo by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val updateProfileState by profileViewModel.updateProfileState.collectAsState()
    val isUpdating by profileViewModel.isUpdating.collectAsState()
    val subscriberDetailsState by profileViewModel.subscriberDetailsState.collectAsState()
    var isLoadingSubscriberDetails by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    // Observe logout state
    val logoutState by logoutViewModel.logoutState.collectAsState()
    val isLoggingOut by logoutViewModel.isLoggingOut.collectAsState()

    // Handle logout response
    LaunchedEffect(logoutState) {
        when (logoutState) {
            is Resource.Success -> {
                val message = (logoutState as Resource.Success).data?.message ?: "Logged out successfully"
                snackbarHostState.showSnackbar(
                    message,
                    duration = SnackbarDuration.Short
                )
                // Navigate to StartScreen
                val intent = Intent(context, StartScreen::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
                logoutViewModel.resetLogoutState()
            }
            is Resource.Error -> {
                val error = (logoutState as Resource.Error).message ?: "Logout failed"
                snackbarHostState.showSnackbar(
                    error,
                    duration = SnackbarDuration.Short
                )
                // Even on error, we should clear local session
                UserSession.clearSession(context)
                val intent = Intent(context, StartScreen::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
                logoutViewModel.resetLogoutState()
            }
            else -> {}
        }
    }

    LaunchedEffect(updateProfileState) {
        if (updateProfileState is Resource.Success) {
            snackbarHostState.showSnackbar(
                (updateProfileState as Resource.Success).data?.message ?: "Profile updated successfully",
                duration = SnackbarDuration.Short
            )
            profileViewModel.resetUpdateState()
            showSubscriberInfo = false
        } else if (updateProfileState is Resource.Error) {
            snackbarHostState.showSnackbar(
                (updateProfileState as Resource.Error).message ?: "Failed to update profile",
                duration = SnackbarDuration.Short
            )
            profileViewModel.resetUpdateState()
        }
    }

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

    LaunchedEffect(userMobile) {
        if (isLoggedIn && userMobile != null) {
            val mobileNumber = userMobile!!.replace("+91", "").replace(" ", "")
            if (mobileNumber.isNotEmpty()) {
                isLoadingSubscriberDetails = true
                profileViewModel.getSubscriberDetails(mobileNumber, context)
            }
        }
    }

    LaunchedEffect(subscriberDetailsState) {
        if (subscriberDetailsState is Resource.Success) {
            isLoadingSubscriberDetails = false
            val response = (subscriberDetailsState as Resource.Success).data
            response?.let { details ->
                val ottplayData = details.ottplayDetails?.data
                val subscriber = details.subscriber

                subscriberInfo = subscriberInfo.copy(
                    useAltLcoCode = subscriber?.useAltLcoCode ?: "0",
                    phone = subscriber?.mobile ?: ottplayData?.phone ?: subscriberInfo.phone,
                    email = subscriber?.email ?: ottplayData?.email ?: subscriberInfo.email,
                    firstName = subscriber?.firstname ?: ottplayData?.name?.split(" ")?.firstOrNull() ?: subscriberInfo.firstName,
                    lastName = subscriber?.lastname ?: ottplayData?.name?.split(" ")?.drop(1)?.joinToString(" ") ?: subscriberInfo.lastName,
                    address = subscriber?.address ?: subscriberInfo.address,
                    zone = subscriber?.zone ?: ottplayData?.zone ?: subscriberInfo.zone,
                    serviceNumber = subscriber?.serviceNumber ?: ottplayData?.serviceNo ?: subscriberInfo.serviceNumber,
                    stateCode = subscriber?.stateCode ?: subscriberInfo.stateCode,
                    partnerReferenceId = ottplayData?.subCode ?: subscriberInfo.partnerReferenceId
                )
            }
        } else if (subscriberDetailsState is Resource.Error) {
            isLoadingSubscriberDetails = false
        }
    }

    LaunchedEffect(Unit) {
        UserSession.updateSession(context)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Black
    ) { paddingValues ->
        if (!isLoggedIn) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Please login to view profile",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(paddingValues)
                ) {
                    if (isLoadingSubscriberDetails) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = Color(0xFFE50914),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

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

                    val accountsSectionListItems = remember(userName, userEmail, userMobile, subscriberInfo) {
                        listOf(
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
                            AccountsSectionData(
                                title = "Edit Subscriber Info",
                                icon = Icons.Default.Edit,
                                onClick = { showSubscriberInfo = true }
                            ),
                            AccountsSectionData(
                                title = "Change Password",
                                value = "Change",
                                icon = Icons.Default.Lock,
                                onClick = { }
                            ),
                            AccountsSectionData(
                                title = "View Subscriptions",
                                icon = Subscriptions,
                                onClick = { }
                            ),
                            AccountsSectionData(
                                title = "Log Out",
                                icon = Logout,
                                onClick = {
                                    if (!isLoggingOut) {
                                        // Make sure deviceId is not null
                                        val currentDeviceId = deviceId ?: UserSession.getDeviceId(context)
                                        if (currentDeviceId != null) {
                                            logoutViewModel.logout(currentDeviceId, context)
                                        } else {
                                            // Handle case where deviceId is null
                                            Log.e("AccountsSection", "Device ID is null")
                                            // Still clear local session
                                            UserSession.clearSession(context)
                                            val intent = Intent(context, StartScreen::class.java)
                                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            context.startActivity(intent)
                                        }
                                    }
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
                    ) {
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
                }

                // Show loading indicator while logging out
                if (isLoggingOut) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.7f))
                            .clickable(enabled = false) { },
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .width(200.dp)
                                .padding(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(
                                    color = Color(0xFFE50914),
                                    modifier = Modifier.size(40.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Logging out...",
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

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
                        profileViewModel.updateSubscriberInfo(updatedInfo, context)
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
        modifier = modifier.height(70.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(0xFFE50914),
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = value,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Text(
                text = title,
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 9.sp,
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Use Alt LCO Code:",
                        color = Color.White,
                        fontSize = 13.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Row {
                        listOf("No (0)", "Yes (1)").forEachIndexed { index, option ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 3.dp)
                                    .background(
                                        color = if ((index == 0 && useAltLcoCode == "0") ||
                                            (index == 1 && useAltLcoCode == "1"))
                                            Color(0xFFE50914)
                                        else
                                            Color(0xFF333333),
                                        shape = RoundedCornerShape(3.dp)
                                    )
                                    .clickable(enabled = !isUpdating) {
                                        useAltLcoCode = if (index == 0) "0" else "1"
                                    }
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = option,
                                    color = Color.White,
                                    fontSize = 12.sp
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
                        modifier = Modifier.size(18.dp),
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
    isMultiline: Boolean = false,
    enabled: Boolean = true
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
    ) {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 11.sp,
            modifier = Modifier.padding(bottom = 3.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isMultiline) 65.dp else 42.dp)
                .background(
                    color = if (isFocused) Color(0xFF333333) else Color(0xFF2A2A2A),
                    shape = RoundedCornerShape(3.dp)
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
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                textStyle = LocalTextStyle.current.copy(
                    color = Color.White,
                    fontSize = 13.sp
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