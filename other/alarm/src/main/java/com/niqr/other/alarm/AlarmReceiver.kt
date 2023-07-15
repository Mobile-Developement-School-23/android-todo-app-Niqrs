package com.niqr.other.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.niqr.other.alarm.di.AlarmComponentProvider
import com.niqr.other.alarm.di.MainActivityIntent
import com.niqr.other.alarm.utils.ALARM_CHANNEL_ID
import com.niqr.other.alarm.utils.TASK_ID
import com.niqr.other.alarm.utils.goAsync
import com.niqr.tasks.domain.model.Priority
import com.niqr.tasks.domain.repo.TodoItemsRepository
import javax.inject.Inject

class AlarmReceiver: BroadcastReceiver() {
    @Inject
    lateinit var repo: TodoItemsRepository
    @Inject
    @MainActivityIntent
    lateinit var activityIntent: Intent


    override fun onReceive(context: Context, intent: Intent?) = goAsync {
        (context.applicationContext as AlarmComponentProvider)
            .provideAlarmComponent().inject(this@AlarmReceiver)

        val id = intent?.getStringExtra(TASK_ID) ?: return@goAsync
        Log.d("TAG", "ID: $id")
        val item = repo.findItemById(id) ?: return@goAsync
        Log.d("TAG", "ITEM: $item")

        if (item.isDone)
            return@goAsync

        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val icon = when(item.priority) {
            Priority.HIGH -> R.drawable.ic_priority_high_24dp
            Priority.LOW -> R.drawable.ic_priority_low_24dp
            else -> R.drawable.ic_remove_24dp
        }

        val notification = NotificationCompat.Builder(context, ALARM_CHANNEL_ID)
            .setSmallIcon(icon)
            .setContentTitle(context.getString(R.string.unfinished_task))
            .setContentText(item.description)
            .setContentIntent(activityPendingIntent)
//            .setContentIntent(activityPendingIntent)
//            .addAction(
//                R.drawable.baseline_baby_changing_station_24,
//                "Increment",
//                incrementIntent
//            )
            .build()

        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .notify(item.id.hashCode(), notification)
    }
}