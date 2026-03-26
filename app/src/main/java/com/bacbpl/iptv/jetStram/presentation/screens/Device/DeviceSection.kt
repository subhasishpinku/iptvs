package com.bacbpl.iptv.jetStram.presentation.screens.Device

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.os.Build
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
import java.util.Locale

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
        // Header Section with Icon
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 32.dp)
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
            // Grid with enhanced animations
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
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
        targetValue = if (isFocused) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "card_scale_animation"
    )

    // Get base colors for the card
    val baseContainerColor = when {
        item.label.contains("ID", ignoreCase = true) -> MaterialTheme.colorScheme.secondaryContainer
        item.label.contains("MAC", ignoreCase = true) -> MaterialTheme.colorScheme.tertiaryContainer
        item.label.contains("Version", ignoreCase = true) -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    val baseContentColor = when {
        item.label.contains("ID", ignoreCase = true) -> MaterialTheme.colorScheme.onSecondaryContainer
        item.label.contains("MAC", ignoreCase = true) -> MaterialTheme.colorScheme.onTertiaryContainer
        item.label.contains("Version", ignoreCase = true) -> MaterialTheme.colorScheme.onPrimaryContainer
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    // Focused colors - brighter and more vibrant
    val containerColor = if (isFocused) {
        when {
            item.label.contains("ID", ignoreCase = true) -> MaterialTheme.colorScheme.secondary
            item.label.contains("MAC", ignoreCase = true) -> MaterialTheme.colorScheme.tertiary
            item.label.contains("Version", ignoreCase = true) -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.primaryContainer
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Label with icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = getIconForLabel(item.label),
                        contentDescription = null,
                        tint = if (isFocused) {
                            Color.White
                        } else {
                            baseContentColor
                        },
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Medium,
                        color = if (isFocused) {
                            Color.White
                        } else {
                            baseContentColor
                        }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Value with copy indicator
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = item.value,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (isFocused) {
                            Color.White
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        },
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (item.value != "Not Available") {
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
}

@Composable
fun getIconForLabel(label: String): ImageVector {
    return when {
        label.contains("ID") -> Icons.Default.Info
        label.contains("MAC") -> Icons.Default.Wifi
        label.contains("Model") -> Icons.Default.Devices
        label.contains("Manufacturer") -> Icons.Default.Business
        label.contains("Version") -> Icons.Default.Android
        label.contains("API") -> Icons.Default.Code
        label.contains("Name") -> Icons.Default.DriveFileRenameOutline
        else -> Icons.Default.DeviceHub
    }
}

data class DeviceInfoItem(
    val label: String,
    val value: String,
    val category: DeviceInfoCategory = DeviceInfoCategory.getCategory(label)
)

enum class DeviceInfoCategory(val icon: ImageVector, val color: Color) {
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
        DeviceInfoItem("MAC Address", getMacAddress()),
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

private fun getMacAddress(): String {
    return try {
        val networkInterfaces = NetworkInterface.getNetworkInterfaces()
        while (networkInterfaces.hasMoreElements()) {
            val networkInterface = networkInterfaces.nextElement()
            val mac = networkInterface.hardwareAddress
            if (mac != null && networkInterface.name == "wlan0") {
                return mac.joinToString(":") { "%02x".format(it) }
                    .uppercase(Locale.getDefault())
            }
        }
        "Not Available"
    } catch (e: Exception) {
        "Not Available"
    }
}

private fun getDeviceModel(): String = Build.MODEL
private fun getManufacturer(): String = Build.MANUFACTURER
private fun getAndroidVersion(): String = "${Build.VERSION.RELEASE} (${Build.VERSION.CODENAME})"
private fun getApiLevel(): Int = Build.VERSION.SDK_INT
private fun getDeviceName(): String = Build.DEVICE