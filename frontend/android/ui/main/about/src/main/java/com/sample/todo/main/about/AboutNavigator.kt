package com.sample.todo.main.about

import com.sample.todo.base.FragmentNavigator
import com.sample.todo.base.di.FragmentScope
import com.sample.todo.base.Holder
import com.sample.todo.main.navigation.MainNavigator
import com.sample.todo.navigation.IntentProvider
import javax.inject.Inject

@FragmentScope
class AboutNavigator @Inject constructor(
    private val holder: Holder<AboutFragment>,
    private val intentProvider: IntentProvider
) : FragmentNavigator(holder) {

    // TODO dagger bug, this is a work around
    private val mainNavigator: MainNavigator by lazy {
        val activity = holder.instance.requireActivity()
        MainNavigator(intentProvider, activity)
    }

    fun toSettingActivity() {
        mainNavigator.toSettingsActivity()
    }

    fun toSeedDatabaseActivity() {
        mainNavigator.toSeedDatabaseActivity()
    }

    fun toLeakActivity() {
        mainNavigator.toLeakActivity()
    }

    fun toDataImplementationActivity() {
        mainNavigator.toDataImplementationActivity()
    }
}
