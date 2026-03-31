package com.bacbpl.iptv.ui.activities.otpscreen

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bacbpl.iptv.R
import com.bacbpl.iptv.data.api.RetrofitClient
import com.bacbpl.iptv.utils.ToastUtils
import com.bacbpl.iptv.ui.activities.otpscreen.viewmodel.OtpUiState
import com.bacbpl.iptv.ui.activities.otpscreen.viewmodel.OtpViewModel
import com.bacbpl.iptv.ui.activities.otpscreen.viewmodel.OtpViewModelFactory
import com.bacbpl.iptv.ui.activities.otpscreen.viewmodel.VerifyOtpUiState
import com.bacbpl.iptv.utils.UserSession

@Composable
fun OTPActivity(
    phoneNumber: String,
    deviceId: String,
    macId: String,
    deviceName: String,
    onBack: () -> Unit,
    onSubmit: (String) -> Unit,
    onResend: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: OtpViewModel = viewModel(
        factory = OtpViewModelFactory(context.applicationContext as Application)
    )
    val otpLength = 4
    val otpValues = remember { mutableStateListOf("", "", "", "") }
    val focusRequesters = List(otpLength) { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val sendOtpState by viewModel.sendOtpState.collectAsState()
    val verifyOtpState by viewModel.verifyOtpState.collectAsState()
    val timerState by viewModel.timerState.collectAsState()

    var isSubmitting by remember { mutableStateOf(false) }

    // Auto-send OTP when screen loads
    LaunchedEffect(Unit) {
        viewModel.sendOtp(phoneNumber, deviceId, macId, deviceName)
    }

    // Handle send OTP state
    LaunchedEffect(sendOtpState) {
        when (sendOtpState) {
            is OtpUiState.Success -> {
                val response = (sendOtpState as OtpUiState.Success).response
                ToastUtils.showSafeToast(context, "OTP: ${response.otp} (For testing)")
            }
            is OtpUiState.Error -> {
                ToastUtils.showSafeToast(context, (sendOtpState as OtpUiState.Error).message)
            }
            else -> {}
        }
    }

    // Handle verify OTP state
    LaunchedEffect(verifyOtpState) {
        when (verifyOtpState) {
            is VerifyOtpUiState.Success -> {
                isSubmitting = false
                val response = (verifyOtpState as VerifyOtpUiState.Success).response
                ToastUtils.showSafeToast(context, response!!.message)

                if (response.status) {
                    // Save token to RetrofitClient
                    response.token?.let { token ->
                        RetrofitClient.setAuthToken(token)
                        println("=== Token set in RetrofitClient after OTP verification ===")
                        println("Token: ${token.take(20)}...")
                    }

                    // Update UserSession
                    UserSession.updateSession(context)

                    ToastUtils.showSafeToast(
                        context,
                        "Login Successful! Welcome ${response.user?.name ?: "User"}"
                    )
                    onSubmit(otpValues.joinToString(""))
                }
            }
            is VerifyOtpUiState.Error -> {
                isSubmitting = false
                ToastUtils.showSafeToast(context, (verifyOtpState as VerifyOtpUiState.Error).message)
                otpValues.forEachIndexed { index, _ ->
                    otpValues[index] = ""
                }
                focusRequesters[0].requestFocus()
            }
            is VerifyOtpUiState.Loading -> {
                isSubmitting = true
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = R.drawable.bg_movies,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.7f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .width(500.dp)
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1A1A1A)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "OTP Verification",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Enter the 4-digit OTP sent to",
                        fontSize = 18.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        text = phoneNumber,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE50914)
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    if (sendOtpState is OtpUiState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(48.dp)
                                .align(Alignment.CenterHorizontally),
                            color = Color(0xFFE50914)
                        )
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            for (i in 0 until otpLength) {
                                OtpTextField(
                                    value = otpValues[i],
                                    onValueChange = { newValue ->
                                        if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                                            otpValues[i] = newValue
                                            if (newValue.isNotEmpty() && i < otpLength - 1) {
                                                focusRequesters[i + 1].requestFocus()
                                            } else if (newValue.isNotEmpty() && i == otpLength - 1) {
                                                val otp = (otpValues.toList().subList(0, otpLength - 1) + newValue).joinToString("")
                                                if (otp.length == otpLength) {
                                                    viewModel.verifyOtp(phoneNumber, otp, deviceId, macId, deviceName)
                                                }
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .size(80.dp)
                                        .focusRequester(focusRequesters[i])
                                        .padding(end = if (i < otpLength - 1) 16.dp else 0.dp),
                                    enabled = !isSubmitting
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

//                        if (timerState.isRunning) {
//                            Text(
//                                text = String.format("00:%02d", timerState.seconds),
//                                fontSize = 22.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = Color.White,
//                                modifier = Modifier.align(Alignment.CenterHorizontally)
//                            )
//                        }

                        if (isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(48.dp)
                                    .align(Alignment.CenterHorizontally),
                                color = Color(0xFFE50914)
                            )
                        }

                        if (!timerState.isRunning) {
                            TextButton(
                                onClick = {
                                    viewModel.sendOtp(phoneNumber, deviceId, macId, deviceName)
                                    otpValues.forEachIndexed { index, _ ->
                                        otpValues[index] = ""
                                    }
                                    focusRequesters[0].requestFocus()
                                    onResend()
                                },
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                enabled = !isSubmitting
                            ) {
                                Text(
                                    text = "Resend OTP",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE50914)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                val otp = otpValues.joinToString("")
                                if (otp.length == otpLength) {
                                    viewModel.verifyOtp(phoneNumber, otp, deviceId, macId, deviceName)
                                }
                            },
                            enabled = otpValues.all { it.isNotEmpty() } &&
                                    !isSubmitting &&
                                    timerState.isRunning,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE50914),
                                disabledContainerColor = Color(0xFFE50914).copy(alpha = 0.5f)
                            )
                        ) {
                            Text(
                                text = "Verify OTP",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OtpTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    enabled: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            color = Color.White
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.Gray,
            cursorColor = Color.White
        )
    )
}