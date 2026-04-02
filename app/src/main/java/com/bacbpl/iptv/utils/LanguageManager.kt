// com/bacbpl/iptv/utils/LanguageManager.kt
package com.bacbpl.iptv.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import java.util.*

object LanguageManager {
    const val PREF_LANGUAGE = "app_language"
    const val LANGUAGE_ENGLISH = "en"
    const val LANGUAGE_BENGALI = "bn"
    const val LANGUAGE_HINDI = "hi"

    private val supportedLanguages = mapOf(
        LANGUAGE_ENGLISH to "English",
        LANGUAGE_BENGALI to "বাংলা",
        LANGUAGE_HINDI to "हिंदी"
    )

    fun getLanguageNames(): Map<String, String> = supportedLanguages

    fun getCurrentLanguage(context: Context): String {
        val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPref.getString(PREF_LANGUAGE, LANGUAGE_ENGLISH) ?: LANGUAGE_ENGLISH
    }

    fun setLanguage(context: Context, languageCode: String) {
        if (!supportedLanguages.containsKey(languageCode)) return

        val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPref.edit().putString(PREF_LANGUAGE, languageCode).apply()

        // Apply language change
        setLocale(context, languageCode)
    }

    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            resources.updateConfiguration(configuration, resources.displayMetrics)
        } else {
            @Suppress("DEPRECATION")
            configuration.locale = locale
            @Suppress("DEPRECATION")
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
    }

    fun restartApp(context: Context) {
        (context as? android.app.Activity)?.recreate()
    }
}

@Composable
fun rememberLanguageState(): LanguageState {
    val context = LocalContext.current
    val currentLanguage = LanguageManager.getCurrentLanguage(context)
    var selectedLanguage by remember { mutableStateOf(currentLanguage) }

    // Define the function correctly
    val updateLanguage: (String) -> Unit = { languageCode ->
        selectedLanguage = languageCode
        LanguageManager.setLanguage(context, languageCode)
    }

    return LanguageState(
        currentLanguage = selectedLanguage,
        updateLanguage = updateLanguage
    )
}

data class LanguageState(
    val currentLanguage: String,
    val updateLanguage: (String) -> Unit  // This is a function
)