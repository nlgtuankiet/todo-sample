package com.sample.todo.di

import androidx.fragment.app.FragmentFactory
import com.sample.todo.TodoFragmentFactory
import dagger.Binds
import dagger.Module

@Module
interface FragmentFactoryBindingModule {
    @Binds
    fun fragmentFactory(instance: TodoFragmentFactory): FragmentFactory
}
