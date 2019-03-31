package com.sample.todo.domain.di

import dagger.Module

@Module(
    includes = [LoremBindingModule::class]
)
interface DomainModule