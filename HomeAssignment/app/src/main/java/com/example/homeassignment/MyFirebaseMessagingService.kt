package com.example.homeassignment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    private lateinit var spm: SharedPreferenceManager
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        spm = SharedPreferenceManager(this)
        val lastUsedDate = spm.getLastUsedDate()
        val currentDate = System.currentTimeMillis()
        val diffOfSeconds = (currentDate - lastUsedDate) / 1000 //Used for testing purposes
        val diffOfDays = (currentDate - lastUsedDate) / (1000 * 60 * 60 * 24)

        if (diffOfSeconds >= 15) {
            //Used for testing purposes
            sendNotification()
        }

        if (diffOfDays >= 5) {
            sendNotification()
        }
    }


    private fun sendNotification() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val requestCode = 0
        val pendingIntent = PendingIntent.getActivity(
            this,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )

        val channelId = "fcm_default_channel"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("FCM Message: We miss you!!")
            .setContentText("It's been 5 days since you've been to the app :C")
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        //& The creation of a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT,
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationId = 0
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}