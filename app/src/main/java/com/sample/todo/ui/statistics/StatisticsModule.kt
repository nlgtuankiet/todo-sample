package com.sample.todo.ui.statistics

import com.sample.todo.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class StatisticsModule {
    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            StatisticsBindingModule::class
        ]
    )
    abstract fun contributeStatisticsFragment(): StatisticsFragment
}
