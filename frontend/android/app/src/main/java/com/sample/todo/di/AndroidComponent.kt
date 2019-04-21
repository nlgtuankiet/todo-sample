package com.sample.todo.di

import android.content.Context
import android.content.SharedPreferences
import androidx.core.app.NotificationManagerCompat
import androidx.work.WorkManager
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.sample.todo.TodoApplication
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

interface AndroidComponent {
    fun application(): TodoApplication
    fun context(): Context
    fun notificationManagerConpat(): NotificationManagerCompat
    fun sharePreference(): SharedPreferences
    fun splitInstallManager(): SplitInstallManager
    fun workManager(): WorkManager

    companion object {
        operator fun invoke(application: TodoApplication): AndroidComponent =
            DaggerAndroidComponentImpl.factory().create(application)
    }
}

@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class AndroidScope

@Component(
    modules = [
        AndroidComponentImpl.Provision::class,
        AndroidComponentImpl.Binding::class
    ]
)
@AndroidScope
interface AndroidComponentImpl : AndroidComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: TodoApplication): AndroidComponent
    }

    @Module
    object Provision {
        @Provides
        @JvmStatic
        @AndroidScope
        fun provideNotificationManager(context: Context): NotificationManagerCompat {
            return NotificationManagerCompat.from(context)
        }

        @Provides
        @JvmStatic
        @AndroidScope
        fun provideSharePreference(context: Context) = context.getSharedPreferences("todo_pref", Context.MODE_PRIVATE)

        @Provides
        @JvmStatic
        @AndroidScope
        fun provideSplitInstallManager(context: Context) = SplitInstallManagerFactory.create(context)

        @Provides
        @JvmStatic
        fun workManager() = WorkManager.getInstance()
    }

    @Module
    interface Binding {
        @Binds
        fun context(impl: TodoApplication): Context
    }
}
