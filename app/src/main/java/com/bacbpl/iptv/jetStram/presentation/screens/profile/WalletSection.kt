package com.bacbpl.iptv.jetStram.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bacbpl.iptv.R

@Composable
fun WalletSection(
    isSubtitlesChecked: Boolean,
    onSubtitleCheckChange: (isChecked: Boolean) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.bg_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF101010).copy(alpha = 0.7f))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Connect Mobile with TV",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Mobile QR Scan Section
                Card(
                    modifier = Modifier
                        .width(250.dp)
                        .height(450.dp)
                        .shadow(0.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black.copy(alpha = 0.0f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ph_image),
                            contentDescription = "Mobile QR",
                            modifier = Modifier
                                .width(250.dp)
                                .height(450.dp)
                        )
                    }
                }
                // Payment Screen Section
                Card(
                    modifier = Modifier
                        .width(550.dp)
                        .height(500.dp)
                        .shadow(20.dp, RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    PaymentScreen()
                }
            }

//            Spacer(modifier = Modifier.height(30.dp))
//
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "Enable Subtitles",
//                    color = Color.White,
//                    fontSize = 16.sp
//                )
//                Spacer(modifier = Modifier.width(10.dp))
//
//                Switch(
//                    checked = isSubtitlesChecked,
//                    onCheckedChange = onSubtitleCheckChange
//                )
//            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PaymentScreen() {
    var selectedTab by remember { mutableStateOf("Credit Card") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2A1B1B),
                        Color(0xFF1A0F0F),
                        Color(0xFF0A0505)
                    )
                )
            )
            .padding(horizontal = 20.dp, vertical = 15.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "⚡ Payment",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Payment Method Toggle
            Row(
                modifier = Modifier
                    .background(Color(0xFF3A2A2A), RoundedCornerShape(30))
                    .padding(4.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                PaymentTab(
                    title = "💳 Card",
                    selected = selectedTab == "Credit Card",
                    onSelected = {
                        selectedTab = "Credit Card"
                    }
                )

                PaymentTab(
                    title = "📱 QR Code",
                    selected = selectedTab == "QR Code",
                    onSelected = {
                        selectedTab = "QR Code"
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Payment Content based on selected tab
            when (selectedTab) {
                "Credit Card" -> CreditCardContent()
                "QR Code" -> QRCodePaymentContent()
            }

            Spacer(modifier = Modifier.weight(1f))

            // Footer
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "🔒 Secure Payment",
                    color = Color.Green.copy(alpha = 0.7f),
                    fontSize = 11.sp
                )
                Text(
                    text = "Need help? help.bacbpl.com",
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun PaymentTab(title: String, selected: Boolean, onSelected: () -> Unit) {
    Box(
        modifier = Modifier
            .then(
                if (selected) {
                    Modifier.background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Red,
                                Color(0xFFFF6B6B)
                            )
                        ),
                        RoundedCornerShape(30)
                    )
                } else Modifier
            )
            .clip(RoundedCornerShape(30))
            .clickable { onSelected() }
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Text(
            text = title,
            color = if (selected) Color.White else Color.LightGray,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 14.sp
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreditCardContent() {
    // State variables for card details
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var cardHolderName by remember { mutableStateOf("") }

    // Focus states
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    // Focus requesters for each field
    val cardNumberFocus = remember { FocusRequester() }
    val expiryFocus = remember { FocusRequester() }
    val cvvFocus = remember { FocusRequester() }
    val nameFocus = remember { FocusRequester() }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Custom Credit Card Display (shows entered values)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF434343),
                                Color(0xFF1A1A1A)
                            )
                        ),
                        RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                // Card Chip
//                Box(
//                    modifier = Modifier
//                        .size(40.dp)
//                        .background(Color(0xFFFFD700), RoundedCornerShape(4.dp))
//                        .align(Alignment.TopStart)
//                )
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Transparent)
                        .align(Alignment.TopStart)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_chip),
                        contentDescription = "Card Chip",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Card Type
                Text(
                    text = "CREDIT",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.TopEnd)
                )

                // Card Number - shows entered or masked
                Text(
                    text = if (cardNumber.isNotEmpty()) cardNumber else "****  ****  ****  ****",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    modifier = Modifier.align(Alignment.Center)
                )

                // Card Holder
                Column(
                    modifier = Modifier.align(Alignment.BottomStart)
                ) {
                    Text(
                        text = "CARD HOLDER",
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                    Text(
                        text = if (cardHolderName.isNotEmpty()) cardHolderName.uppercase() else "JOHN DOE",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Expiry
                Column(
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Text(
                        text = "EXPIRES",
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                    Text(
                        text = if (expiryDate.isNotEmpty()) expiryDate else "12/25",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Card Number Field (Editable)
        CustomTextField(
            value = cardNumber,
            onValueChange = {
                val cleaned = it.replace(" ", "").take(16)
                val formatted = cleaned.chunked(4).joinToString(" ")
                cardNumber = formatted
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .focusRequester(cardNumberFocus)
                .onPreviewKeyEvent { keyEvent ->
                    if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Tab) {
                        if (keyEvent.isShiftPressed) {
                            nameFocus.requestFocus()
                        } else {
                            expiryFocus.requestFocus()
                        }
                        true
                    } else false
                },
            placeholder = "1234 5678 9012 3456",
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Expiry Date and CVV Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Expiry Date (Editable)
            CustomTextField(
                value = expiryDate,
                onValueChange = {
                    var cleaned = it.replace("/", "").take(4)
                    if (cleaned.length >= 2) {
                        cleaned = cleaned.substring(0, 2) + "/" + cleaned.substring(2)
                    }
                    expiryDate = cleaned
                },
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .focusRequester(expiryFocus)
                    .onPreviewKeyEvent { keyEvent ->
                        if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Tab) {
                            if (keyEvent.isShiftPressed) {
                                cardNumberFocus.requestFocus()
                            } else {
                                cvvFocus.requestFocus()
                            }
                            true
                        } else false
                    },
                placeholder = "MM/YY",
                keyboardType = KeyboardType.Number
            )

            // CVV (Editable) - Fixed the visual transformation
            CustomTextField(
                value = cvv,
                onValueChange = { cvv = it.take(3) },
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .focusRequester(cvvFocus)
                    .onPreviewKeyEvent { keyEvent ->
                        if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Tab) {
                            if (keyEvent.isShiftPressed) {
                                expiryFocus.requestFocus()
                            } else {
                                nameFocus.requestFocus()
                            }
                            true
                        } else false
                    },
                placeholder = "CVV",
                keyboardType = KeyboardType.Number,
                isPassword = true // Added password mode for CVV
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Card Holder Name (Editable)
        CustomTextField(
            value = cardHolderName,
            onValueChange = { cardHolderName = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .focusRequester(nameFocus)
                .onPreviewKeyEvent { keyEvent ->
                    if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Tab) {
                        if (keyEvent.isShiftPressed) {
                            cvvFocus.requestFocus()
                        } else {
                            cardNumberFocus.requestFocus()
                        }
                        true
                    } else false
                },
            placeholder = "Card Holder Name",
            keyboardType = KeyboardType.Text
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Pay Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Red,
                            Color(0xFFFF6B6B)
                        )
                    ),
                    RoundedCornerShape(25.dp)
                )
                .clickable {
                    // Payment processing logic here
                }
                .focusable(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Pay $199.99",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false // Added password parameter
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .padding(horizontal = 16.dp)
            .onKeyEvent { keyEvent ->
                if (keyEvent.type == KeyEventType.KeyDown) {
                    when (keyEvent.key) {
                        Key.Enter, Key.NumPadEnter -> {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            true
                        }
                        Key.DirectionDown -> {
                            focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Down)
                            true
                        }
                        Key.DirectionUp -> {
                            focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Up)
                            true
                        }
                        else -> false
                    }
                } else false
            },
        textStyle = LocalTextStyle.current.copy(
            color = Color.White,
            fontSize = 16.sp
        ),
        cursorBrush = SolidColor(Color.Red),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            },
            onNext = {
                focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Down)
            }
        ),
        visualTransformation = if (isPassword) {
            // Simple password transformation - shows dots
            VisualTransformation { text ->
                androidx.compose.ui.text.input.TransformedText(
                    androidx.compose.ui.text.AnnotatedString("•".repeat(text.text.length)),
                    object : androidx.compose.ui.text.input.OffsetMapping {
                        override fun originalToTransformed(offset: Int): Int = offset
                        override fun transformedToOriginal(offset: Int): Int = offset
                    }
                )
            }
        } else {
            VisualTransformation.None
        },
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.CenterStart
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
fun QRCodePaymentContent() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Large QR Code Display
        Card(
            modifier = Modifier
                .size(220.dp)
                .shadow(10.dp, RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.qr_code),
                    contentDescription = "Payment QR Code",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // QR Code Info
        Text(
            text = "Scan QR Code to Pay",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Use any UPI app or Mobile Banking to scan",
            color = Color.LightGray,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Amount
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.1f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total Amount:",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
                Text(
                    text = "$199.99",
                    color = Color.Green,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Pay Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFFF6B6B),
                            Color.Red
                        )
                    ),
                    RoundedCornerShape(25.dp)
                )
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Confirm Payment",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}