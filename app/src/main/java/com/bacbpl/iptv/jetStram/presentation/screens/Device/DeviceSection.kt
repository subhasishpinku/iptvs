package com.bacbpl.iptv.jetStram.presentation.screens.Device
import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import java.net.NetworkInterface
import java.util.Locale

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun DeviceSection(
    isSubtitlesChecked: Boolean,
    onSubtitleCheckChange: (isChecked: Boolean) -> Unit
) {
    val context = LocalContext.current
    var deviceInfo by remember { mutableStateOf<List<DeviceInfoItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        deviceInfo = getDeviceInfo(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Device Information",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(deviceInfo) { item ->
                // Option 1: Use CardDefaults.colors() instead of cardColors()
                Card(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = item.value,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

data class DeviceInfoItem(
    val label: String,
    val value: String
)

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

private fun getDeviceModel(): String {
    return Build.MODEL
}

private fun getManufacturer(): String {
    return Build.MANUFACTURER
}

private fun getAndroidVersion(): String {
    return "${Build.VERSION.RELEASE} (${Build.VERSION.CODENAME})"
}

private fun getApiLevel(): Int {
    return Build.VERSION.SDK_INT
}

private fun getDeviceName(): String {
    return Build.DEVICE
}