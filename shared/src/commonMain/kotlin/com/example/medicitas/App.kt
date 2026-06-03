package com.example.medicitas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val systemLang = remember { getSystemLanguage() }
    var currentLanguage by remember { 
        mutableStateOf(if (systemLang.startsWith("es")) AppLanguage.ESP else AppLanguage.ENG) 
    }
    
    val strings = when (currentLanguage) {
        AppLanguage.ENG -> EnglishStrings
        AppLanguage.ESP -> SpanishStrings
    }

    var currentTab by remember { mutableStateOf("Home") }
    var currentScreen by remember { mutableStateOf("Main") }
    var showBottomSheet by remember { mutableStateOf(false) }

    CompositionLocalProvider(LocalStrings provides strings) {
        MaterialTheme {
            Scaffold(
                bottomBar = {
                    if (currentScreen == "Main") {
                        BottomNavigationBar(
                            selectedTab = currentTab,
                            onTabSelected = { currentTab = it }
                        )
                    }
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    if (currentScreen == "Main") {
                        when (currentTab) {
                            "Home" -> HomeScreen(
                                onSeeAllDoctors = { currentScreen = "PopularDoctorsGrid" },
                                onBookDoctor = { showBottomSheet = true },
                                currentLanguage = currentLanguage,
                                onLanguageChange = { currentLanguage = it }
                            )
                            "Book" -> AppointmentScreen(
                                onAppointmentClick = { currentScreen = "AppointmentDetail" }
                            )
                            "My doctor" -> PlaceholderScreen(strings.navMyDoctor)
                            "Chat" -> PlaceholderScreen(strings.navChat)
                            "Profile" -> PlaceholderScreen(strings.navProfile)
                            else -> HomeScreen(
                                onSeeAllDoctors = { currentScreen = "PopularDoctorsGrid" },
                                onBookDoctor = { showBottomSheet = true },
                                currentLanguage = currentLanguage,
                                onLanguageChange = { currentLanguage = it }
                            )
                        }
                    } else if (currentScreen == "ReviewAndBook") {
                        ReviewAndBookScreen(onBack = { currentScreen = "Main" })
                    } else if (currentScreen == "PopularDoctorsGrid") {
                        PopularDoctorsGridScreen(
                            onBack = { currentScreen = "Main" },
                            onDoctorClick = { showBottomSheet = true }
                        )
                    } else if (currentScreen == "AppointmentDetail") {
                        AppointmentDetailScreen(
                            onBack = { currentScreen = "Main" },
                            onChat = {
                                currentScreen = "Main"
                                currentTab = "Chat"
                            }
                        )
                    }

                    if (showBottomSheet) {
                        BookAppointmentBottomSheet(
                            onDismiss = { showBottomSheet = false },
                            onMakeAppointment = {
                                showBottomSheet = false
                                currentScreen = "ReviewAndBook"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceholderScreen(title: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = title, style = MaterialTheme.typography.headlineMedium)
    }
}
