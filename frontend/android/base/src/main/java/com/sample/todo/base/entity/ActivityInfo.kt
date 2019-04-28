package com.sample.todo.base.entity

import android.content.Context
import android.content.Intent

enum class ActivityInfo(val fullName: String) {
    Splash("com.sample.todo.splash.SplashActivity"),
    Main("com.sample.todo.main.MainActivity"),
    Settings("com.sample.todo.dynamic.settings.ui.SettingsActivity"),
    SeedDatabase("com.sample.todo.dynamic.seeddatabase.ui.SeedDatabaseActivity"),
    Leak("com.sample.todo.dynamic.leak.LeakActivity"),
    DataImplementation("com.sample.todo.dynamic.dataimplementation.ui.DataImplementationActivity");

    private val isAvailable: Boolean
        get() =
            try {
                Class.forName(fullName)
                true
            } catch (cne: ClassNotFoundException) {
                false
            }

    private val javaClass: Class<*>
        get() = Class.forName(fullName)

    fun resolveIntent(context: Context): Intent? {
        return if (isAvailable) {
            Intent(context, javaClass)
        } else {
            null
        }
    }
}
