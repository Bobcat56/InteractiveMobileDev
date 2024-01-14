package com.example.homeassignment

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat

class AlarmReceiver: BroadcastReceiver() {

    private lateinit var spm: SharedPreferenceManager

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            spm = SharedPreferenceManager(context)
            val lastUsedDate = spm.getLastUsedDate()
            println(lastUsedDate)
            val currentDate = System.currentTimeMillis()
            println(currentDate)
            val diffOfDays = (currentDate - lastUsedDate) / (1000 * 60 * 60 * 24)
            println(diffOfDays)

            /*
            Toast.makeText(context, "",Toast.LENGTH_SHORT).show()
            showNotification(context)
            */

            if (diffOfDays >= 5) {
                Toast.makeText(context, "",Toast.LENGTH_SHORT).show()
                showNotification(context)
            }
        }
    }

    private fun showNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = Notification.Builder(context, context.getString(R.string.channelComeBack))
            .setSmallIcon(R.drawable.alarm_icom)
            .setContentTitle("We miss you")
            .setContentText("It's been 5 days since you've been to the app :C")
            .setCategory(NotificationCompat.CATEGORY_RECOMMENDATION).build()

        notificationManager.notify(10, notification)
    }
}