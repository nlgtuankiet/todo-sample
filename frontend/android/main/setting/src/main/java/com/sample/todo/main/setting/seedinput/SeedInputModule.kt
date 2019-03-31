package com.sample.todo.main.setting.seedinput

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SeedInputModule {
    @SeedInputScope
    @ContributesAndroidInjector(
        modules = [
        ]
    )
    abstract fun contributeSeedInputFragment(): SeedInputFragment
}
