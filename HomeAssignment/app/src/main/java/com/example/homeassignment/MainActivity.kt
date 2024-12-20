package com.example.homeassignment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.time.LocalDateTime
import java.time.ZoneId

class MainActivity: AppCompatActivity() {

    private lateinit var spm: SharedPreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spm = SharedPreferenceManager(this)

        setUpAndroidClient()
    }//Close On Create

    override fun onPause() {
        super.onPause()

        spm.saveLastUsageDate()

        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, 10, intent, PendingIntent.FLAG_IMMUTABLE)

        val alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager

        //Testing if it works in 15 seconds
        alarmManager.set(AlarmManager.RTC,
            LocalDateTime.now().plusSeconds(15).atZone(ZoneId.systemDefault()).
            toEpochSecond() * 1000, pendingIntent)

        /*
        //Alarm going off after 5 days
        alarmManager.set(AlarmManager.RTC,
            LocalDateTime.now().plusDays(5).atZone(ZoneId.systemDefault()).
            toEpochSecond() * 1000, pendingIntent)
        */

    }//Close on Pause

    private fun setUpAndroidClient() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                println("Fetching FCM registration token failed")
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log
            Log.d("FCMToken", token)
        })
    }
}

