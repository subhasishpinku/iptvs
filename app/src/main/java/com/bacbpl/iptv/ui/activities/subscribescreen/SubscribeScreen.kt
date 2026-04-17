package com.bacbpl.iptv.ui.activities.subscribescreen
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bacbpl.iptv.JetStreamActivity
import com.bacbpl.iptv.R
import com.bacbpl.iptv.data.SharedPrefManager
import com.bacbpl.iptv.ui.activities.HomeScreen
import com.bacbpl.iptv.ui.activities.subscribescreen.components.DynamicPlanCard
import com.bacbpl.iptv.ui.activities.subscribescreen.data.Plan
import com.bacbpl.iptv.ui.activities.subscribescreen.data.repositories.PlanRepository
import com.bacbpl.iptv.ui.activities.subscribescreen.viewmodels.PlanViewModel
import com.bacbpl.iptv.utils.ToastUtils
import kotlinx.coroutines.delay
import kotlin.jvm.java

class SubscribeScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SubscribeUI()
       //     SubscribeUI1()
        }
    }
}
//@Composable
//fun SubscribeUI() {
//    val planViewModel: PlanViewModel = viewModel()
//    val monthlyPlans by planViewModel.monthlyPlans.observeAsState(emptyList())
//    val quarterlyPlans by planViewModel.quarterlyPlans.observeAsState(emptyList())
//    val halfYearlyPlans by planViewModel.halfYearlyPlans.observeAsState(emptyList())
//    val isLoading by planViewModel.isLoading.observeAsState(false)
//    val errorMessage by planViewModel.errorMessage.observeAsState()
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFF050505))
//            .padding(16.dp)
//    ) {
//        item { TopSection() }
//        item { Spacer(modifier = Modifier.height(16.dp)) }
//        item { BannerRow() }
//        item { Spacer(modifier = Modifier.height(20.dp)) }
//        item { SubscribeSection() }
//        item { Spacer(modifier = Modifier.height(20.dp)) }
//
//        // Dynamic Plan Section
//        item {
//            DynamicChoosePlanSection(
//                monthlyPlans = monthlyPlans,
//                quarterlyPlans = quarterlyPlans,
//                halfYearlyPlans = halfYearlyPlans,
//                isLoading = isLoading,
//                errorMessage = errorMessage,
//                onRetry = { planViewModel.loadAllPlans() },
//                onPlanSelected = { plan ->
//                    planViewModel.selectPlan(plan)
//                    // Navigate to payment or home screen
////                    val intent = Intent(LocalContext.current, HomeScreen::class.java)
////                    LocalContext.current.startActivity(intent)
//                }
//            )
//        }
//    }
//
//    // Show error message if any
//    errorMessage?.let { message ->
//        LaunchedEffect(message) {
//            // You can show a snackbar or toast here
//            planViewModel.clearErrorMessage()
//        }
//    }
//}
@Composable
fun SubscribeUI() {
    val planViewModel: PlanViewModel = viewModel()
    val monthlyPlans by planViewModel.monthlyPlans.observeAsState(emptyList())
    val quarterlyPlans by planViewModel.quarterlyPlans.observeAsState(emptyList())
    val halfYearlyPlans by planViewModel.halfYearlyPlans.observeAsState(emptyList())
    val isLoading by planViewModel.isLoading.observeAsState(false)
    val errorMessage by planViewModel.errorMessage.observeAsState()
    val subscribeResponse by planViewModel.subscribeResponse.observeAsState()
    val isSubscribing by planViewModel.isSubscribing.observeAsState(false)
    val navigateToProfile by planViewModel.navigateToProfile.observeAsState(false)

    val context = LocalContext.current
    // Auto navigate after 5 seconds
    LaunchedEffect(Unit) {
        delay(5000)
//        val intent = Intent(context, HomeScreen::class.java)
//        val intent = Intent(context, JetStreamActivity::class.java)
//        context.startActivity(intent)
    }

    val sharedPrefManager = remember { SharedPrefManager(context) }

    // Handle navigation to Profile (when subscriber doesn't exist)
    LaunchedEffect(navigateToProfile) {
        if (navigateToProfile) {
            delay(500) // Small delay to show error message
//            val intent = Intent(context, JetStreamActivity::class.java).apply {
//                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//                putExtra("navigate_to_profile", true)
//            }
//            context.startActivity(intent)
            planViewModel.clearNavigateToProfile()
        }
    }

    // Handle subscription response
    LaunchedEffect(subscribeResponse) {
        subscribeResponse?.let { response ->
            val toastMessage = if (response.success || response.status == true) {
                "✅ Subscription successful!"
            } else {
                "❌ ${response.message}"
            }
//            Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
            if (context is Activity && !context.isFinishing && !context.isDestroyed) {
//                Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
                ToastUtils.showSafeToast(context, toastMessage)
            }
            planViewModel.clearSubscribeResponse()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
            .padding(16.dp)
    ) {
        item { TopSection() }
        item { Spacer(modifier = Modifier.height(5.dp)) }
        item { BannerRow() }
        item { Spacer(modifier = Modifier.height(10.dp)) }

        // Dynamic Plan Section
        item {
            DynamicChoosePlanSection(
                monthlyPlans = monthlyPlans,
                quarterlyPlans = quarterlyPlans,
                halfYearlyPlans = halfYearlyPlans,
                isLoading = isLoading,
                isSubscribing = isSubscribing,
                errorMessage = errorMessage,
                onRetry = { planViewModel.loadAllPlans() },
                onPlanSelected = { plan ->
                    planViewModel.selectPlan(plan)

                    // Get mobile number from SharedPreferences
                    val mobile = sharedPrefManager.getUserMobile()
                    println("mobile_Print: $mobile")

                    // Remove +91 country code
                    val cleanMobile = mobile?.replace("+91", "")?.trim()
                    println("cleanMobile_Print: $cleanMobile")

                    if (cleanMobile.isNullOrEmpty()) {
//                        Toast.makeText(
//                            context,
//                            "User mobile number not found. Please login again.",
//                            Toast.LENGTH_LONG
//                        ).show()
                        ToastUtils.showSafeToast(
                            context,
                            "User mobile number not found. Please login again."
                        )
                        return@DynamicChoosePlanSection
                    }

                    // Call subscription
                    planViewModel.subscribeToPlan(cleanMobile, plan.id, context)
                }
            )
        }
        item { Spacer(modifier = Modifier.height(10.dp)) }
        item { SubscribeSection() }
        item { Spacer(modifier = Modifier.height(10.dp)) }
    }

    // Show error message if any
    errorMessage?.let { message ->
        LaunchedEffect(message) {
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            ToastUtils.showSafeToast(context, message)
            planViewModel.clearErrorMessage()
        }
    }

    // Show loading indicator for subscription
    if (isSubscribing) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Processing subscription...",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun DynamicChoosePlanSection(
    monthlyPlans: List<Plan>,
    quarterlyPlans: List<Plan>,
    halfYearlyPlans: List<Plan>,
    isLoading: Boolean,
    isSubscribing: Boolean,
    errorMessage: String?,
    onRetry: () -> Unit,
    onPlanSelected: (Plan) -> Unit
) {
    var selectedPeriod by remember { mutableStateOf("monthly") }
    val selectedPlan by remember { mutableStateOf<Plan?>(null) }
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Choose a Plan",
            color = Color.White,
            fontSize = 22.sp
        )

        Text(
            text = "TRENDING OFFER",
            color = Color(0xFF00FF66),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Period Selection Tabs
        DynamicPeriodTabs(
            selectedPeriod = selectedPeriod,
            onPeriodSelected = { selectedPeriod = it }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Loading indicator
        if (isLoading) {
            CircularProgressIndicator(color = Color.Red)
        }

        // Error message
        else if (!errorMessage.isNullOrEmpty()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp
                )
                Button(
                    onClick = onRetry,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Retry")
                }
            }
        }

        // Plans display
        else {
            // Get plans based on selected period
            val plansToShow = when (selectedPeriod) {
                "monthly" -> monthlyPlans
                "quarterly" -> quarterlyPlans
                "halfyearly" -> halfYearlyPlans
                else -> emptyList()
            }

            if (plansToShow.isEmpty()) {
                Text(
                    text = "No plans available",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            } else {
                // Sort plans by price (Premium first, then Gold, then Silver)
                val sortedPlans = plansToShow.sortedByDescending { plan ->
                    when (PlanRepository.getPlanTypeFromName(plan.sysPlanName)) {
                        PlanRepository.PLAN_TYPE_PREMIUM -> 1
                        PlanRepository.PLAN_TYPE_GOLD -> 2
                        PlanRepository.PLAN_TYPE_SILVER -> 3
                        else -> 0
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        items(sortedPlans.size) { index ->
                            DynamicPlanCard(
                                plan = sortedPlans[index],
                                onPlanSelected = { plan ->
                                    // Show plan info in Toast
//                                    Toast.makeText(
//                                        context,
//                                        "Processing: ${plan.sysPlanName} - ₹${plan.sysPlanPrice}",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
                                    ToastUtils.showSafeToast(
                                        context,
                                        "Processing: ${plan.sysPlanName} - ₹${plan.sysPlanPrice}"
                                    )
                                    // Call the subscription function
                                    onPlanSelected(plan)
                                },
                                isSelected = selectedPlan?.id == sortedPlans[index].id,
                                isSubscribing = isSubscribing
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun DynamicChoosePlanSection(
    monthlyPlans: List<Plan>,
    quarterlyPlans: List<Plan>,
    halfYearlyPlans: List<Plan>,
    isLoading: Boolean,
    errorMessage: String?,
    onRetry: () -> Unit,
    onPlanSelected: (Plan) -> Unit
) {
    var selectedPeriod by remember { mutableStateOf("monthly") }
    val selectedPlan by remember { mutableStateOf<Plan?>(null) }
    val context = LocalContext.current  // ← Get context here
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Choose a Plan",
            color = Color.White,
            fontSize = 22.sp
        )

        Text(
            text = "TRENDING OFFER",
            color = Color(0xFF00FF66),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Period Selection Tabs
        DynamicPeriodTabs(
            selectedPeriod = selectedPeriod,
            onPeriodSelected = { selectedPeriod = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Loading indicator
        if (isLoading) {
            CircularProgressIndicator(color = Color.Red)
        }

        // Error message
        else if (!errorMessage.isNullOrEmpty()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp
                )
                Button(
                    onClick = onRetry,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Retry")
                }
            }
        }

        // Plans display
        else {
            // Get plans based on selected period
            val plansToShow = when (selectedPeriod) {
                "monthly" -> monthlyPlans
                "quarterly" -> quarterlyPlans
                "halfyearly" -> halfYearlyPlans
                else -> emptyList()
            }

            if (plansToShow.isEmpty()) {
                Text(
                    text = "No plans available",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            } else {
                // Sort plans by price (Premium first, then Gold, then Silver)
                val sortedPlans = plansToShow.sortedBy { plan ->
                    when (PlanRepository.getPlanTypeFromName(plan.sysPlanName)) {
                        PlanRepository.PLAN_TYPE_PREMIUM -> 3
                        PlanRepository.PLAN_TYPE_GOLD -> 2
                        PlanRepository.PLAN_TYPE_SILVER -> 1
                        else -> 4
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        items(sortedPlans.size) { index ->
                            DynamicPlanCard(
                                plan = sortedPlans[index],
                                //onPlanSelected = onPlanSelected,
                                onPlanSelected = { plan ->
                                    // Show Toast when plan is selected
//                                    Toast.makeText(
//                                        context,  // ← Now using the correct context
//                                        "Selected: ${plan.sysPlanName} - ₹${plan.sysPlanPrice}- ${plan.id}",
//                                        Toast.LENGTH_SHORT
//                                    ).show()

                                    ToastUtils.showSafeToast(
                                        context,
                                        "Selected: ${plan.sysPlanName} - ₹${plan.sysPlanPrice}- ${plan.id}"
                                    )

                                    // Call the original onPlanSelected callback
                                    onPlanSelected(plan)
                                },
                                isSelected = selectedPlan?.id == sortedPlans[index].id

                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DynamicPeriodTabs(
    selectedPeriod: String,
    onPeriodSelected: (String) -> Unit
) {
    val periods = listOf("Monthly", "Quarterly", "Half-Yearly")
    val periodKeys = listOf("monthly", "quarterly", "halfyearly")
    val interactionSources = remember { periods.map { MutableInteractionSource() } }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .background(Color(0xFF2A2A2A), RoundedCornerShape(50.dp))
            .padding(4.dp)
    ) {
        periods.forEachIndexed { index, period ->
            val isSelected = selectedPeriod == periodKeys[index]
            val interactionSource = interactionSources[index]
            val isFocused by interactionSource.collectIsFocusedAsState()

            Surface(
                modifier = Modifier
                    .focusable(interactionSource = interactionSource)
                    .then(
                        if (isFocused) {
                            Modifier.border(2.dp, Color.Red, RoundedCornerShape(50.dp))
                        } else {
                            Modifier
                        }
                    ),
                shape = RoundedCornerShape(50.dp),
                color = if (isSelected) Color(0xFFD60000) else Color.Transparent,
                onClick = { onPeriodSelected(periodKeys[index]) }
            ) {
                Text(
                    text = period,
                    color = if (isSelected) Color.White else Color.Gray,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }
        }
    }
}
//@Composable
//fun SubscribeUI1() {
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFF050505))
//            .padding(16.dp)
//    ) {
//
//        item { TopSection() }
//
//        item { Spacer(modifier = Modifier.height(16.dp)) }
//
//        item { BannerRow() }
//
//        item { Spacer(modifier = Modifier.height(20.dp)) }
//
//        item { SubscribeSection() }
//
//        item { Spacer(modifier = Modifier.height(20.dp)) }
//
//        item { ChoosePlanSection() }
//
//    }
//}

@Composable
fun TopSection() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "My Plan",
            color = Color.White,
            fontSize = 28.sp
        )

        Text(
            text = "One Stop For All Your Favourite\nOTT Subscriptions",
            color = Color.White,
            fontSize = 14.sp
        )
    }
}
@Composable
fun BannerRow() {
    val banners = listOf(
        R.drawable.plan_banner,
        R.drawable.banner2,
        R.drawable.banner3,
        R.drawable.plan_banner,
        R.drawable.banner2,
        R.drawable.banner4
    )

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var currentPage by remember { mutableStateOf(0) }

    // Create an infinite list by repeating the banners
    val infiniteBanners = remember {
        List(100) { index -> banners[index % banners.size] }
    }

    // Auto scroll continuously
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)

            // Get current visible item
            val currentItem = listState.firstVisibleItemIndex

            // Calculate next item (just increment, never reset)
            val nextItem = currentItem + 1

            // Animate to next item
            listState.animateScrollToItem(nextItem)

            // Update current page based on actual banner (modulo)
            currentPage = nextItem % banners.size
        }
    }

    Column {
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF4B2A2A))
                .padding(12.dp)
        ) {
            items(infiniteBanners.size) { index ->
                BannerImage(image = infiniteBanners[index])
            }
        }

        // Page Indicators (based on actual banner index)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(banners.size) { index ->
                Box(
                    modifier = Modifier
                        .size(if (currentPage == index) 10.dp else 6.dp)
                        .clip(RoundedCornerShape(50))
                        .background(
                            if (currentPage == index)
                                Color.Red
                            else
                                Color.Gray.copy(alpha = 0.5f)
                        )
                        .padding(horizontal = 2.dp)
                )
            }
        }
    }
}

@Composable
fun BannerImage(image: Int) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Image(
        painter = painterResource(id = image),
        contentDescription = "",
        modifier = Modifier
            .width(300.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(10.dp))
            .focusable(interactionSource = interactionSource)
            .then(
                if (isFocused) {
                    Modifier.border(3.dp, Color.Red, RoundedCornerShape(10.dp))
                } else {
                    Modifier
                }
            )
            .clickable {
                // Handle banner click
                // You can navigate to different screens based on which banner was clicked
            },
        contentScale = ContentScale.Crop
    )
}

@Composable
fun SubscribeSection() {
    // Add this line to get the context
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF4B2A2A))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "SUBSCRIBE TO WATCH",
            color = Color.White,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(10.dp))
        val interactionSource = remember { MutableInteractionSource() }
        val isFocused by interactionSource.collectIsFocusedAsState()

        Button(
            onClick = {
                val intent = Intent(context, JetStreamActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .focusable(interactionSource = interactionSource)
                .then(
                    if (isFocused) {
                        Modifier.border(2.dp, Color.Red, RoundedCornerShape(4.dp))
                    } else {
                        Modifier
                    }
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            interactionSource = interactionSource
        ) {
            Text(
                text = "SUBSCRIBE NOW",
                color = Color.Black
            )
        }
    }
}

//@Composable
//fun ChoosePlanSection() {
//    var selectedPeriod by remember { mutableStateOf("monthly") }
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Text(
//            text = "Choose a Plan",
//            color = Color.White,
//            fontSize = 22.sp
//        )
//
//        Text(
//            text = "TRENDING OFFER",
//            color = Color(0xFF00FF66),
//            fontSize = 14.sp
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Period Selection Tabs
//        PeriodTabs(
//            selectedPeriod = selectedPeriod,
//            onPeriodSelected = { selectedPeriod = it }
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        // Centered Plans
//        Box(
//            modifier = Modifier.fillMaxWidth(),
//            contentAlignment = Alignment.Center
//        ) {
//            LazyRow(
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier.wrapContentWidth()
//            ) {
//                items(3) { index ->
//                    when (selectedPeriod) {
//                        "monthly" -> {
//                            PlanCard(
//                                price = when (index) {
//                                    0 -> "99"
//                                    1 -> "199"
//                                    else -> "299"
//                                },
//                                period = "Monthly",
//                                logos = when (index) {
//                                    0 -> listOf(R.drawable.z5, R.drawable.sonyliv)
//                                    1 -> listOf(R.drawable.z5, R.drawable.sonyliv, R.drawable.jiohotstar)
//                                    else -> listOf(R.drawable.z5, R.drawable.sonyliv, R.drawable.jiohotstar, R.drawable.amazonprime)
//                                }
//                            )
//                        }
//                        "quarterly" -> {
//                            PlanCard(
//                                price = when (index) {
//                                    0 -> "249"
//                                    1 -> "499"
//                                    else -> "749"
//                                },
//                                period = "Quarterly",
//                                logos = when (index) {
//                                    0 -> listOf(R.drawable.z5, R.drawable.sonyliv)
//                                    1 -> listOf(R.drawable.z5, R.drawable.sonyliv, R.drawable.jiohotstar)
//                                    else -> listOf(R.drawable.z5, R.drawable.sonyliv, R.drawable.jiohotstar, R.drawable.amazonprime)
//                                }
//                            )
//                        }
//                        "halfyearly" -> {
//                            PlanCard(
//                                price = when (index) {
//                                    0 -> "449"
//                                    1 -> "899"
//                                    else -> "1349"
//                                },
//                                period = "Half-Yearly",
//                                logos = when (index) {
//                                    0 -> listOf(R.drawable.z5, R.drawable.sonyliv)
//                                    1 -> listOf(R.drawable.z5, R.drawable.sonyliv, R.drawable.jiohotstar)
//                                    else -> listOf(R.drawable.z5, R.drawable.sonyliv, R.drawable.jiohotstar, R.drawable.amazonprime)
//                                }
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun PeriodTabs(
//    selectedPeriod: String,
//    onPeriodSelected: (String) -> Unit
//) {
//    val periods = listOf("Monthly", "Quarterly", "Half-Yearly")
//    val interactionSources = remember { periods.map { MutableInteractionSource() } }
//
//    Row(
//        horizontalArrangement = Arrangement.spacedBy(8.dp),
//        modifier = Modifier
//            .background(Color(0xFF2A2A2A), RoundedCornerShape(50.dp))
//            .padding(4.dp)
//    ) {
//        periods.forEachIndexed { index, period ->
//            val periodKey = period.lowercase().replace("-", "")
//            val isSelected = selectedPeriod == periodKey
//            val interactionSource = interactionSources[index]
//            val isFocused by interactionSource.collectIsFocusedAsState()
//
//            Surface(
//                modifier = Modifier
//                    .focusable(interactionSource = interactionSource)
//                    .then(
//                        if (isFocused) {
//                            Modifier.border(2.dp, Color.Red, RoundedCornerShape(50.dp))
//                        } else {
//                            Modifier
//                        }
//                    ),
//                shape = RoundedCornerShape(50.dp),
//                color = if (isSelected) Color(0xFFD60000) else Color.Transparent,
//                onClick = { onPeriodSelected(periodKey) }
//            ) {
//                Text(
//                    text = period,
//                    color = if (isSelected) Color.White else Color.Gray,
//                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun PlanCard(
//    price: String,
//    period: String,
//    logos: List<Int>
//) {
//    var isFocused by remember { mutableStateOf(false) }
//    val context = LocalContext.current
//    val interactionSource = remember { MutableInteractionSource() }
//    val isButtonFocused by interactionSource.collectIsFocusedAsState()
//
//    Card(
//        modifier = Modifier
//            .width(280.dp)
//            .onFocusChanged { isFocused = it.isFocused }
//            .focusable()
//            .border(
//                width = if (isFocused) 3.dp else 0.dp,
//                color = if (isFocused) Color.Red else Color.Transparent,
//                shape = RoundedCornerShape(16.dp)
//            ),
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = Color(0xFFEDEDED)
//        ),
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = if (isFocused) 8.dp else 4.dp
//        )
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            // Plan Badge
//            Surface(
//                shape = RoundedCornerShape(50.dp),
//                color = Color(0xFFD60000),
//                modifier = Modifier.padding(bottom = 8.dp)
//            ) {
//                Text(
//                    text = "BEST VALUE",
//                    color = Color.White,
//                    fontSize = 12.sp,
//                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
//                )
//            }
//
//            Text(
//                text = "24 OTTs",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//            Text(
//                text = "for ₹$price $period",
//                fontSize = 14.sp,
//                color = Color.Gray
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // Logo Row
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                logos.forEach { logo ->
//                    Image(
//                        painter = painterResource(logo),
//                        contentDescription = "",
//                        modifier = Modifier
//                            .size(40.dp)
//                            .padding(2.dp)
//                            .clip(RoundedCornerShape(8.dp))
//                            .background(Color.White)
//                            .padding(4.dp)
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // OTT Grid Image
//            Image(
//                painter = painterResource(R.drawable.ott_grid),
//                contentDescription = "",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(120.dp)
//                    .clip(RoundedCornerShape(8.dp)),
//                contentScale = ContentScale.Crop
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Subscribe Button
//            Button(
//                onClick = {
//                    val intent = Intent(context, HomeScreen::class.java)
//                    context.startActivity(intent)
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .focusable(interactionSource = interactionSource)
//                    .then(
//                        if (isButtonFocused) {
//                            Modifier.border(2.dp, Color.Red, RoundedCornerShape(8.dp))
//                        } else {
//                            Modifier
//                        }
//                    ),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color(0xFFD60000)
//                ),
//                shape = RoundedCornerShape(8.dp),
//                interactionSource = interactionSource
//            ) {
//                Text(
//                    text = "Subscribe for ₹$price",
//                    color = Color.White
//                )
//            }
//
//            // Savings Text
//            Text(
//                text = "Save ${if (period == "Monthly") "0%" else if (period == "Quarterly") "15%" else "25%"}",
//                color = Color(0xFF00FF66),
//                fontSize = 12.sp,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//        }
//    }
//}
//
//@Composable
//fun PlanCard(
//    price: String,
//    logos: List<Int>
//) {
//
//    var isFocused by remember { mutableStateOf(false) }
//    val context = LocalContext.current
//
//    Card(
//        modifier = Modifier
//            .width(250.dp)
//            .onFocusChanged { isFocused = it.isFocused }
//            .focusable()
//            .border(
//                width = if (isFocused) 3.dp else 0.dp,
//                color = if (isFocused) Color.Red else Color.Transparent,
//                shape = RoundedCornerShape(12.dp)
//            ),
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = Color(0xFFEDEDED)
//        )
//    ) {
//
//        Column(
//            modifier = Modifier.padding(14.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//            Text(
//                text = "24 OTTs",
//                fontSize = 16.sp
//            )
//
//            Text(
//                text = "for ₹$price Monthly",
//                fontSize = 14.sp
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(6.dp)
//            ) {
//
//                logos.forEach { logo ->
//
//                    Image(
//                        painter = painterResource(logo),
//                        contentDescription = "",
//                        modifier = Modifier.size(36.dp)
//                    )
//
//                }
//
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Image(
//                painter = painterResource(R.drawable.ott_grid),
//                contentDescription = "",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(120.dp),
//                contentScale = ContentScale.Crop
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Button(
//                onClick = {
//                    // Navigate to HomeScreen
//                    val intent = Intent(context, HomeScreen::class.java)
//                    context.startActivity(intent)
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .focusable(),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color(0xFFD60000)
//                )
//            ) {
//
//                Text(
//                    text = "Subscribe Now for ₹$price",
//                    color = Color.White
//                )
//            }
//        }
//    }
//}
