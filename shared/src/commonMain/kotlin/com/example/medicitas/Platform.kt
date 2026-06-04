package com.example.medicitas

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getSystemLanguage(): String

expect fun makePhoneCall(phoneNumber: String)

expect fun getCurrentTimeMillis(): Long

expect fun addEventToCalendar(title: String, description: String, dateTime: String)

expect fun setSpeakerMaxVolume()

@Composable
expect fun VideoStreamView(modifier: Modifier, isLocal: Boolean)