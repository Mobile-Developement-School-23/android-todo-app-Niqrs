package com.niqr.other.work

import android.app.NotificationChannel
import android.app.NotificationManager
import com.niqr.other.work.utils.SYNCHRONIZATION_CHANNEL
import com.niqr.other.work.utils.SYNCHRONIZATION_CHANNEL_ID

/**
 * [NotificationChannel] for showing synchronization
 * work notification on old Android devices
 *
 * (Shows notifications only on Android 9 and lower)
 */
class SynchronizationNotificationChannel {
    val channel = NotificationChannel(
        SYNCHRONIZATION_CHANNEL_ID,
        SYNCHRONIZATION_CHANNEL,
        NotificationManager.IMPORTANCE_NONE
    )
}
