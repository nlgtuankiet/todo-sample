package com.sample.todo.main.navigation

import android.app.Activity
import com.sample.todo.base.ActivityNavigator
import com.sample.todo.navigation.IntentProvider

class MainNavigator constructor(
    private val intentProvider: IntentProvider,
    private val activity: Activity
) : ActivityNavigator(activity) {
    fun toSettingsActivity(): Boolean {
        return intentProvider.settingsActivity(activity).go()
    }
    fun toSeedDatabaseActivity(): Boolean {
        return intentProvider.seedDatabaseActivity(activity).go()
    }

    fun toLeakActivity(): Boolean {
        return intentProvider.leakActivity(activity).go()
    }

    fun toDataImplementationActivity(): Boolean {
        return intentProvider.dataImplementationActivity(activity).go()
    }
}
