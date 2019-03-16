package com.sample.todo.ui

import com.sample.todo.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

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
