package com.sample.todo.dynamic.seeddatabase.worker

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.work.WorkManager
import com.sample.todo.TodoApplication
import com.sample.todo.data.DataComponent
import com.sample.todo.dynamic.seeddatabase.service.SeedDatabaseControllerScope
import com.sample.todo.dynamic.seeddatabase.lorem.Lorem
import com.sample.todo.dynamic.seeddatabase.lorem.LoremImpl
import dagger.*

@Component(
    dependencies = [
        DataComponent::class
    ],
    modules = [
        SeedDatabaseWorkerComponent.Binding::class,
        SeedDatabaseWorkerComponent.Provision::class
    ]
)
@SeedDatabaseWorkerScope
interface SeedDatabaseWorkerComponent {
    fun inject(target: SeedDatabaseWorker)

    @Module
    interface Binding {
        @Binds
        fun bindLorem(type: LoremImpl): Lorem
    }

    @Module
    object Provision {
        @JvmStatic
        @SeedDatabaseWorkerScope
        @Provides
        fun provideWorkManager(): WorkManager = WorkManager.getInstance()

        @JvmStatic
        @SeedDatabaseWorkerScope
        @Provides
        fun provideNotificationManager(context: Context) = NotificationManagerCompat.from(context)

    }

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            dataComponent: DataComponent
        ): SeedDatabaseWorkerComponent
    }

    companion object {
        operator fun invoke(target: SeedDatabaseWorker) {
            val application = target.applicationContext as? TodoApplication ?: TODO()
            DaggerSeedDatabaseWorkerComponent
                .factory()
                .create(application, application.dataComponent)
                .inject(target)
        }
    }


}