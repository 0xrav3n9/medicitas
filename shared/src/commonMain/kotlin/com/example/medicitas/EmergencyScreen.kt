package com.example.medicitas

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
@Preview
fun EmergencyScreen(onBack: () -> Unit = {}, onEmergencySuccess: () -> Unit = {}) {
    val strings = LocalStrings.current
    
    // Logic for long press (3 seconds)
    var isPressing by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }
    
    // Launch timer logic only when pressing
    LaunchedEffect(isPressing) {
        if (isPressing) {
            val startTime = getCurrentTimeMillis()
            while (isPressing && progress < 1f) {
                val elapsed = getCurrentTimeMillis() - startTime
                progress = (elapsed / 3000f).coerceIn(0f, 1f)
                if (progress >= 1f) {
                    // Success: Navigate to Video Call
                    onEmergencySuccess()
                    isPressing = false
                }
                delay(16) // ~60fps updates
            }
        } else {
            progress = 0f
        }
    }

    // Radar animations (Infinite and safe)
    val infiniteTransition = rememberInfiniteTransition(label = "RadarTransition")
    val rippleScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 2.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "RippleScale"
    )
    val rippleAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "RippleAlpha"
    )

    // Pulse effect when pressing
    val pressingScale by animateFloatAsState(
        targetValue = if (isPressing) 1.25f else 1.0f,
        animationSpec = tween(300, easing = FastOutSlowInEasing),
        label = "PressingScale"
    )

    // Main UI container (Solid background for immediate rendering)
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Section
            Column(
                modifier = Modifier.padding(top = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = strings.emergencyQuestion,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B34),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = strings.emergencySubtitle,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }

            // Central Interaction Area
            Box(
                modifier = Modifier.size(320.dp),
                contentAlignment = Alignment.Center
            ) {
                // Background Waves
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .scale(rippleScale)
                        .clip(CircleShape)
                        .background(Color.Red.copy(alpha = rippleAlpha))
                )
                
                // Second Wave (Shifted)
                val rippleScale2 by infiniteTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = 2.2f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2000, delayMillis = 1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    ),
                    label = "RippleScale2"
                )
                val rippleAlpha2 by infiniteTransition.animateFloat(
                    initialValue = 0.6f,
                    targetValue = 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2000, delayMillis = 1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    ),
                    label = "RippleAlpha2"
                )
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .scale(rippleScale2)
                        .clip(CircleShape)
                        .background(Color.Red.copy(alpha = rippleAlpha2))
                )

                // The "HELP ME!" Button
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .scale(pressingScale)
                        .clip(CircleShape)
                        .background(Color.Red)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    isPressing = true
                                    try {
                                        awaitRelease()
                                    } finally {
                                        isPressing = false
                                    }
                                }
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = strings.helpMe,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        )
                        if (isPressing) {
                            Spacer(modifier = Modifier.height(12.dp))
                            CircularProgressIndicator(
                                progress = { progress },
                                color = Color.White,
                                strokeWidth = 4.dp,
                                modifier = Modifier.size(36.dp),
                                trackColor = Color.White.copy(alpha = 0.3f)
                            )
                        }
                    }
                }
            }

            // Cancel/Back Action
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
            ) {
                Text(
                    text = strings.cancel,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}
