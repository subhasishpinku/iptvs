package com.bacbpl.iptv.ui.activities.subscribescreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter
import com.bacbpl.iptv.ui.activities.subscribescreen.data.Plan
import com.bacbpl.iptv.ui.activities.subscribescreen.data.repositories.PlanRepository

import com.bacbpl.iptv.utils.ImageLoader
import java.text.NumberFormat
import java.util.*
import com.bacbpl.iptv.R

@Composable
fun DynamicPlanCard(
    plan: Plan,
    onPlanSelected: (Plan) -> Unit,
    isSelected: Boolean = false,
    isSubscribing: Boolean = false  // Add this parameter
) {
    var isFocused by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val price = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
        .format(plan.sysPlanPrice.toDouble())
        .replace("₹", "") // Remove ₹ symbol if needed

    Card(
        modifier = Modifier
            .width(280.dp)
            .onFocusChanged { isFocused = it.isFocused }
            .focusable()
            .border(
                width = if (isFocused || isSelected) 3.dp else 0.dp,
                color = if (isFocused || isSelected) Color.Red else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEDEDED)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isFocused) 8.dp else 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Plan Badge based on plan name
            val planType = PlanRepository.getPlanTypeFromName(plan.sysPlanName)
            if (planType == PlanRepository.PLAN_TYPE_PREMIUM || planType == PlanRepository.PLAN_TYPE_GOLD || planType == PlanRepository.PLAN_TYPE_SILVER) {
                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = Color(0xFFD60000),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "BEST VALUE",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }

            Text(
                text = plan.sysPlanName,
                fontSize = 18.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )

            Text(
                text = "for ₹$price ${plan.sysPlanDurationMode}",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            // OTT Logos Row
            if (plan.ottList.isNotEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    plan.ottList.take(4).forEach { ott ->
                        // Use Coil for image loading
                        val painter = rememberAsyncImagePainter(
                            model = "http://192.168.1.11:8080/ott-icons/${ott.ottThumbnail}",
                            placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                            error = painterResource(android.R.drawable.ic_menu_gallery)
                        )

                        Image(
                            painter = painter,
                            contentDescription = ott.ottName,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(2.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White)
                                .padding(4.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                    if (plan.ottList.size > 4) {
                        Text(
                            text = "+${plan.ottList.size - 4}",
                            modifier = Modifier
                                .size(40.dp)
                                .padding(2.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray)
                                .padding(4.dp)
                                .wrapContentSize(Alignment.Center),
                            fontSize = 12.sp
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.height(12.dp))

            // OTT Grid Image
            Image(
                painter = painterResource(R.drawable.ott_grid),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )



            Spacer(modifier = Modifier.height(16.dp))

            // Subscribe Button
//            Button(
//                onClick = { onPlanSelected(plan) },
//                modifier = Modifier.fillMaxWidth(),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color(0xFFD60000)
//                ),
//                shape = RoundedCornerShape(8.dp)
//            ) {
//                Text(
//                    text = "Subscribe for ₹$price",
//                    color = Color.White
//                )
//            }
            // Subscribe Button
            Button(
                onClick = {
                    if (!isSubscribing) {
                        onPlanSelected(plan)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD60000),
                    disabledContainerColor = Color.Gray
                ),
                shape = RoundedCornerShape(8.dp),
                enabled = !isSubscribing  // Disable button while subscribing
            ) {
                if (isSubscribing) {
                    Row(horizontalArrangement = Arrangement.Center) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Processing...",
                            color = Color.White
                        )
                    }
                } else {
                    Text(
                        text = "Subscribe for ₹$price",
                        color = Color.White
                    )
                }
            }
            // Savings Text based on duration
            val savings = when (plan.sysPlanDuration) {
                PlanRepository.DURATION_QUARTERLY -> "15%"
                PlanRepository.DURATION_HALF_YEARLY -> "25%"
                else -> "0%"
            }
            Text(
                text = "Save $savings",
                color = Color(0xFF00FF66),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

// Legacy support for painterResource
@Composable
fun painterResource(id: Int) = androidx.compose.ui.res.painterResource(id = id)