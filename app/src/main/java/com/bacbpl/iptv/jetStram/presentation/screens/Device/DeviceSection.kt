package com.bacbpl.iptv.jetStram.presentation.screens.Device

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import kotlinx.coroutines.delay
import java.net.NetworkInterface
import java.util.*

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun DeviceSection(
    isSubtitlesChecked: Boolean,
    onSubtitleCheckChange: (isChecked: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var deviceInfo by remember { mutableStateOf<List<DeviceInfoItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(500)
        deviceInfo = getDeviceInfo(context)
        isLoading = false
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .padding(24.dp)
    ) {
        // Header Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Devices,
                contentDescription = "Device Info",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Device Information",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.weight(1f))

            // Device Health Indicator
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (deviceInfo.isNotEmpty())
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (deviceInfo.isNotEmpty()) "All Systems Operational" else "Loading...",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        // Loading State
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Gathering device information...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            // Grid with responsive columns
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 280.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(deviceInfo, key = { it.label }) { item ->
                    AnimatedDeviceCard(item)
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun AnimatedDeviceCard(
    item: DeviceInfoItem,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.02f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "card_scale_animation"
    )

    // Get colors based on category
    val baseContainerColor = when (item.category) {
        DeviceInfoCategory.IDENTIFIER -> MaterialTheme.colorScheme.secondaryContainer
        DeviceInfoCategory.NETWORK -> MaterialTheme.colorScheme.tertiaryContainer
        DeviceInfoCategory.HARDWARE -> MaterialTheme.colorScheme.primaryContainer
        DeviceInfoCategory.SOFTWARE -> MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f)
        DeviceInfoCategory.SYSTEM -> MaterialTheme.colorScheme.surfaceVariant
    }

    val baseContentColor = when (item.category) {
        DeviceInfoCategory.IDENTIFIER -> MaterialTheme.colorScheme.onSecondaryContainer
        DeviceInfoCategory.NETWORK -> MaterialTheme.colorScheme.onTertiaryContainer
        DeviceInfoCategory.HARDWARE -> MaterialTheme.colorScheme.onPrimaryContainer
        DeviceInfoCategory.SOFTWARE -> MaterialTheme.colorScheme.onSecondaryContainer
        DeviceInfoCategory.SYSTEM -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    // Focused colors
    val containerColor = if (isFocused) {
        when (item.category) {
            DeviceInfoCategory.IDENTIFIER -> MaterialTheme.colorScheme.secondary
            DeviceInfoCategory.NETWORK -> MaterialTheme.colorScheme.tertiary
            DeviceInfoCategory.HARDWARE -> MaterialTheme.colorScheme.primary
            DeviceInfoCategory.SOFTWARE -> MaterialTheme.colorScheme.secondary
            DeviceInfoCategory.SYSTEM -> MaterialTheme.colorScheme.primaryContainer
        }
    } else {
        baseContainerColor
    }

    Card(
        onClick = { },
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        colors = CardDefaults.colors(
            containerColor = containerColor
        ),
        shape = CardDefaults.shape(shape = RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header with icon and label
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = item.category.icon,
                    contentDescription = null,
                    tint = if (isFocused) Color.White else baseContentColor,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = item.label,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium,
                    color = if (isFocused) Color.White else baseContentColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Value section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = item.value,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (isFocused) Color.White else MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                if (item.value != "Not Available" && !item.value.startsWith("Restricted") && !item.value.startsWith("UID:")) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy",
                        tint = if (isFocused) {
                            Color.White.copy(alpha = 0.8f)
                        } else {
                            baseContentColor.copy(alpha = 0.5f)
                        },
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

data class DeviceInfoItem(
    val label: String,
    val value: String,
    val category: DeviceInfoCategory = DeviceInfoCategory.getCategory(label)
)

enum class DeviceInfoCategory(
    val icon: ImageVector,
    val color: Color
) {
    IDENTIFIER(Icons.Default.Info, Color(0xFF2196F3)),
    NETWORK(Icons.Default.Wifi, Color(0xFF4CAF50)),
    HARDWARE(Icons.Default.Devices, Color(0xFFFF9800)),
    SOFTWARE(Icons.Default.Android, Color(0xFF9C27B0)),
    SYSTEM(Icons.Default.Settings, Color(0xFF607D8B));

    companion object {
        fun getCategory(label: String): DeviceInfoCategory {
            return when {
                label.contains("ID") || label.contains("Name") -> IDENTIFIER
                label.contains("MAC") -> NETWORK
                label.contains("Model") || label.contains("Manufacturer") -> HARDWARE
                label.contains("Version") || label.contains("API") -> SOFTWARE
                else -> SYSTEM
            }
        }
    }
}

private fun getDeviceInfo(context: Context): List<DeviceInfoItem> {
    return listOf(
        DeviceInfoItem("Device ID", getDeviceId(context)),
        DeviceInfoItem("MAC Address", getMacAddress(context)),
        DeviceInfoItem("Device Model", getDeviceModel()),
        DeviceInfoItem("Manufacturer", getManufacturer()),
        DeviceInfoItem("Android Version", getAndroidVersion()),
        DeviceInfoItem("API Level", getApiLevel().toString()),
        DeviceInfoItem("Device Name", getDeviceName())
    )
}

@SuppressLint("HardwareIds")
private fun getDeviceId(context: Context): String {
    return try {
        Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        ) ?: "Not Available"
    } catch (e: Exception) {
        "Not Available"
    }
}

private fun getMacAddress(context: Context): String {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10+ - Use alternative methods
            getMacAddressAndroid10Plus(context)
        } else {
            // Android 9 and below - Original method works
            getMacAddressLegacy()
        }
    } catch (e: Exception) {
        "Not Available"
    }
}

@SuppressLint("HardwareIds")
private fun getMacAddressAndroid10Plus(context: Context): String {
    return try {
        // Method 1: Try to get from WiFiManager (requires permission)
        if (hasLocationPermission(context)) {
            val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as? android.net.wifi.WifiManager
            val wifiInfo = wifiManager?.connectionInfo
            val mac = wifiInfo?.macAddress
            if (!mac.isNullOrEmpty() && mac != "02:00:00:00:00:00" && mac != "02:00:00:00:00:00") {
                return mac.uppercase(Locale.getDefault())
            }
        }

        // Method 2: Try using NetworkInterface (might still work for some devices)
        val networkInterfaces = NetworkInterface.getNetworkInterfaces()
        while (networkInterfaces.hasMoreElements()) {
            val networkInterface = networkInterfaces.nextElement()
            val mac = networkInterface.hardwareAddress
            if (mac != null && mac.isNotEmpty() &&
                (networkInterface.name == "wlan0" || networkInterface.name == "eth0")) {
                return mac.joinToString(":") { "%02x".format(it) }
                    .uppercase(Locale.getDefault())
            }
        }

        // Method 3: Fallback to device ID or generate a unique identifier
        getPersistentUniqueId(context)
    } catch (e: Exception) {
        getPersistentUniqueId(context)
    }
}

private fun getMacAddressLegacy(): String {
    val networkInterfaces = NetworkInterface.getNetworkInterfaces()
    while (networkInterfaces.hasMoreElements()) {
        val networkInterface = networkInterfaces.nextElement()
        val mac = networkInterface.hardwareAddress
        if (mac != null && networkInterface.name == "wlan0") {
            return mac.joinToString(":") { "%02x".format(it) }
                .uppercase(Locale.getDefault())
        }
    }
    return "Not Available"
}

// Generate a persistent unique ID for the device (alternative to MAC address)
@SuppressLint("HardwareIds")
private fun getPersistentUniqueId(context: Context): String {
    return try {
        val sharedPref = context.getSharedPreferences("device_prefs", Context.MODE_PRIVATE)
        var uniqueId = sharedPref.getString("unique_device_id", null)

        if (uniqueId == null) {
            // Combine multiple identifiers to create a unique ID
            val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            val buildSerial = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    Build.getSerial()
                } catch (e: SecurityException) {
                    "serial_denied"
                }
            } else {
                Build.SERIAL
            }
            val combined = "$androidId-$buildSerial-${Build.MANUFACTURER}-${Build.MODEL}"

            // Create a hash of the combined string
            uniqueId = combined.hashCode().toString(16).uppercase(Locale.getDefault())

            // Store for persistence
            sharedPref.edit().putString("unique_device_id", uniqueId).apply()
        }

        "UID: $uniqueId"
    } catch (e: Exception) {
        "Not Available"
    }
}

private fun hasLocationPermission(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ||
                context.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}

private fun getDeviceModel(): String = Build.MODEL
private fun getManufacturer(): String = Build.MANUFACTURER
private fun getAndroidVersion(): String = "${Build.VERSION.RELEASE} (${Build.VERSION.CODENAME})"
private fun getApiLevel(): Int = Build.VERSION.SDK_INT
private fun getDeviceName(): String = Build.DEVICE