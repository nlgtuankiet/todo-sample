package com.sample.todo.di

import androidx.fragment.app.FragmentFactory
import com.sample.todo.TodoFragmentFactory
import dagger.Binds
import dagger.Module

@Module
interface ApplicationBindingModule {
    @Binds
    fun fragmentFactory(instance: TodoFragmentFactory): FragmentFactory
}