package com.sample.todo.main.about

import androidx.navigation.fragment.findNavController
import com.sample.todo.base.FragmentNavigator
import com.sample.todo.base.di.FragmentScoped
import com.sample.todo.base.Holder
import com.sample.todo.navigation.MainNavigator
import javax.inject.Inject

@FragmentScoped
class AboutNavigator @Inject constructor(
    holder: Holder<AboutFragment>
): FragmentNavigator(holder) {

    fun toSettingActivity() {
        MainNavigator.toSettingsActivity(activity)
    }

    fun toSeedDatabaseActivity() {
        MainNavigator.toSeedDatabaseActivity(activity)
    }
}
