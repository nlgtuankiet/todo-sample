package com.sample.todo.ui

import com.sample.todo.MainActivityModule
import dagger.Module

@Module(
    includes = [
        MainActivityModule::class
    ]
)
interface UiModule