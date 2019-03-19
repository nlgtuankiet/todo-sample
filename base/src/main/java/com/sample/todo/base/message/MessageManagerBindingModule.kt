package com.sample.todo.base.message

import dagger.Binds
import dagger.Module

@Module
interface MessageManagerBindingModule {
    @Binds
    fun bind(messageManager: SnackbarMessageManager): MessageManager
}