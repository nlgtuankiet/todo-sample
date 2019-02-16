package com.sample.todo.di

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.sample.todo.TodoApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    @ApplicationScope
    fun provideApplicationContext(application: TodoApplication): Context {
        return application
    }

    @Provides
    @ApplicationScope
    fun provideNotificationManager(context: Context): NotificationManagerCompat {
        return NotificationManagerCompat.from(context)
    }
}