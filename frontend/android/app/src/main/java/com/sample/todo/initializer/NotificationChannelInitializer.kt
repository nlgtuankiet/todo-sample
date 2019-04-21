package com.sample.todo.initializer

import android.app.Application
import android.app.NotificationChannel
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import com.sample.todo.base.notification.NotificationChannelInformation
import javax.inject.Inject

class NotificationChannelInitializer @Inject constructor(
    private val notificationManager: NotificationManagerCompat
) : Initializer {

    override fun initialize(application: Application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannelInformation.values().forEach {
                val name = application.getString(it.title)
                val descriptionText = application.getString(it.description)
                val channel = NotificationChannel(
                    it.ordinal.toString(),
                    name,
                    it.importance
                ).apply {
                    description = descriptionText
                }
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}
