package com.sample.todo.ui.setting

import com.sample.todo.di.FragmentScoped
import com.sample.todo.ui.setting.seedinput.SeedInputModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SettingModule {
    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            SeedInputModule::class
        ]
    )
    abstract fun contributeSettingFragment(): SettingFragment
}
