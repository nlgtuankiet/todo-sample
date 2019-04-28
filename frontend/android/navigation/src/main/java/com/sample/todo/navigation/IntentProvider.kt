package com.sample.todo.navigation

import android.content.Context
import android.content.Intent
import com.sample.todo.base.di.AppScope
import com.sample.todo.base.entity.ActivityInfo
import javax.inject.Inject

@AppScope
class IntentProvider @Inject constructor() {
    fun mainActivityIntent(context: Context): Intent? {
        return ActivityInfo.Main.resolveIntent(context)
    }

    fun settingsActivity(context: Context): Intent? {
        return ActivityInfo.Settings.resolveIntent(context)
    }

    fun seedDatabaseActivity(context: Context): Intent? {
        return ActivityInfo.SeedDatabase.resolveIntent(context)
    }

    fun leakActivity(context: Context): Intent? {
        return ActivityInfo.Leak.resolveIntent(context)
    }

    fun dataImplementationActivity(context: Context): Intent? {
        return ActivityInfo.DataImplementation.resolveIntent(context)
    }
}
