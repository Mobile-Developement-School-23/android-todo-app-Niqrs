package com.niqr.other.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import com.niqr.other.alarm.utils.ALARM_CHANNEL
import com.niqr.other.alarm.utils.ALARM_CHANNEL_ID

/**
 * [NotificationChannel] for showing alarms
 */
class AlarmNotificationChannel {
    val channel = NotificationChannel(
        ALARM_CHANNEL_ID,
        ALARM_CHANNEL,
        NotificationManager.IMPORTANCE_HIGH
    )
}
