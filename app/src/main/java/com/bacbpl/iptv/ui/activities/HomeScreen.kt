package com.bacbpl.iptv.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bacbpl.iptv.JetStreamActivity

class HomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                DarkCardUI()
            }
        }
    }
}

@Composable
fun DarkCardUI() {
    val context = LocalContext.current
    var selectedCard by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            // FAMILY CARD
//            SelectableCard(
//                title = "Family",
//                isSelected = selectedCard == "Family",
//                onFocus = { selectedCard = "Family" }
//            ) {
//                val intent = Intent(context, MainActivity::class.java)
//                context.startActivity(intent)
//            }

            // CHILD CARD
            SelectableCard(
                title = "Guest",
                isSelected = selectedCard == "Guest",
                onFocus = { selectedCard = "Guest" }
            ) {
                val intent = Intent(context, JetStreamActivity::class.java)
                context.startActivity(intent)
            }

            // ACCOUNT CARD - Launch JetStreamActivity
            SelectableCard(
                title = "Account",
                isSelected = selectedCard == "Account",
                onFocus = { selectedCard = "Account" }
            ) {
                // Launch JetStreamActivity - user can click profile icon in top bar
                val intent = Intent(context, JetStreamActivity::class.java)
                // Optional: Add extra to indicate we want to go to profile
                intent.putExtra("navigate_to_profile", true)
                context.startActivity(intent)
            }
        }

        Spacer(modifier = Modifier.height(60.dp))

//        Row(
//            horizontalArrangement = Arrangement.spacedBy(20.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            TvButton("Button 1")
//            TvButton("Button 2")
//            TvButton("Button 3")
//        }
    }
}

@Composable
fun SelectableCard(
    title: String,
    isSelected: Boolean,
    onFocus: () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(240.dp, 150.dp)
            .onFocusChanged {
                if (it.isFocused) onFocus()
            }
            .focusable()
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) Color.White else Color.Gray,
                shape = RoundedCornerShape(18.dp)
            )
            .background(
                color = if (isSelected) Color(0xFF3A3A3A) else Color(0xFF2A2A2A),
                shape = RoundedCornerShape(18.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

//@Composable
//fun TvButton(text: String) {
//    var focused by remember { mutableStateOf(false) }
//
//    Box(
//        modifier = Modifier
//            .onFocusChanged { focused = it.isFocused }
//            .focusable()
//            .background(
//                color = if (focused) Color.White else Color.DarkGray,
//                shape = RoundedCornerShape(50)
//            )
//            .padding(horizontal = 30.dp, vertical = 12.dp)
//    ) {
//        Text(
//            text = text,
//            color = if (focused) Color.Black else Color.White
//        )
//    }
//}