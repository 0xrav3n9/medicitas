package com.example.medicitas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.Emergency
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavigationBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    onEmergencyClick: () -> Unit,
) {
    val strings = LocalStrings.current
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Bottom Bar with Notch
        BottomAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .graphicsLayer {
                    shape = NotchedShape(38.dp)
                    clip = true
                },
            containerColor = Color.White,
            tonalElevation = 8.dp,
            actions = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left Group: Home & Book
                    Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.SpaceEvenly) {
                        NavBarItem(
                            selected = selectedTab == "Home",
                            onClick = { onTabSelected("Home") },
                            icon = if (selectedTab == "Home") Icons.Filled.Home else Icons.Outlined.Home,
                            label = strings.navHome
                        )
                        NavBarItem(
                            selected = selectedTab == "Book",
                            onClick = { onTabSelected("Book") },
                            icon = if (selectedTab == "Book") Icons.Filled.DateRange else Icons.Outlined.DateRange,
                            label = strings.navBook
                        )
                    }
                    
                    // Center Space for FAB (Emergency Button)
                    Spacer(modifier = Modifier.width(80.dp))
                    
                    // Right Group: Chat & Profile
                    Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.SpaceEvenly) {
                        NavBarItem(
                            selected = selectedTab == "Chat",
                            onClick = { onTabSelected("Chat") },
                            icon = if (selectedTab == "Chat") Icons.AutoMirrored.Filled.Chat else Icons.AutoMirrored.Outlined.Chat,
                            label = strings.navChat
                        )
                        NavBarItem(
                            selected = selectedTab == "Profile",
                            onClick = { onTabSelected("Profile") },
                            icon = if (selectedTab == "Profile") Icons.Filled.Person else Icons.Outlined.Person,
                            label = strings.navProfile
                        )
                    }
                }
            }
        )
        
        // Floating Emergency Button (Red FAB with Label)
        Column(
            modifier = Modifier.offset(y = (-20).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FloatingActionButton(
                onClick = onEmergencyClick,
                containerColor = Color.Red,
                contentColor = Color.White,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Emergency,
                    contentDescription = strings.navEmergencyLabel,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = strings.navEmergencyLabel,
                color = Color.Red,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun NavBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String
) {
    Column(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (selected) Color(0xFF007BFF) else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            color = if (selected) Color(0xFF007BFF) else Color.Gray,
            fontSize = 10.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

/**
 * A custom shape that creates a smooth notched cutout in the top center.
 */
class NotchedShape(private val radius: Dp) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val radiusPx = with(density) { radius.toPx() }
        val path = Path().apply {
            val centerX = size.width / 2
            moveTo(0f, 0f)
            // Draw line to the start of the notch area
            lineTo(centerX - (radiusPx * 1.5f), 0f)
            
            // Create a smooth notched cutout using a cubic bezier or arc
            // Using arcTo for a clean circular cutout
            arcTo(
                rect = Rect(
                    left = centerX - radiusPx,
                    top = -radiusPx,
                    right = centerX + radiusPx,
                    bottom = radiusPx
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )
            
            lineTo(size.width, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}
