package com.sample.todo.setting

import com.sample.todo.base.di.FragmentScoped
import com.sample.todo.setting.seedinput.SeedInputModule
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
