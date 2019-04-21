package com.sample.todo.main.about

import androidx.navigation.fragment.findNavController
import com.sample.todo.base.di.FragmentScoped
import com.sample.todo.base.entity.Holder
import com.sample.todo.navigation.MainNavigator
import javax.inject.Inject

@FragmentScoped
class AboutNavigator @Inject constructor(
    private val holder: Holder<AboutFragment>
) {
    private val fragment by lazy { holder.instance }
    private val activity by lazy { fragment.requireActivity() }

    fun navigateToSettingActivity() {
        MainNavigator.toSettingsActivity(activity)
    }

    fun navigateToStatisticsFragment() {
        fragment.findNavController().navigate(R.id.statisticsFragment)
    }

    fun navigateToSeedDatabaseActivity() {
        MainNavigator.toSeedDatabaseActivity(activity)
    }
}