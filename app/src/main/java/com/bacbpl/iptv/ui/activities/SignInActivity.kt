package com.bacbpl.iptv.ui.activities

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
import coil.compose.AsyncImage
import com.bacbpl.iptv.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay


@Composable
fun SignInActivity(
    onNavigateToOTP: (String) -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onSkip: () -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current

    // For auto-scrolling logos
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var isScrolling by remember { mutableStateOf(true) }

    // Auto-scroll effect
    LaunchedEffect(key1 = isScrolling) {
        if (isScrolling) {
            while (true) {
                // Calculate max scroll value
                val maxScroll = scrollState.maxValue

                // Smoothly scroll to the end
                scrollState.animateScrollTo(
                    value = maxScroll,
                    animationSpec = tween(
                        durationMillis = 100000, // 10 seconds to scroll full width
                        easing = LinearEasing
                    )
                )

                // Small delay at the end
                delay(5000)

                // Smoothly scroll back to start
                scrollState.animateScrollTo(
                    value = 0,
                    animationSpec = tween(
                        durationMillis = 100000,
                        easing = LinearEasing
                    )
                )

                // Small delay at the start
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
            // 🔥 AUTO-SCROLLING TOP LOGOS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
                    .padding(top = 20.dp, bottom = 30.dp),
                horizontalArrangement = Arrangement.spacedBy(80.dp)
            ) {
                // Repeat more times for smoother infinite scroll effect
                repeat(30) { // Increased from 10 to 30 for continuous feel
                    Image(
                        painter = painterResource(id = R.drawable.logos),
                        contentDescription = "Logo",
                        modifier = Modifier.height(80.dp).clip(RoundedCornerShape(12.dp)) // This adds rounded corners
                    )
                }
            }

            // Rest of your UI remains the same...
            // Sign-in Cards Row
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(380.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // QR Code Card (your existing code)
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = R.color.card_background).copy(alpha = 0.9f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    // ... your existing QR card content ...
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // QR Code Icon/Image
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

                // Sign In Card (your existing code)
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
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Title
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
                                        onNavigateToOTP(phoneNumber)
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

                        // Remember Me and Forgot Password
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

                        // Sign Up
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