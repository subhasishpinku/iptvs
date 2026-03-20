 package com.bacbpl.iptv.ui.activities

    import androidx.compose.animation.core.*
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.alpha
    import androidx.compose.ui.draw.scale
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.unit.dp
    import androidx.constraintlayout.compose.ConstraintLayout
    import com.bacbpl.iptv.R
    import kotlinx.coroutines.delay
    import coil.compose.AsyncImage
    import kotlinx.coroutines.launch

    @Composable
    fun SplashScreen(
        onSplashFinished: () -> Unit
    ) {
        val context = LocalContext.current
        var startExit by remember { mutableStateOf(false) }
        var splashFinished by remember { mutableStateOf(false) }

        // Animations
        val mainLogoAlpha = remember { Animatable(0f) }
        val mainLogoScale = remember { Animatable(0.2f) }
        val bottomLogoAlpha = remember { Animatable(0f) }
        val bottomLogoScale = remember { Animatable(0.8f) }
        val backgroundAlpha = remember { Animatable(0.8f) }

        LaunchedEffect(Unit) {
            // Main logo animation sequence
            launch {
                mainLogoAlpha.animateTo(
                    1f,
                    animationSpec = tween(800, easing = FastOutSlowInEasing)
                )
            }

            launch {
                mainLogoScale.animateTo(
                    1.2f,
                    animationSpec = tween(1000, easing = FastOutSlowInEasing)
                )
                mainLogoScale.animateTo(
                    1.0f,
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            }

            // Bottom logo animation with delay
            launch {
                delay(600)
                bottomLogoAlpha.animateTo(
                    1f,
                    animationSpec = tween(1200, easing = LinearOutSlowInEasing)
                )
                bottomLogoScale.animateTo(
                    1f,
                    animationSpec = tween(1200, easing = LinearOutSlowInEasing)
                )
            }

            // Background pulse animation
            launch {
                while (!startExit) {
                    backgroundAlpha.animateTo(
                        1f,
                        animationSpec = tween(3000, easing = FastOutSlowInEasing)
                    )
                    backgroundAlpha.animateTo(
                        0.8f,
                        animationSpec = tween(3000, easing = FastOutSlowInEasing)
                    )
                }
            }

            // Floating animation for main logo (fixed this part)
            // Floating animation for main logo - Simpler version
            launch {
                delay(1800)
                while (!startExit) {
                    // Animate to 0.5 and back to 1.0 repeatedly
                    mainLogoAlpha.animateTo(
                        0.5f,
                        animationSpec = tween(2000, easing = FastOutSlowInEasing)
                    )
                    mainLogoAlpha.animateTo(
                        1f,
                        animationSpec = tween(2000, easing = FastOutSlowInEasing)
                    )
                }
            }

            // Delay for splash duration
            delay(4000)
            startExit = true

            // Exit animation
            mainLogoAlpha.animateTo(
                0f,
                animationSpec = tween(400, easing = FastOutSlowInEasing)
            )
            bottomLogoAlpha.animateTo(
                0f,
                animationSpec = tween(400, easing = FastOutSlowInEasing)
            )
            backgroundAlpha.animateTo(
                0f,
                animationSpec = tween(400, easing = FastOutSlowInEasing)
            )

            splashFinished = true
            onSplashFinished()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .alpha(backgroundAlpha.value)
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (logo, bottomLogo) = createRefs()

                // Main Logo
                AsyncImage(
                    model = R.drawable.logos,
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .constrainAs(logo) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .alpha(mainLogoAlpha.value)
                        .scale(mainLogoScale.value)
                )

                // Bottom Logo
//                AsyncImage(
//                    model = R.drawable.logo2,
//                    contentDescription = "Powered By",
//                    modifier = Modifier
//                        .constrainAs(bottomLogo) {
//                            bottom.linkTo(parent.bottom, margin = 40.dp)
//                            start.linkTo(parent.start)
//                            end.linkTo(parent.end)
//                        }
//                        .alpha(bottomLogoAlpha.value)
//                        .scale(bottomLogoScale.value)
//                )
            }
        }
    }