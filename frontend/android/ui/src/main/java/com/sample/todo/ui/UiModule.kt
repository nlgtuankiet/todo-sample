package com.sample.todo.ui

import com.sample.todo.main.MainActivityModule
import com.sample.todo.splash.SplashActivityModule
import dagger.Module

@Module(
    includes = [
        MainActivityModule::class,
        SplashActivityModule::class
    ]
)
interface UiModule
