package com.example.homeassignment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.time.ZoneId

class MainActivity: AppCompatActivity() {

    private lateinit var spm: SharedPreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spm = SharedPreferenceManager(this)
        spm.saveLastUsageDate()

        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, 10, intent, PendingIntent.FLAG_IMMUTABLE)

        val alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager


        alarmManager.set(AlarmManager.RTC,
            LocalDateTime.now().plusDays(5).atZone(ZoneId.systemDefault()).
            toEpochSecond() * 1000, pendingIntent)


        /*
        //Testing if it works in 15 seconds
        alarmManager.set(AlarmManager.RTC,
            LocalDateTime.now().plusSeconds(15).atZone(ZoneId.systemDefault()).
            toEpochSecond() * 1000, pendingIntent)

         */
    }
}

