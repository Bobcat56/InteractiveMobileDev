package com.example.homeassignment

import android.content.Context
import java.util.*
class SharedPreferenceManager(private val context: Context) {

    //This way the values can be retrieved through instantiating the class SharedPreferenceManager
    companion object {
        private const val PREFERENCES_FILE_NAME = "AppPreferences"
        private const val LAST_USAGE_DATE_KEY = "LastUsageDate"
    }

    fun saveLastUsageDate() {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val currentDate = Calendar.getInstance().timeInMillis
        editor.putLong(LAST_USAGE_DATE_KEY, currentDate)
        editor.apply()
    }

    fun getLastUsedDate(): Long {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getLong(LAST_USAGE_DATE_KEY, 0)
    }
}
