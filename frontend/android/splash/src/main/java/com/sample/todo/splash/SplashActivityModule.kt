package com.sample.todo.splash

import com.sample.todo.base.di.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface SplashActivityModule {
    @ContributesAndroidInjector
    @ActivityScope
    fun activity(): SplashActivity
}
