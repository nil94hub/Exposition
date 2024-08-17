package com.niladri.lloydsdemo

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import java.io.File
@HiltAndroidApp
class LloydsApplication: Application() {
    private val sharedPrefFile = "sharedPref"
    override fun onCreate() {
        super.onCreate()
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
    }

    fun setToastEnabled(toast: Boolean, context: Context) {
        val sharedPref =
            context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putBoolean(context.resources.getString(R.string.toast_Enabled), toast)
            apply()
        }
    }

    fun getToastEnabled(context: Context): Boolean {
        val sharedPref =
            context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE) ?: return true
        return sharedPref.getBoolean(context.resources.getString(R.string.toast_Enabled), false)
    }
}