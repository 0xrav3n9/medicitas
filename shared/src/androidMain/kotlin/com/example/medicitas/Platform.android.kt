package com.example.medicitas

import android.os.Build
import java.util.Locale
import android.content.Intent
import android.net.Uri
import android.content.Context

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getSystemLanguage(): String = Locale.getDefault().language

actual fun getCurrentTimeMillis(): Long = System.currentTimeMillis()

private var androidContext: Context? = null

fun setAndroidContext(context: Context) {
    androidContext = context
}

actual fun makePhoneCall(phoneNumber: String) {
    androidContext?.let { context ->
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}