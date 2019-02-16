package com.sample.todo.ui.message

import dagger.Binds
import dagger.Module

@Module
interface MessageManagerBindingModule {
    @Binds
    fun bind(messageManager: SnackbarMessageManager): MessageManager
}