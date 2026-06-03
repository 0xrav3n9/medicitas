package com.example.medicitas

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavigationBar(selectedTab: String, onTabSelected: (String) -> Unit) {
    val strings = LocalStrings.current
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color(0xFF007BFF)
    ) {
        NavigationBarItem(
            selected = selectedTab == "Home",
            onClick = { onTabSelected("Home") },
            icon = { 
                Icon(
                    imageVector = if (selectedTab == "Home") Icons.Filled.Home else Icons.Outlined.Home, 
                    contentDescription = strings.navHome
                ) 
            },
            label = { Text(strings.navHome, fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF007BFF),
                selectedTextColor = Color(0xFF007BFF),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color(0xFFE6F0FF)
            )
        )
        NavigationBarItem(
            selected = selectedTab == "Book",
            onClick = { onTabSelected("Book") },
            icon = { 
                Icon(
                    imageVector = if (selectedTab == "Book") Icons.Filled.DateRange else Icons.Outlined.DateRange, 
                    contentDescription = strings.navBook
                ) 
            },
            label = { Text(strings.navBook, fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF007BFF),
                selectedTextColor = Color(0xFF007BFF),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color(0xFFE6F0FF)
            )
        )
        NavigationBarItem(
            selected = selectedTab == "My doctor",
            onClick = { onTabSelected("My doctor") },
            icon = { 
                Icon(
                    imageVector = if (selectedTab == "My doctor") Icons.Filled.MedicalServices else Icons.Outlined.MedicalServices, 
                    contentDescription = strings.navMyDoctor
                ) 
            },
            label = { Text(strings.navMyDoctor, fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF007BFF),
                selectedTextColor = Color(0xFF007BFF),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color(0xFFE6F0FF)
            )
        )
        NavigationBarItem(
            selected = selectedTab == "Chat",
            onClick = { onTabSelected("Chat") },
            icon = { 
                Icon(
                    imageVector = if (selectedTab == "Chat") Icons.AutoMirrored.Filled.Chat else Icons.AutoMirrored.Outlined.Chat,
                    contentDescription = strings.navChat
                ) 
            },
            label = { Text(strings.navChat, fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF007BFF),
                selectedTextColor = Color(0xFF007BFF),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color(0xFFE6F0FF)
            )
        )
        NavigationBarItem(
            selected = selectedTab == "Profile",
            onClick = { onTabSelected("Profile") },
            icon = { 
                Icon(
                    imageVector = if (selectedTab == "Profile") Icons.Filled.AccountCircle else Icons.Outlined.AccountCircle, 
                    contentDescription = strings.navProfile
                ) 
            },
            label = { Text(strings.navProfile, fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF007BFF),
                selectedTextColor = Color(0xFF007BFF),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color(0xFFE6F0FF)
            )
        )
    }
}
