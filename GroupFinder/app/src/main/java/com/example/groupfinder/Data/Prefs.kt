package com.example.groupfinder.Data

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {
    val PREFS_FILENAME = "com.example.groupfinder.prefs"
    val USER_RA = "user_ra"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var userRa: Int
        get() = prefs.getInt(USER_RA, -1)
        set(value) = prefs.edit().putInt(USER_RA, value).apply()

}