package com.bacbpl.iptv.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import java.net.NetworkInterface
import java.util.*

object DeviceInfoHelper {

    /**
     * Get unique device ID (Android ID or generated UUID)
     */
    fun getDeviceId(context: Context): String {
        return try {
            val androidId = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )

            if (!androidId.isNullOrEmpty() && androidId != "9774d56d682e549c") {
                androidId
            } else {
                // Generate a unique ID if Android ID is not available
                getOrCreateUniqueId(context)
            }
        } catch (e: Exception) {
            getOrCreateUniqueId(context)
        }
    }

    /**
     * Generate and store a persistent unique ID
     */
    private fun getOrCreateUniqueId(context: Context): String {
        val sharedPref = context.getSharedPreferences("device_prefs", Context.MODE_PRIVATE)
        var uniqueId = sharedPref.getString("unique_device_id", null)

        if (uniqueId == null) {
            // Combine multiple device identifiers
            val combined = "${Build.MANUFACTURER}-${Build.MODEL}-${Build.SERIAL}-${System.currentTimeMillis()}"
            uniqueId = UUID.nameUUIDFromBytes(combined.toByteArray()).toString()
            sharedPref.edit().putString("unique_device_id", uniqueId).apply()
        }

        return uniqueId
    }

    /**
     * Get MAC address - works on all Android versions
     */
    fun getMacAddress(context: Context): String {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Android 10+ - Use persistent unique ID as fallback
                getMacAddressAndroid10Plus(context)
            } else {
                // Android 9 and below - Can get real MAC
                getMacAddressLegacy()
            }
        } catch (e: Exception) {
            // Return fallback MAC for Android 10+
            "02:00:00:00:00:00"
        }
    }

    /**
     * Get MAC address on Android 10+ using alternative methods
     */
    private fun getMacAddressAndroid10Plus(context: Context): String {
        // Try to get from WiFiManager (requires location permission)
        if (hasLocationPermission(context)) {
            try {
                val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as? android.net.wifi.WifiManager
                val wifiInfo = wifiManager?.connectionInfo
                val mac = wifiInfo?.macAddress

                if (!mac.isNullOrEmpty() && mac != "02:00:00:00:00:00" && mac != "02:00:00:00:00:00") {
                    return mac.uppercase(Locale.getDefault())
                }
            } catch (e: Exception) {
                // Continue to next method
            }
        }

        // Try to get from NetworkInterface
        try {
            val networkInterfaces = NetworkInterface.getNetworkInterfaces()
            while (networkInterfaces.hasMoreElements()) {
                val networkInterface = networkInterfaces.nextElement()
                val mac = networkInterface.hardwareAddress

                if (mac != null && mac.isNotEmpty()) {
                    val macAddress = mac.joinToString(":") { "%02x".format(it) }
                        .uppercase(Locale.getDefault())

                    // Filter out valid MAC addresses
                    if (macAddress != "02:00:00:00:00:00" &&
                        macAddress != "00:00:00:00:00:00" &&
                        !macAddress.startsWith("00:00:00:00:00")) {
                        return macAddress
                    }
                }
            }
        } catch (e: Exception) {
            // Continue to fallback
        }

        // Return persistent unique ID as MAC fallback
        return getPersistentMacFallback(context)
    }

    /**
     * Get MAC address on Android 9 and below
     */
    private fun getMacAddressLegacy(): String {
        val networkInterfaces = NetworkInterface.getNetworkInterfaces()

        while (networkInterfaces.hasMoreElements()) {
            val networkInterface = networkInterfaces.nextElement()
            val mac = networkInterface.hardwareAddress

            if (mac != null) {
                // Check for wlan0 or eth0 interfaces
                if (networkInterface.name == "wlan0" || networkInterface.name == "eth0") {
                    return mac.joinToString(":") { "%02x".format(it) }
                        .uppercase(Locale.getDefault())
                }
            }
        }

        // Try alternative interfaces
        val interfaces = NetworkInterface.getNetworkInterfaces()
        while (interfaces.hasMoreElements()) {
            val networkInterface = interfaces.nextElement()
            val mac = networkInterface.hardwareAddress

            if (mac != null && mac.isNotEmpty()) {
                val macAddress = mac.joinToString(":") { "%02x".format(it) }
                    .uppercase(Locale.getDefault())

                // Skip loopback
                if (macAddress != "00:00:00:00:00:00") {
                    return macAddress
                }
            }
        }

        return "02:00:00:00:00:00"
    }

    /**
     * Generate persistent MAC-like ID for Android 10+
     */
    private fun getPersistentMacFallback(context: Context): String {
        val sharedPref = context.getSharedPreferences("device_prefs", Context.MODE_PRIVATE)
        var persistentId = sharedPref.getString("persistent_mac_id", null)

        if (persistentId == null) {
            // Create a unique ID that looks like a MAC address
            val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            val hash = (androidId + Build.MANUFACTURER + Build.MODEL).hashCode()
            val hexString = String.format("%012X", hash)

            // Format as MAC address: XX:XX:XX:XX:XX:XX
            persistentId = hexString.chunked(2).joinToString(":")
            sharedPref.edit().putString("persistent_mac_id", persistentId).apply()
        }

        return persistentId
    }

    /**
     * Get device name (manufacturer + model)
     */
    fun getDeviceName(): String {
        return try {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL

            if (model.startsWith(manufacturer, ignoreCase = true)) {
                model.capitalize(Locale.getDefault())
            } else {
                "${manufacturer.capitalize(Locale.getDefault())} $model"
            }
        } catch (e: Exception) {
            "Android Device"
        }
    }

    /**
     * Check if location permission is granted (required for MAC on Android 10+)
     */
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

    /**
     * Get all device info as a map
     */
    fun getAllDeviceInfo(context: Context): Map<String, String> {
        return mapOf(
            "device_id" to getDeviceId(context),
            "mac_address" to getMacAddress(context),
            "device_name" to getDeviceName(),
            "manufacturer" to Build.MANUFACTURER,
            "model" to Build.MODEL,
            "android_version" to "${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})",
            "build_id" to Build.ID
        )
    }
}