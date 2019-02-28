package com.sample.todo.domain.di

import com.sample.todo.domain.util.lorem.Lorem
import com.sample.todo.domain.util.lorem.LoremImpl
import dagger.Binds
import dagger.Module

@Module
interface LoremBindingModule {
    @Binds
    @DomainScope
    fun provideLorem(lorem: LoremImpl): Lorem
}