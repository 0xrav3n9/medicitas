package com.example.medicitas

import platform.UIKit.UIDevice
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import platform.UIKit.UIView
import platform.UIKit.backgroundColor
import platform.UIKit.UIColor
import platform.AVFoundation.*
import platform.EventKit.*
import kotlinx.cinterop.ExperimentalForeignApi

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun getSystemLanguage(): String = NSLocale.currentLocale.languageCode ?: "en"

actual fun getCurrentTimeMillis(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()

actual fun addEventToCalendar(title: String, description: String, dateTime: String) {
    val eventStore = EKEventStore()
    eventStore.requestAccessToEntityType(EKEntityTypeEvent) { granted, error ->
        if (granted) {
            val event = EKEvent.eventWithEventStore(eventStore)
            event.title = title
            event.notes = description
            event.startDate = NSDate() // simplified for mock
            event.endDate = NSDate().dateByAddingTimeInterval(3600.0)
            event.calendar = eventStore.defaultCalendarForNewEvents
            eventStore.saveEvent(event, EKSpanThisEvent, error = null)
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
actual fun setSpeakerMaxVolume() {
    val session = AVAudioSession.sharedInstance()
    session.setCategory(AVAudioSessionCategoryPlayAndRecord, error = null)
    session.overrideOutputAudioPort(AVAudioSessionPortOverrideSpeaker, error = null)
    session.setActive(true, error = null)
}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun VideoStreamView(modifier: Modifier, isLocal: Boolean) {
    UIKitView(
        factory = {
            UIView().apply {
                backgroundColor = if (isLocal) UIColor.darkGrayColor else UIColor.blackColor
            }
        },
        modifier = modifier
    )
}

actual fun makePhoneCall(phoneNumber: String) {
    val url = NSURL(string = "tel:$phoneNumber")
    if (UIApplication.sharedApplication.canOpenURL(url)) {
        UIApplication.sharedApplication.openURL(url)
    }
}