package com.bacbpl.iptv.utils

import android.content.Context
import android.content.SharedPreferences
import com.bacbpl.iptv.data.SharedPrefManager
import com.bacbpl.iptv.data.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object UserSession {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName.asStateFlow()

    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail.asStateFlow()

    private val _userMobile = MutableStateFlow<String?>(null)
    val userMobile: StateFlow<String?> = _userMobile.asStateFlow()

    private val _userId = MutableStateFlow(-1)
    val userId: StateFlow<Int> = _userId.asStateFlow()

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token.asStateFlow()

    // Add device information
    private val _deviceId = MutableStateFlow<String?>(null)
    val deviceId: StateFlow<String?> = _deviceId.asStateFlow()

    private val _macId = MutableStateFlow<String?>(null)
    val macId: StateFlow<String?> = _macId.asStateFlow()

    private val _deviceName = MutableStateFlow<String?>(null)
    val deviceName: StateFlow<String?> = _deviceName.asStateFlow()

    // Keys for SharedPreferences
    private const val KEY_FIRST_NAME = "first_name"
    private const val KEY_LAST_NAME = "last_name"
    private const val KEY_EMAIL = "email"
    private const val KEY_MOBILE = "mobile"
    private const val KEY_ADDRESS = "address"
    private const val KEY_ZONE = "zone"
    private const val KEY_SERVICE_NUMBER = "service_number"
    private const val KEY_STATE_CODE = "state_code"
    private const val KEY_USE_ALT_LCO_CODE = "use_alt_lco_code"
    private const val KEY_PARTNER_REFERENCE_ID = "partner_reference_id"

    // Add keys for device information
    private const val KEY_DEVICE_ID = "device_id"
    private const val KEY_MAC_ID = "mac_id"
    private const val KEY_DEVICE_NAME = "device_name"

    private const val PREFS_NAME = "user_session_prefs"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun updateSession(context: Context) {
        try {
            println("=== Updating User Session ===")
            val sharedPrefManager = SharedPrefManager(context)
            val prefs = getSharedPreferences(context)

            _isLoggedIn.value = sharedPrefManager.isLoggedIn()
            println("Is Logged In: ${_isLoggedIn.value}")

            if (_isLoggedIn.value) {
                _userName.value = sharedPrefManager.getUserName()
                _userEmail.value = sharedPrefManager.getUserEmail()
                _userMobile.value = sharedPrefManager.getUserMobile()
                _userId.value = sharedPrefManager.getUserId()
                _token.value = sharedPrefManager.getToken()

                // Set token in RetrofitClient
                _token.value?.let { token ->
                    RetrofitClient.setAuthToken(token)
                    println("Token set in RetrofitClient: ${token.take(20)}...")
                }

                // Load device information
                _deviceId.value = prefs.getString(KEY_DEVICE_ID, null)
                _macId.value = prefs.getString(KEY_MAC_ID, null)
                _deviceName.value = prefs.getString(KEY_DEVICE_NAME, null)

                println("Session updated for user: ${_userName.value}")
            } else {
                clearSessionData()
                RetrofitClient.setAuthToken(null)
                println("No active session")
            }
        } catch (e: Exception) {
            println("Error updating session: ${e.message}")
            e.printStackTrace()
            clearSessionData()
        }
    }

    private fun clearSessionData() {
        _isLoggedIn.value = false
        _userName.value = null
        _userEmail.value = null
        _userMobile.value = null
        _userId.value = -1
        _token.value = null
        _deviceId.value = null
        _macId.value = null
        _deviceName.value = null
    }

    fun clearSession(context: Context) {
        try {
            println("=== Clearing User Session ===")
            SharedPrefManager(context).clearUserSession()
            getSharedPreferences(context).edit().clear().apply()
            RetrofitClient.setAuthToken(null)
            println("Session cleared successfully")
        } catch (e: Exception) {
            println("Error clearing session: ${e.message}")
            e.printStackTrace()
        } finally {
            clearSessionData()
        }
    }

    // Save device information to session
    fun saveDeviceInfo(context: Context, deviceId: String, macId: String, deviceName: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit()
            .putString(KEY_DEVICE_ID, deviceId)
            .putString(KEY_MAC_ID, macId)
            .putString(KEY_DEVICE_NAME, deviceName)
            .apply()

        _deviceId.value = deviceId
        _macId.value = macId
        _deviceName.value = deviceName

        println("=== Device Info Saved ===")
        println("Device ID: $deviceId")
        println("MAC ID: $macId")
        println("Device Name: $deviceName")
    }

    // Get device information
    fun getDeviceId(context: Context): String? = getSharedPreferences(context).getString(KEY_DEVICE_ID, null)
    fun getMacId(context: Context): String? = getSharedPreferences(context).getString(KEY_MAC_ID, null)
    fun getDeviceName(context: Context): String? = getSharedPreferences(context).getString(KEY_DEVICE_NAME, null)

    fun saveSubscriberDetails(
        context: Context,
        firstName: String,
        lastName: String,
        email: String,
        mobile: String,
        address: String,
        zone: String,
        serviceNumber: String,
        stateCode: String,
        useAltLcoCode: String,
        partnerReferenceId: String = ""
    ) {
        val prefs = getSharedPreferences(context)
        prefs.edit()
            .putString(KEY_FIRST_NAME, firstName)
            .putString(KEY_LAST_NAME, lastName)
            .putString(KEY_EMAIL, email)
            .putString(KEY_MOBILE, mobile)
            .putString(KEY_ADDRESS, address)
            .putString(KEY_ZONE, zone)
            .putString(KEY_SERVICE_NUMBER, serviceNumber)
            .putString(KEY_STATE_CODE, stateCode)
            .putString(KEY_USE_ALT_LCO_CODE, useAltLcoCode)
            .putString(KEY_PARTNER_REFERENCE_ID, partnerReferenceId)
            .apply()

        _userName.value = "$firstName $lastName".trim()
        _userEmail.value = email
        _userMobile.value = mobile
    }

    fun getFirstName(context: Context): String? = getSharedPreferences(context).getString(KEY_FIRST_NAME, null)
    fun getLastName(context: Context): String? = getSharedPreferences(context).getString(KEY_LAST_NAME, null)
    fun getEmail(context: Context): String? = getSharedPreferences(context).getString(KEY_EMAIL, null)
    fun getMobile(context: Context): String? = getSharedPreferences(context).getString(KEY_MOBILE, null)
    fun getAddress(context: Context): String? = getSharedPreferences(context).getString(KEY_ADDRESS, null)
    fun getZone(context: Context): String? = getSharedPreferences(context).getString(KEY_ZONE, null)
    fun getServiceNumber(context: Context): String? = getSharedPreferences(context).getString(KEY_SERVICE_NUMBER, null)
    fun getStateCode(context: Context): String? = getSharedPreferences(context).getString(KEY_STATE_CODE, null)
    fun getUseAltLcoCode(context: Context): String? = getSharedPreferences(context).getString(KEY_USE_ALT_LCO_CODE, null)
    fun getPartnerReferenceId(context: Context): String? = getSharedPreferences(context).getString(KEY_PARTNER_REFERENCE_ID, null)
}