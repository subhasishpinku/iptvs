package com.bacbpl.iptv.ui.activities.navigation

import android.content.Intent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bacbpl.iptv.JetStreamActivity
import com.bacbpl.iptv.ui.activities.*
import com.bacbpl.iptv.ui.activities.otpscreen.OTPActivity
import com.bacbpl.iptv.ui.activities.signupscreen.SignupActivity
import com.bacbpl.iptv.utils.LoginUtils

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Check if user is already logged in
    val isLoggedIn = remember { LoginUtils.isUserLoggedIn(context) }
    var startDestination by remember { mutableStateOf("splash") }

    // If already logged in, navigate directly to main content
    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            startDestination = "main"
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("splash") {
            SplashScreen(
                onSplashFinished = {
                    if (isLoggedIn) {
                        navController.navigate("main") {
                            popUpTo("splash") { inclusive = true }
                        }
                    } else {
                        navController.navigate("signin") {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                }
            )
        }

        composable("signin") {
            SignInActivity(
                onNavigateToOTP = { phone, deviceId, macId, deviceName ->
                    navController.navigate("otp/$phone/$deviceId/$macId/$deviceName")
                },
                onNavigateToSignUp = {
                    navController.navigate("signup")
                },
                onNavigateToForgotPassword = {},
                onSkip = {
                    navController.navigate("main") {
                        popUpTo("signin") { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = "otp/{phone}/{deviceId}/{macId}/{deviceName}",
            arguments = listOf(
                navArgument("phone") { type = NavType.StringType },
                navArgument("deviceId") { type = NavType.StringType },
                navArgument("macId") { type = NavType.StringType },
                navArgument("deviceName") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val phone = backStackEntry.arguments?.getString("phone") ?: ""
            val deviceId = backStackEntry.arguments?.getString("deviceId") ?: ""
            val macId = backStackEntry.arguments?.getString("macId") ?: ""
            val deviceName = backStackEntry.arguments?.getString("deviceName") ?: ""

            OTPActivity(
                phoneNumber = phone,
                deviceId = deviceId,
                macId = macId,
                deviceName = deviceName,
                onBack = { navController.popBackStack() },
                onSubmit = {
                    navController.navigate("main") {
                        popUpTo("signin") { inclusive = true }
                    }
                },
                onResend = {}
            )
        }

        composable("signup") {
            SignupActivity(
                onNavigateToSignIn = { navController.popBackStack() },
                onSignupSuccess = {  //  callback
                    // After successful signup, navigate to main
                    navController.navigate("main") {
                        popUpTo("signup") { inclusive = true }
                    }
                }
            )
        }

        composable("main") {
            JetStreamActivityLauncher()
        }
    }
}