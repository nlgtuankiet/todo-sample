package com.sample.todo.initializer

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
interface InitializerBindingModule {
    @Binds
    @IntoSet
    fun bindStetho(initializer: StethoInitializer): Initializer

    @Binds
    @IntoSet
    fun bindTimber(initializer: TimberInitializer): Initializer

    @Binds
    @IntoSet
    fun bindWorkManager(initializer: WorkManagerInitializer): Initializer

    @Binds
    @IntoSet
    fun bindNotification(initializer: NotificationChannelInitializer): Initializer
}