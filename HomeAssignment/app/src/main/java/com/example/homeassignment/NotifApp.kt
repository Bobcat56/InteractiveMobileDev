package com.example.homeassignment

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class NotifApp: Application() {

    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            getString(R.string.channelComeBack),
            "Come Back",
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}