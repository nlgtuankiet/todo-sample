package com.sample.todo.settings.di

import androidx.lifecycle.ViewModelProvider
import com.sample.todo.settings.ui.SettingsViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface SettingsViewModelFactoryBindingModule {
    @Binds
    fun bind(factory: SettingsViewModelFactory): ViewModelProvider.Factory
}