package com.sample.todo.settings.ui

import androidx.lifecycle.ViewModel
import com.sample.todo.di.FragmentScoped
import com.sample.todo.di.ViewModelKey
import com.sample.todo.ui.setting.SettingFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SettingsModule {
    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            SettingsBindingModule::class
        ]
    )
    abstract fun contributeSettingsFragment(): SettingsFragment
}

@Module
abstract class SettingsBindingModule {
    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindViewModel(settingsViewModel: SettingsViewModel): ViewModel
}