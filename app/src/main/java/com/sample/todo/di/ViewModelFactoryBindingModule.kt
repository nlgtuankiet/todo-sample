package com.sample.todo.di

import androidx.lifecycle.ViewModelProvider
import com.sample.todo.ui.TodoViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryBindingModule {
    @Binds
    abstract fun bindTodoViewModelFactory(viewModelFactory: TodoViewModelFactory): ViewModelProvider.Factory
}