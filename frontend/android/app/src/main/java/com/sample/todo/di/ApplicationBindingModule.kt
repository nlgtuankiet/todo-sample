package com.sample.todo.di

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.sample.todo.TodoFragmentFactory
import com.sample.todo.TodoViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ApplicationBindingModule {
    @Binds
    fun fragmentFactory(instance: TodoFragmentFactory): FragmentFactory

    @Binds
    fun viewModelFactory(instance: TodoViewModelFactory): ViewModelProvider.Factory
}