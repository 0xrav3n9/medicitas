package com.example.medicitas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun EmergencyVideoCallScreen(onEndCall: () -> Unit) {
    val strings = LocalStrings.current
    var isMuted by remember { mutableStateOf(false) }
    var isFrontCamera by remember { mutableStateOf(true) }
    
    // Auto-enable speaker and async init
    LaunchedEffect(Unit) {
        setSpeakerMaxVolume()
        // Simulate WebRTC/Agora connecting
        delay(1000)
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // 1. Remote Doctor Video (Full Screen)
        VideoStreamView(modifier = Modifier.fillMaxSize(), isLocal = false)
        
        // Doctor Name Overlay
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = strings.emergencyDoctor,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "00:45", // Simulated duration
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }

        // 2. Patient Local Video (Floating bottom left)
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 24.dp, bottom = 120.dp)
                .size(120.dp, 180.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, Color.White, RoundedCornerShape(16.dp))
        ) {
            VideoStreamView(modifier = Modifier.fillMaxSize(), isLocal = true)
        }

        // 3. Floating Action Bar (Bottom Center)
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .wrapContentSize(),
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            tonalElevation = 8.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Button 1: Mute
                CallActionButton(
                    icon = if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                    containerColor = if (isMuted) Color.LightGray else Color(0xFFF0F0F0),
                    contentColor = if (isMuted) Color.White else Color.Black,
                    onClick = { isMuted = !isMuted },
                    description = strings.mute
                )
                
                // Button 2: Switch Camera (Strictly between front/back)
                CallActionButton(
                    icon = Icons.Default.FlipCameraIos,
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black,
                    onClick = { isFrontCamera = !isFrontCamera },
                    description = strings.switchCamera
                )
                
                // Button 3: Quick Chat
                CallActionButton(
                    icon = Icons.AutoMirrored.Filled.Chat,
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black,
                    onClick = { /* Open Quick Chat */ },
                    description = strings.chat
                )
                
                // Button 4: End Call (Red with X)
                CallActionButton(
                    icon = Icons.Default.Close,
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    onClick = onEndCall,
                    description = strings.endCall
                )
            }
        }
    }
}

@Composable
fun CallActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
    description: String
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(52.dp)
            .clip(CircleShape)
            .background(containerColor)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )
    }
}
