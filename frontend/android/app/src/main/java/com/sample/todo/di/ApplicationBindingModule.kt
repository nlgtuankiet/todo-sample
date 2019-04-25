package com.sample.todo.di

import dagger.Module

@Module(
    includes = [
        FragmentFactoryBindingModule::class,
        ViewModelFactoryBindingModule::class
    ]
)
interface ApplicationBindingModule
