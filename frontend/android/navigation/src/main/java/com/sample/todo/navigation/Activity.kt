package com.sample.todo.navigation

enum class Activity(val fullName: String) {
    Splash("com.sample.todo.splash.SplashActivity"),
    Main("com.sample.todo.main.MainActivity"),
    Settings("com.sample.todo.dynamic.settings.ui.SettingsActivity"),
    SeedDatabase("com.sample.todo.dynamic.seeddatabase.ui.SeedDatabaseActivity");
    fun getIsAvailable(): Boolean {
        return try {
            Class.forName(fullName)
            true
        } catch (cne: ClassNotFoundException) {
            false
        }
    }
    fun getJavaClass(): Class<*> {
        return Class.forName(fullName)
    }
}