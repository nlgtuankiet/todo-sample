package com.sample.todo.ui

import com.sample.todo.main.MainActivityModule
import dagger.Module

@Module(
    includes = [
        MainActivityModule::class
    ]
)
interface UiModule