package com.bacbpl.iptv.jetStram.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteAccountOtpDialog(
    mobile: String,
    generatedOtp: Int?,
    isSendingOtp: Boolean,
    isDeleting: Boolean,
    onDismiss: () -> Unit,
    onSendOtp: () -> Unit,
    onVerifyAndDelete: (String) -> Unit,
    errorMessage: String? = null
) {
    var otpValue by remember { mutableStateOf("") }
    var isOtpFocused by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableStateOf(60) }
    var canResend by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Start timer when OTP is sent
    LaunchedEffect(generatedOtp) {
        if (generatedOtp != null) {
            timeLeft = 60
            canResend = false
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
            }
            canResend = true
        }
    }

    AlertDialog(
        onDismissRequest = {
            if (!isDeleting) onDismiss()
        },
        title = {
            Text(
                text = "Delete Account",
                color = Color.White,
                fontSize = 20.sp
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "We've sent a verification code to $mobile",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // OTP Input Field
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            color = if (isOtpFocused) Color(0xFF333333) else Color(0xFF2A2A2A),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = if (isOtpFocused) Color(0xFFE50914) else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    BasicTextField(
                        value = otpValue,
                        onValueChange = {
                            if (it.length <= 6 && it.all { char -> char.isDigit() }) {
                                otpValue = it
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        textStyle = LocalTextStyle.current.copy(
                            color = Color.White,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        enabled = !isDeleting
                    )
                }

                // Show generated OTP for testing (remove in production)
                if (generatedOtp != null) {
                    Text(
                        text = "Test OTP: $generatedOtp",
                        color = Color(0xFFE50914),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // Resend button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (!canResend) {
                        Text(
                            text = "Resend code in ${timeLeft}s",
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 12.sp
                        )
                    } else {
                        TextButton(
                            onClick = {
                                onSendOtp()
                                otpValue = ""
                            },
                            enabled = !isSendingOtp && !isDeleting
                        ) {
                            if (isSendingOtp) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    color = Color(0xFFE50914)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text(
                                text = "Resend OTP",
                                color = Color(0xFFE50914),
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                // Error message
                errorMessage?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }

                // Warning message
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF330000)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "⚠️ Warning: This action cannot be undone. All your data will be permanently deleted.",
                        color = Color(0xFFFF6666),
                        fontSize = 11.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (otpValue.length == 4) {
                        onVerifyAndDelete(otpValue)
                    }
                },
                enabled = otpValue.length == 4 && !isDeleting,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE50914)
                )
            ) {
                if (isDeleting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Deleting...", color = Color.White)
                } else {
                    Text("Delete Account", color = Color.White)
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isDeleting
            ) {
                Text("Cancel", color = Color.White)
            }
        },
        containerColor = Color(0xFF1A1A1A),
        titleContentColor = Color.White,
        textContentColor = Color.White
    )
}