package com.example.homeassignment

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class AlarmReceiver: BroadcastReceiver() {

    private lateinit var spm: SharedPreferenceManager
    private lateinit var mfms: MyFirebaseMessagingService

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            spm = SharedPreferenceManager(context)
            val lastUsedDate = spm.getLastUsedDate()
            val currentDate = System.currentTimeMillis()
            val diffOfSeconds = (currentDate - lastUsedDate) / 1000 //Used for testing purposes
            val diffOfDays = (currentDate - lastUsedDate) / (1000 * 60 * 60 * 24)

            if (diffOfSeconds >= 15) {
                //Used for testing purposes
                showNotification(context)
                mfms.sendNotification()

            }

            if (diffOfDays >= 5) {
                showNotification(context)
                mfms.sendNotification()
            }
        }
    }

    private fun showNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //Intent to start MainActivity
        val intentToStartActivity = Intent(context, MainActivity::class.java)
        intentToStartActivity.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        // PendingIntent will start main activity
        val pendingIntent = PendingIntent.getActivity(context, 0, intentToStartActivity,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notification = Notification.Builder(context, context.getString(R.string.channelComeBack))
            .setSmallIcon(R.drawable.alarm_icom)
            .setContentTitle("We miss you")
            .setContentText("It's been 5 days since you've been to the app :C")
            .setCategory(NotificationCompat.CATEGORY_RECOMMENDATION)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(10, notification)
    }
}