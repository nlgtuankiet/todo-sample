package com.sample.todo.ui

import androidx.lifecycle.ViewModel
import com.sample.todo.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SettingsBindingModule {
    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindViewModel(settingsViewModel: SettingsViewModel): ViewModel
}