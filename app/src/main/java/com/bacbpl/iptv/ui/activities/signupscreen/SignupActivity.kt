package com.bacbpl.iptv.ui.activities.signupscreen

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bacbpl.iptv.R
import com.bacbpl.iptv.utils.ToastUtils
import com.bacbpl.iptv.utils.UserSession
import com.bacbpl.iptv.ui.activities.signupscreen.viewmodel.SignupUiState
import com.bacbpl.iptv.ui.activities.signupscreen.viewmodel.SignupViewModel
import com.bacbpl.iptv.ui.activities.signupscreen.viewmodel.SignupViewModelFactory
import java.util.regex.Pattern

@Composable
fun SignupActivity(
    onNavigateToSignIn: () -> Unit,
    onSignupSuccess: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: SignupViewModel = viewModel(
        factory = SignupViewModelFactory(context.applicationContext as Application)
    )

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    // Validation states
    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }

    var nameTouched by remember { mutableStateOf(false) }
    var emailTouched by remember { mutableStateOf(false) }
    var phoneTouched by remember { mutableStateOf(false) }

    val signupState by viewModel.signupState.collectAsState()

    // Handle signup state with ToastUtils
    LaunchedEffect(signupState) {
        when (val state = signupState) {
            is SignupUiState.Success -> {
                if (state.response.status) {
                    ToastUtils.showSafeToast(context, state.response.message)

                    // Update UserSession
                    UserSession.updateSession(context)

                    // Small delay to ensure session is updated
                    kotlinx.coroutines.delay(500)

                    // Navigate to main screen
                    onSignupSuccess()
                } else {
                    ToastUtils.showSafeToast(context, state.response.message ?: "Signup failed")
                }
                viewModel.resetState()
            }
            is SignupUiState.Error -> {
                ToastUtils.showSafeToast(context, state.message)
                viewModel.resetState()
            }
            else -> {}
        }
    }

    // Validation functions
    fun validateName(name: String): String? {
        return when {
            name.isBlank() -> "Name is required"
            name.length < 2 -> "Name must be at least 2 characters"
            name.length > 50 -> "Name must be less than 50 characters"
            !name.matches(Regex("^[a-zA-Z\\s]+$")) -> "Name can only contain letters and spaces"
            else -> null
        }
    }

    fun validateEmail(email: String): String? {
        val emailPattern = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        )
        return when {
            email.isBlank() -> "Email is required"
            !emailPattern.matcher(email).matches() -> "Please enter a valid email address"
            else -> null
        }
    }

    fun validatePhone(phone: String): String? {
        val cleanedPhone = phone.replace(Regex("[\\s-]"), "")
        return when {
            phone.isBlank() -> "Phone number is required"
            !cleanedPhone.matches(Regex("^[0-9]{10}$")) -> "Please enter a valid 10-digit phone number"
            else -> null
        }
    }

    fun validateAllFields(): Boolean {
        nameError = validateName(name)
        emailError = validateEmail(email)
        phoneError = validatePhone(phone)

        nameTouched = true
        emailTouched = true
        phoneTouched = true

        return nameError == null && emailError == null && phoneError == null
    }

    // Check if form is valid for button enable
    val isFormValid = remember(name, email, phone, nameError, emailError, phoneError) {
        name.isNotBlank() && email.isNotBlank() && phone.length == 10 &&
                nameError == null && emailError == null && phoneError == null
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        AsyncImage(
            model = R.drawable.bg_movies,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Dark Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f))
        )

        // Card
        Card(
            modifier = Modifier
                .width(450.dp)
                .wrapContentHeight()
                .align(Alignment.Center),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1A1A1A)
            )
        ) {
            Column(
                modifier = Modifier.padding(28.dp)
            ) {
                // Title with Back Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onNavigateToSignIn,
                        enabled = signupState !is SignupUiState.Loading
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Text(
                        text = "Sign Up",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(48.dp))
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Input Fields
                SignupTextField(
                    value = name,
                    hint = "Enter Name",
                    error = if (nameTouched) nameError else null,
                    onValueChange = {
                        name = it
                        nameError = validateName(it)
                    },
                    onFocusChanged = { hasFocus ->
                        if (!hasFocus) nameTouched = true
                    },
                    enabled = signupState !is SignupUiState.Loading
                )

                SignupTextField(
                    value = email,
                    hint = "Enter Email",
                    keyboardType = KeyboardType.Email,
                    error = if (emailTouched) emailError else null,
                    onValueChange = {
                        email = it
                        emailError = validateEmail(it)
                    },
                    onFocusChanged = { hasFocus ->
                        if (!hasFocus) emailTouched = true
                    },
                    enabled = signupState !is SignupUiState.Loading
                )

                SignupTextField(
                    value = phone,
                    hint = "Enter Phone Number",
                    keyboardType = KeyboardType.Phone,
                    error = if (phoneTouched) phoneError else null,
                    onValueChange = {
                        val cleaned = it.filter { char -> char.isDigit() }
                        if (cleaned.length <= 10) {
                            phone = cleaned
                            phoneError = validatePhone(cleaned)
                        }
                    },
                    onFocusChanged = { hasFocus ->
                        if (!hasFocus) phoneTouched = true
                    },
                    enabled = signupState !is SignupUiState.Loading
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Submit Button or Loading Indicator
                if (signupState is SignupUiState.Loading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFFE50914)
                        )
                    }
                } else {
                    Button(
                        onClick = {
                            if (validateAllFields()) {
                                val formattedPhone = "+91$phone"
                                viewModel.signup(formattedPhone, name, email)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE50914),
                            disabledContainerColor = Color(0xFFE50914).copy(alpha = 0.3f)
                        ),
                        enabled = isFormValid
                    ) {
                        Text(
                            text = "Submit",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Sign In Link
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Already have an account? ",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 16.sp
                    )
                    TextButton(
                        onClick = onNavigateToSignIn,
                        enabled = signupState !is SignupUiState.Loading
                    ) {
                        Text(
                            text = "Sign In",
                            color = Color(0xFFE50914),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SignupTextField(
    value: String,
    hint: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    error: String? = null,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = hint,
                    color = if (enabled) Color.White.copy(alpha = 0.7f) else Color.Gray
                )
            },
            placeholder = {
                Text(
                    text = hint,
                    color = Color.Gray
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            isError = error != null,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .onFocusChanged { focusState ->
                    onFocusChanged(focusState.isFocused)
                },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.Gray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                disabledTextColor = Color.Gray,
                disabledBorderColor = Color.DarkGray,
                disabledLabelColor = Color.Gray,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red
            ),
            textStyle = LocalTextStyle.current.copy(
                color = if (enabled) Color.White else Color.Gray,
                fontSize = 16.sp
            ),
            singleLine = true
        )

        if (error != null) {
            Text(
                text = error,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 2.dp)
            )
        }
    }
}