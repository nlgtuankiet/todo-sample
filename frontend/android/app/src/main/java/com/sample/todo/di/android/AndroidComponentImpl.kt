package com.sample.todo.di.android

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.work.WorkManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.sample.todo.TodoApplication
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

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
        fun provideSharePreference(application: TodoApplication) = application.getSharedPreferences("todo_pref", Context.MODE_PRIVATE)

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
