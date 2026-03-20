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
                onNavigateToOTP = { phoneNumber ->
                    navController.navigate("otp/$phoneNumber")
                },
                onNavigateToSignUp = {
                    navController.navigate("signup")
                },
                onNavigateToForgotPassword = {
                    // Handle forgot password
                },
                onSkip = {
                    navController.navigate("main") {
                        popUpTo("signin") { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "otp/{phoneNumber}",
            arguments = listOf(navArgument("phoneNumber") { type = NavType.StringType })
        ) { backStackEntry ->
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""
            OTPActivity(
                phoneNumber = phoneNumber,
                onBack = { navController.popBackStack() },
                onSubmit = { otp ->
                    // After successful OTP verification, navigate to main
                    navController.navigate("main") {
                        popUpTo("signin") { inclusive = true }
                    }
                },
                onResend = { }
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