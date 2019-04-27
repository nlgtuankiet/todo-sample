package com.sample.todo.dynamic.seeddatabase.service

import com.sample.todo.androidComponent
import com.sample.todo.di.android.AndroidComponent
import dagger.Component

@Component(
    dependencies = [
        AndroidComponent::class
    ]
)
@SeedDatabaseControllerScope
interface SeedDatabaseControllerComponent {
    fun inject(target: SeedDatabaseControllerService)

    @Component.Factory
    interface Factory {
        fun create(androidComponent: AndroidComponent): SeedDatabaseControllerComponent
    }

    companion object {
        operator fun invoke(target: SeedDatabaseControllerService) {
            DaggerSeedDatabaseControllerComponent.factory().create(target.androidComponent).inject(target)
        }
    }
}
