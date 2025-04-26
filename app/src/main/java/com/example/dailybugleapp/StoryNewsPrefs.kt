package com.example.dailybugleapp

import android.content.Context


object StoryNewsPrefs {

    private const val PREFS_NAME = "DAILY_NEWS_PREFS"
    private const val KEY_SESSION_ACTIVE = "SESSION_ENABLED"
    private const val KEY_READER_ALIAS = "READER_ALIAS"
    private const val KEY_READER_EMAIL = "READER_EMAIL"

    fun setSessionStatus(context: Context, isActive: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_SESSION_ACTIVE, isActive).apply()
    }

    fun isSessionActive(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_SESSION_ACTIVE, false)
    }

    fun storeReaderAlias(context: Context, alias: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_READER_ALIAS, alias).apply()
    }

    fun getReaderAlias(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_READER_ALIAS, "") ?: ""
    }

    fun saveReaderEmail(context: Context, email: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_READER_EMAIL, email).apply()
    }

    fun getReaderEmail(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_READER_EMAIL, "") ?: ""
    }
}
