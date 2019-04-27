package com.sample.todo.di.app

import com.sample.todo.domain.repository.MessageManager
import com.sample.todo.domain.repository.SnackbarMessageManager
import dagger.Binds
import dagger.Module

@Module
interface MessageManagerBindingModule {
    @Binds
    fun bind(messageManager: SnackbarMessageManager): MessageManager
}
