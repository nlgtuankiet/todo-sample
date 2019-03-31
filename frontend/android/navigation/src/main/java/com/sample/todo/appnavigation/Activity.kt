package com.sample.todo.navigation

enum class Activity(val fullName: String) {
    Splash("com.sample.todo.splash.SplashActivity"),
    Main("com.sample.todo.main.MainActivity"),
    Settings("com.sample.todo.ui.SettingsActivity");
    fun isAvalable(): Boolean {
        return try {
            Class.forName(fullName)
            true
        } catch (cne: ClassNotFoundException) {
            false
        }
    }
    fun javaClass(): Class<*> {
        return Class.forName(fullName)
    }
}