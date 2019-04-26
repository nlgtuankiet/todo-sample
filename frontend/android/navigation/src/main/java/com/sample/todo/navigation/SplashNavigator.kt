package com.sample.todo.navigation

import android.content.Intent
import androidx.navigation.ActivityNavigator

object SplashNavigator {
    fun toMainActivity(componentActivity: android.app.Activity) {
        if (componentActivity.isAssisableTo(Activity.Splash) && Activity.Main.getIsAvailable()) {
            ActivityNavigator(componentActivity).apply {
                val des = createDestination().apply {
                    intent = Intent(componentActivity, Activity.Main.getJavaClass())
                }
                navigate(des, componentActivity.intent.extras, null, null)
            }
            componentActivity.finish()
        }
    }
}

object MainNavigator {
    fun toSettingsActivity(componentActivity: android.app.Activity): Boolean {
        return if (componentActivity.isAssisableTo(Activity.Main) && Activity.Settings.getIsAvailable()) {
            ActivityNavigator(componentActivity).apply {
                val des = createDestination().apply {
                    intent = Intent(componentActivity, Activity.Settings.getJavaClass())
                }
                navigate(des, componentActivity.intent.extras, null, null)
            }
            true
        } else {
            false
        }
    }
    fun toSeedDatabaseActivity(componentActivity: android.app.Activity): Boolean {
        return if (componentActivity.isAssisableTo(Activity.Main) && Activity.SeedDatabase.getIsAvailable()) {
            ActivityNavigator(componentActivity).apply {
                val des = createDestination().apply {
                    intent = Intent(componentActivity, Activity.SeedDatabase.getJavaClass())
                }
                navigate(des, componentActivity.intent.extras, null, null)
            }
            true
        } else {
            false
        }
    }

    fun toLeakActivity(componentActivity: android.app.Activity): Boolean {
        return if (componentActivity.isAssisableTo(Activity.Main) && Activity.Leak.getIsAvailable()) {
            ActivityNavigator(componentActivity).apply {
                val des = createDestination().apply {
                    intent = Intent(componentActivity, Activity.Leak.getJavaClass())
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
