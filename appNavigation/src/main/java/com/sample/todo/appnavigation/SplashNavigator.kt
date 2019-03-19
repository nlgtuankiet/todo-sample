package com.sample.todo.appnavigation

import android.content.Intent
import androidx.navigation.ActivityNavigator

object SplashNavigator {
    fun toMainActivity(componentActivity: android.app.Activity) {
        if (componentActivity.isAssisableTo(Activity.Splash) && Activity.Main.isAvalable()) {
            ActivityNavigator(componentActivity).apply {
                val des = createDestination().apply {
                    intent = Intent(componentActivity, Activity.Main.javaClass())
                }
                navigate(des, componentActivity.intent.extras, null, null)
            }
            componentActivity.finish()
        }
    }
}

object MainNavigator {
    fun toSettingsActivity(componentActivity: android.app.Activity): Boolean {
        return if (componentActivity.isAssisableTo(Activity.Main) && Activity.Settings.isAvalable()) {
            ActivityNavigator(componentActivity).apply {
                val des = createDestination().apply {
                    intent = Intent(componentActivity, Activity.Settings.javaClass())
                }
                navigate(des, componentActivity.intent.extras, null, null)
            }
            true
        } else {
            false
        }
    }
}

private fun android.app.Activity.isAssisableTo(activity: Activity): Boolean {
    return try {
        val activityClass = Class.forName(activity.fullName)
        activityClass.isAssignableFrom(this::class.java)
    } catch (cne: ClassNotFoundException) {
        false
    }
}