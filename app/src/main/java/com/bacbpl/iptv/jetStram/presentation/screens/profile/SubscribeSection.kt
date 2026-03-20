package com.bacbpl.iptv.jetStram.presentation.screens.profile

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.whenStateAtLeast
import com.bacbpl.iptv.JetStreamActivity
import com.bacbpl.iptv.R
import com.bacbpl.iptv.data.SharedPrefManager
import com.bacbpl.iptv.ui.activities.subscribescreen.BannerRow
import com.bacbpl.iptv.ui.activities.subscribescreen.DynamicChoosePlanSection
import com.bacbpl.iptv.ui.activities.subscribescreen.TopSection
import com.bacbpl.iptv.ui.activities.subscribescreen.components.DynamicPlanCard
import com.bacbpl.iptv.ui.activities.subscribescreen.data.Plan
import com.bacbpl.iptv.ui.activities.subscribescreen.data.repositories.PlanRepository
import com.bacbpl.iptv.ui.activities.subscribescreen.viewmodels.PlanViewModel
import com.bacbpl.iptv.utils.ToastUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Main SubscribeSection composable that matches the signature from profile package
 */
@Composable
fun SubscribeSection(
    isSubtitlesChecked: Boolean,
    onSubtitleCheckChange: (isChecked: Boolean) -> Unit
) {
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
    val sharedPrefManager = remember { SharedPrefManager(context) }
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    // Use SnackbarHostState instead of Toasts for better lifecycle management
    val snackbarHostState = remember { SnackbarHostState() }

    // State for subtitle checkbox
    var localIsSubtitlesChecked by remember { mutableStateOf(isSubtitlesChecked) }

    // Handle navigation to Profile (when subscriber doesn't exist)
    LaunchedEffect(navigateToProfile) {
        if (navigateToProfile) {
            delay(500) // Small delay to show error message

            // Check if lifecycle is at least STARTED before starting activity
            if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                val intent = Intent(context, JetStreamActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    putExtra("navigate_to_profile", true)
                }
                context.startActivity(intent)
                planViewModel.clearNavigateToProfile()
            }
        }
    }

    // Handle subscription response safely using ToastUtils
    LaunchedEffect(subscribeResponse) {
        subscribeResponse?.let { response ->
            val toastMessage = if (response.success || response.status == true) {
                "✅ Subscription successful!"
            } else {
                "❌ ${response.message}"
            }

            // Use ToastUtils for safe toast display
            ToastUtils.showSafeToast(context, toastMessage)
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

                    // Remove country code (+91)
                    val cleanMobile = mobile?.replace("+91", "")?.trim()
                    println("cleanMobile_Print: $cleanMobile")

                    if (cleanMobile.isNullOrEmpty()) {
                        // Show toast safely using ToastUtils
                        coroutineScope.launch {
                            ToastUtils.showSafeToast(
                                context,
                                "User mobile number not found. Please login again."
                            )
                        }
                        return@DynamicChoosePlanSection
                    }

                    // Subscribe to plan
                    planViewModel.subscribeToPlan(cleanMobile, plan.id, context)
                }
            )
        }
        item { Spacer(modifier = Modifier.height(10.dp)) }

        // Subtitle Section
        item {
            SubtitleSection(
                isSubtitlesChecked = localIsSubtitlesChecked,
                onSubtitleCheckChange = {
                    localIsSubtitlesChecked = it
                    onSubtitleCheckChange(it)
                }
            )
        }
        item { Spacer(modifier = Modifier.height(10.dp)) }
    }

    // Show error message safely using ToastUtils
    errorMessage?.let { message ->
        LaunchedEffect(message) {
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

/**
 * Subtitle Section component
 */
@Composable
fun SubtitleSection(
    isSubtitlesChecked: Boolean,
    onSubtitleCheckChange: (isChecked: Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1E1E1E), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Subtitle Settings",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSubtitleCheckChange(!isSubtitlesChecked) }
                .padding(vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        if (isSubtitlesChecked) Color.Red else Color.Gray,
                        RoundedCornerShape(4.dp)
                    )
                    .then(
                        if (isSubtitlesChecked) {
                            Modifier.border(2.dp, Color.White, RoundedCornerShape(4.dp))
                        } else {
                            Modifier
                        }
                    )
            ) {
                if (isSubtitlesChecked) {
                    Text(
                        text = "✓",
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Enable Subtitles",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}