package com.sample.todo.ui.setting.seedinput

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SeedInputModule {
    @SeedInputScoped
    @ContributesAndroidInjector(
        modules = [
        ]
    )
    abstract fun contributeSeedInputFragment(): SeedInputFragment
}
