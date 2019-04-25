package com.sample.todo.di

import androidx.lifecycle.ViewModelProvider
import com.sample.todo.TodoViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryBindingModule {
    @Binds
    fun viewModelFactory(instance: TodoViewModelFactory): ViewModelProvider.Factory
}