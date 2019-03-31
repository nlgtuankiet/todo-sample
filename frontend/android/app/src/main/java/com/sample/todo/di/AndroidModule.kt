package com.sample.todo.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.app.NotificationManagerCompat
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.sample.todo.TodoApplication
import com.sample.todo.base.di.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class AndroidModule {
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

    @Provides
    @ApplicationScope
    fun provideSharePreference(context: Context): SharedPreferences {
        return context.getSharedPreferences("todo_pref", MODE_PRIVATE)
    }

    @Provides
    @ApplicationScope
    fun provideSplitInstallManager(context: Context): SplitInstallManager {
        return SplitInstallManagerFactory.create(context)
    }
}