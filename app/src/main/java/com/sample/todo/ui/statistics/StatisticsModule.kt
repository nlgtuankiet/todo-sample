package com.sample.todo.ui.statistics

import androidx.lifecycle.ViewModel
import com.sample.todo.di.FragmentScoped
import com.sample.todo.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

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

@Module
abstract class StatisticsBindingModule {
    @Binds
    @IntoMap
    @ViewModelKey(StatisticsViewModel::class)
    abstract fun bindStatisticsViewModel(viewModel: StatisticsViewModel): ViewModel
}