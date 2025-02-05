package com.example.ebot.common

import android.content.Context
import android.content.SharedPreferences

object Utils {
    fun saveData(context: Context, key: String, value: Any?) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("PropertyZoneSP", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
        }
        editor.apply()
    }

    fun getData(context: Context, key: String, defaultValue: Any): Any? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("PropertyZoneSP", Context.MODE_PRIVATE)
        return when (defaultValue) {
            is String -> sharedPreferences.getString(key, defaultValue)
            is Int -> sharedPreferences.getInt(key, defaultValue)
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue)
            is Float -> sharedPreferences.getFloat(key, defaultValue)
            is Long -> sharedPreferences.getLong(key, defaultValue)
            else -> null
        }
    }
}