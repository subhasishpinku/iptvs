plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.compose.compiler)  // Make sure this is here


//    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.ksp)
    id(libs.plugins.kotlin.serialization.get().pluginId)
//    alias(libs.plugins.android.test)
    alias(libs.plugins.androidx.baselineprofile)
    alias(libs.plugins.google.gms.google.services)
    id("org.jetbrains.kotlin.plugin.parcelize")

}

android {
    namespace = "com.bacbpl.iptv"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.bacbpl.iptv"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "PARTNER_API_URL", "\"https://partner.app.com\"")
        multiDexEnabled = true

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }
    }

    buildFeatures {
        viewBinding = false
        dataBinding = false
        buildConfig = true


    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    splits {
        abi {
            isEnable = true
            reset()
            include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
            isUniversalApk = true
        }
    }


}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.leanback)
    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.media3.datasource.rtmp)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.play.services.base)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.appcheck)
    implementation(libs.firebase.appcheck.playintegrity)
    implementation(libs.firebase.appcheck.debug)
    implementation(libs.play.services.base)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.auth.ktx.bom)

    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.androidx.compose.runtime.livedata)
    kapt(libs.hilt.compiler)

    implementation(libs.glide)
    implementation(libs.tmdb.java)

    implementation(libs.themoviedbapi)
    implementation(libs.exoplayer)
    implementation(libs.exoplayer.hls)
    implementation(libs.exoplayer.dash)
    implementation(libs.exoplayer.ui)
    implementation(libs.youtubeplayer)


    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.navigation)

    // Networking - Ktor
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.auth)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization)

    // Data Storage - Jetpack DataStore and Encrypted Preferences
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.security.crypto)

    // corouting

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation("com.airbnb.android:lottie:6.0.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.benchmark.macro.junit4)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.tv.material)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx )
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.viewmodel.compose)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.rules)

    implementation(libs.coil.compose)

    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.media3.exoplayer.hls)

//    implementation("com.github.holgerbrandl:themoviedbapi:1.15")
//    implementation("com.google.android.exoplayer:exoplayer:2.19.1")
//    implementation("com.github.HaarigerHarald:android-youtubeExtractor:master-SNAPSHOT")
//    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
//    implementation("androidx.media3:media3-datasource-rtmp:1.4.0")  // RTMP support

    implementation(libs.themoviedbapi)
    implementation(libs.exoplayer)
    implementation(libs.jackson.annotations)
    implementation(libs.jackson.databind)
    implementation(libs.jackson.core)
    implementation(libs.exoplayer.rtmp)
    // For baseline profile generation
//    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.1")
//    // Retrofit
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
//
//    // ViewModel
//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
////    implementation(libs.tmdb)
//    implementation("org.slf4j:slf4j-simple:1.7.36")
//    implementation("com.uwetrottmann.tmdb2:tmdb-java:2.11.0")
//
//    // Coroutines
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
//    kapt("com.github.bumptech.glide:compiler:4.16.0")
//    implementation("org.jetbrains.kotlin:kotlin-parcelize-runtime:1.9.0")
    implementation(libs.androidx.compose.constraintlayout)
    implementation(libs.androidx.viewmodel.compose)
//
    // Retrofit & Networking
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)

    // TMDB
//    implementation(libs.tmdb.java)
    implementation(libs.slf4j.simple)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Glide
    kapt(libs.glide.compiler)

    // Kotlin
    implementation(libs.kotlin.parcelize.runtime)
    implementation("androidx.compose.ui:ui-viewbinding:1.6.0")
    implementation("androidx.multidex:multidex:2.0.1")
    baselineProfile(project(":benchmark"))
}

kapt {
    correctErrorTypes = true
}