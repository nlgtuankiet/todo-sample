package com.sample.todo.dynamic.seeddatabase.service

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.work.WorkManager
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides


@Component(
    modules = [
        SeedDatabaseControllerComponent.Provision::class
    ]
)
@SeedDatabaseControllerScope
interface SeedDatabaseControllerComponent {
    fun inject(target: SeedDatabaseControllerService)

    @Module
    object Provision {
        @JvmStatic
        @SeedDatabaseControllerScope
        @Provides
        fun provideWorkManager(): WorkManager = WorkManager.getInstance()

        @JvmStatic
        @SeedDatabaseControllerScope
        @Provides
        fun prpvideNotificationManager(context: Context) = NotificationManagerCompat.from(context)

    }

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): SeedDatabaseControllerComponent
    }

    companion object {
        operator fun invoke(target: SeedDatabaseControllerService) {
            val context = target.applicationContext
            DaggerSeedDatabaseControllerComponent.factory().create(context).inject(target)
        }
    }
}