package com.example.medicitas

import android.os.Build
import java.util.Locale
import android.content.Intent
import android.net.Uri
import android.content.Context
import android.media.AudioManager
import android.view.View
import android.widget.FrameLayout
import android.provider.CalendarContract
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getSystemLanguage(): String = Locale.getDefault().language

actual fun getCurrentTimeMillis(): Long = System.currentTimeMillis()

actual fun addEventToCalendar(title: String, description: String, dateTime: String) {
    androidContext?.let { context ->
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.Events.DESCRIPTION, description)
            putExtra(CalendarContract.Events.ALL_DAY, false)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}

actual fun setSpeakerMaxVolume() {
    androidContext?.let { context ->
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.mode = AudioManager.MODE_IN_COMMUNICATION
        audioManager.isSpeakerphoneOn = true
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL)
        audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, maxVolume, 0)
    }
}

@Composable
actual fun VideoStreamView(modifier: Modifier, isLocal: Boolean) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            FrameLayout(context).apply {
                setBackgroundColor(if (isLocal) android.graphics.Color.DKGRAY else android.graphics.Color.BLACK)
                // Aquí se integraría la vista nativa de WebRTC/Agora
            }
        }
    )
}

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