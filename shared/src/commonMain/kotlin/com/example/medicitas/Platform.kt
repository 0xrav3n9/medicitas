package com.example.medicitas

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getSystemLanguage(): String

expect fun makePhoneCall(phoneNumber: String)

expect fun getCurrentTimeMillis(): Long