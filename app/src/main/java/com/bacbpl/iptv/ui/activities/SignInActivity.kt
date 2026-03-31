package com.bacbpl.iptv.ui.activities

import android.Manifest
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.bacbpl.iptv.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.bacbpl.iptv.utils.UserSession
import kotlinx.coroutines.delay
import java.net.NetworkInterface
import java.util.UUID

@Composable
fun SignInActivity(
    onNavigateToOTP: (String, String, String, String) -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onSkip: () -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    var deviceId by remember { mutableStateOf("") }
    var macId by remember { mutableStateOf("") }
    var deviceName by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current

    // Permission launcher for Android 10+ to get MAC address
// Permission launcher for Android 10+ to get MAC address
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            macId = getMacAddress(context)
            // Save to session immediately after getting
            UserSession.saveDeviceInfo(context, deviceId, macId, deviceName)
        } else {
            macId = getAlternativeMacId(context)
            UserSession.saveDeviceInfo(context, deviceId, macId, deviceName)
        }
    }

    // Get device information
    LaunchedEffect(Unit) {
        deviceId = getDeviceId(context)
        deviceName = getDeviceName()

        // Handle MAC address based on Android version
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                // Android 10+ - Need ACCESS_FINE_LOCATION permission
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) -> {
                        macId = getMacAddress(context)
                        // Save device info to session
                        UserSession.saveDeviceInfo(context, deviceId, macId, deviceName)
                    }
                    else -> {
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                }
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                // Android 6-9 - Need ACCESS_WIFI_STATE permission
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_WIFI_STATE
                    ) -> {
                        macId = getMacAddress(context)
                        UserSession.saveDeviceInfo(context, deviceId, macId, deviceName)
                    }
                    else -> {
                        // Still try to get MAC without permission (may return null)
                        macId = getMacAddress(context)
                        if (macId == "02:00:00:00:00:00") {
                            macId = getAlternativeMacId(context)
                        }
                        UserSession.saveDeviceInfo(context, deviceId, macId, deviceName)
                    }
                }
            }
            else -> {
                // Below Android 6.0 - Can get MAC address directly
                macId = getMacAddress(context)
                UserSession.saveDeviceInfo(context, deviceId, macId, deviceName)
            }
        }

        // If MAC is still default or empty, generate a stable alternative
        if (macId.isEmpty() || macId == "02:00:00:00:00:00") {
            macId = getAlternativeMacId(context)
            UserSession.saveDeviceInfo(context, deviceId, macId, deviceName)
        }

        // Save device info one more time to ensure it's saved
        UserSession.saveDeviceInfo(context, deviceId, macId, deviceName)
    }

    // Auto-scrolling logos code remains the same...
    val scrollState = rememberScrollState()
    var isScrolling by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = isScrolling) {
        if (isScrolling) {
            while (true) {
                val maxScroll = scrollState.maxValue
                scrollState.animateScrollTo(
                    value = maxScroll,
                    animationSpec = tween(
                        durationMillis = 100000,
                        easing = LinearEasing
                    )
                )
                delay(5000)
                scrollState.animateScrollTo(
                    value = 0,
                    animationSpec = tween(
                        durationMillis = 100000,
                        easing = LinearEasing
                    )
                )
                delay(500)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        AsyncImage(
            model = R.drawable.bg_movies,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // Dark Overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.7f))
        )

        // Main Content Column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // AUTO-SCROLLING TOP LOGOS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
                    .padding(top = 20.dp, bottom = 30.dp),
                horizontalArrangement = Arrangement.spacedBy(80.dp)
            ) {
                repeat(30) {
                    Image(
                        painter = painterResource(id = R.drawable.logos),
                        contentDescription = "Logo",
                        modifier = Modifier.height(80.dp).clip(RoundedCornerShape(12.dp))
                    )
                }
            }

            // Sign-in Cards Row
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(480.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // QR Code Card (unchanged)
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = R.color.card_background).copy(alpha = 0.9f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(130.dp)
                                .background(Color.White, RoundedCornerShape(12.dp))
                                .padding(5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.qr_code),
                                contentDescription = "QR Code",
                                modifier = Modifier.size(80.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Sign in with QR Code",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Scan this QR code with your phone",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 11.sp
                        )

                        Text(
                            text = "to sign in instantly",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 11.sp,
                            modifier = Modifier.padding(bottom = 14.dp)
                        )

                        OutlinedButton(
                            onClick = { /* Refresh QR Code */ },
                            modifier = Modifier
                                .width(200.dp)
                                .height(38.dp)
                                .align(Alignment.CenterHorizontally),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.White
                            )
                        ) {
                            Text("Refresh QR Code", fontSize = 11.sp)
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "or use your TV app",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 9.sp
                        )
                    }
                }

                // Sign In Card with Device Info
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = R.color.card_background).copy(alpha = 0.9f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Sign In",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Phone Input
                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = {
                                if (it.all { char -> char.isDigit() } && it.length <= 10) {
                                    phoneNumber = it
                                    phoneError = null
                                }
                            },
                            label = { Text("Enter Phone number", fontSize = 12.sp) },
                            placeholder = { Text("Enter Phone number", fontSize = 12.sp) },
                            isError = phoneError != null,
                            supportingText = phoneError?.let {
                                { Text(text = it, fontSize = 9.sp) }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.Gray,
                                cursorColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.Gray,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.White,
                                fontSize = 14.sp
                            ),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Device ID (Read-only)
                        OutlinedTextField(
                            value = deviceId,
                            onValueChange = {},
                            label = { Text("Device ID", fontSize = 12.sp) },
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.Gray,
                                cursorColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.Gray,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                disabledTextColor = Color.White.copy(alpha = 0.7f),
                                disabledBorderColor = Color.Gray,
                                disabledLabelColor = Color.Gray
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // MAC ID (Read-only)
                        OutlinedTextField(
                            value = macId,
                            onValueChange = {},
                            label = { Text("MAC ID", fontSize = 12.sp) },
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.Gray,
                                cursorColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.Gray,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                disabledTextColor = Color.White.copy(alpha = 0.7f),
                                disabledBorderColor = Color.Gray,
                                disabledLabelColor = Color.Gray
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Device Name (Read-only)
                        OutlinedTextField(
                            value = deviceName,
                            onValueChange = {},
                            label = { Text("Device Name", fontSize = 12.sp) },
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.Gray,
                                cursorColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.Gray,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                disabledTextColor = Color.White.copy(alpha = 0.7f),
                                disabledBorderColor = Color.Gray,
                                disabledLabelColor = Color.Gray
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                when {
                                    phoneNumber.isEmpty() -> {
                                        phoneError = "Phone number cannot be empty"
                                    }
                                    phoneNumber.length != 10 -> {
                                        phoneError = "Enter valid 10-digit number"
                                    }
                                    else -> {
                                        onNavigateToOTP(phoneNumber, deviceId, macId, deviceName)
                                    }
                                }
                            },
                            modifier = Modifier
                                .width(200.dp)
                                .height(42.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE50914)
                            )
                        ) {
                            Text(
                                text = "Sign In",
                                fontSize = 15.sp,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = rememberMe,
                                    onCheckedChange = { rememberMe = it },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color.White,
                                        uncheckedColor = Color.Gray
                                    ),
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "Remember me",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(start = 10.dp)
                                )
                            }

                            TextButton(
                                onClick = onNavigateToForgotPassword,
                                modifier = Modifier.height(35.dp)
                            ) {
                                Text(
                                    text = "Forgot Password",
                                    color = Color.White,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        TextButton(
                            onClick = onNavigateToSignUp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                text = "New here? Sign up now.",
                                color = Color.White,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

// Helper functions
fun getDeviceId(context: android.content.Context): String {
    return try {
        android.provider.Settings.Secure.getString(
            context.contentResolver,
            android.provider.Settings.Secure.ANDROID_ID
        ) ?: UUID.randomUUID().toString()
    } catch (e: Exception) {
        UUID.randomUUID().toString()
    }
}

fun getDeviceName(): String {
    return try {
        Build.MANUFACTURER + " " + Build.MODEL
    } catch (e: Exception) {
        "Unknown Device"
    }
}

fun getMacAddress(context: android.content.Context): String {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // For Android 6.0 and above, use NetworkInterface
            val interfaces = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val networkInterface = interfaces.nextElement()
                val hardwareAddress = networkInterface.hardwareAddress
                if (hardwareAddress != null && hardwareAddress.isNotEmpty()) {
                    val mac = StringBuilder()
                    for (i in hardwareAddress.indices) {
                        mac.append(String.format("%02X", hardwareAddress[i]))
                        if (i < hardwareAddress.size - 1) {
                            mac.append(":")
                        }
                    }
                    // Skip loopback and virtual interfaces
                    val macString = mac.toString()
                    if (macString != "00:00:00:00:00:00" &&
                        !networkInterface.name.contains("docker") &&
                        !networkInterface.name.contains("veth")) {
                        return macString
                    }
                }
            }
        }

        // Fallback to WifiManager (may return null on newer Android versions)
        val wifiManager = context.applicationContext.getSystemService(android.content.Context.WIFI_SERVICE) as? android.net.wifi.WifiManager
        wifiManager?.connectionInfo?.macAddress ?: "02:00:00:00:00:00"
    } catch (e: Exception) {
        "02:00:00:00:00:00"
    }
}

fun getAlternativeMacId(context: android.content.Context): String {
    // Generate a stable, unique ID based on Android ID and device fingerprint
    val androidId = getDeviceId(context)
    val deviceFingerprint = Build.FINGERPRINT
    val combined = "$androidId-$deviceFingerprint"

    return try {
        val hash = UUID.nameUUIDFromBytes(combined.toByteArray()).toString()
        // Format as MAC address
        val cleanHash = hash.replace("-", "").take(12)
        cleanHash.chunked(2).joinToString(":").uppercase()
    } catch (e: Exception) {
        "02:10:00:00:00:00"
    }
}