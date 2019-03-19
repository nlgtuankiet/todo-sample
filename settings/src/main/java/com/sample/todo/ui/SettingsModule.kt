package com.sample.todo.ui

import com.sample.todo.base.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SettingsModule {
    @com.sample.todo.base.di.FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            SettingsBindingModule::class
        ]
    )
    abstract fun contributeSettingsFragment(): SettingsFragment
}
