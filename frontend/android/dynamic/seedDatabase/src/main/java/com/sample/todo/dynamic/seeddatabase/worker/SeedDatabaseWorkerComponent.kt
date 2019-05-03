package com.sample.todo.dynamic.seeddatabase.worker

import com.sample.todo.androidComponent
import com.sample.todo.data.DataComponent
import com.sample.todo.dataComponent
import com.sample.todo.di.android.AndroidComponent
import com.sample.todo.dynamic.seeddatabase.library.di.SeedDatabaseWorkerScope
import com.sample.todo.dynamic.seeddatabase.lorem.Lorem
import com.sample.todo.dynamic.seeddatabase.lorem.LoremImpl
import dagger.Binds
import dagger.Component
import dagger.Module

@Component(
    dependencies = [
        DataComponent::class,
        AndroidComponent::class
    ],
    modules = [
        SeedDatabaseWorkerComponent.Binding::class
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

    @Component.Factory
    interface Factory {
        fun create(
            dataComponent: DataComponent,
            androidComponent: AndroidComponent
        ): SeedDatabaseWorkerComponent
    }

    companion object {
        operator fun invoke(target: SeedDatabaseWorker) = with(target.applicationContext) {
            DaggerSeedDatabaseWorkerComponent
                .factory()
                .create(dataComponent = dataComponent, androidComponent = androidComponent)
                .inject(target)
        }
    }
}
