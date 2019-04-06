package com.sample.todo.dynamic.seeddatabase.ui

import androidx.work.WorkManager
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides


@Component(
    modules = [
        SeedDatabaseActivityComponent.Provision::class
    ]
)
@SeedDatabaseActivityScope
interface SeedDatabaseActivityComponent {

    fun inject(target: SeedDatabaseActivity)

    @Module
    object Provision {
        @JvmStatic
        @SeedDatabaseActivityScope
        @Provides
        fun provideWorkManager(): WorkManager = WorkManager.getInstance()
    }

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance activity: SeedDatabaseActivity): SeedDatabaseActivityComponent
    }

    companion object {
        operator fun invoke(target: SeedDatabaseActivity) {
            DaggerSeedDatabaseActivityComponent.factory()
                .create(target)
                .inject(target)
        }
    }
}