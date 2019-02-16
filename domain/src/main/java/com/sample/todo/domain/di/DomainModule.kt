package com.sample.todo.domain.di

import dagger.Module

@Module(
    includes = [LoremModule::class]
)
interface DomainModule