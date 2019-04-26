package com.sample.todo.dynamic.seeddatabase.ui

import androidx.lifecycle.ViewModel
import com.sample.todo.androidComponent
import com.sample.todo.base.di.ViewModelKey
import com.sample.todo.di.AndroidComponent
import com.sample.todo.di.ViewModelFactoryBindingModule
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(
    modules = [
        SeedDatabaseActivityComponent.Binding::class
    ],
    dependencies = [
        AndroidComponent::class
    ]
)
@SeedDatabaseActivityScope
interface SeedDatabaseActivityComponent {

    fun inject(target: SeedDatabaseActivity)

    @Module(
        includes = [
            ViewModelFactoryBindingModule::class
        ]
    )
    interface Binding {
        @Binds
        @ViewModelKey(SeedDatabaseViewModel::class)
        @IntoMap
        fun viewModel(instance: SeedDatabaseViewModel): ViewModel
    }

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: SeedDatabaseActivity,
            androidComponent: AndroidComponent
        ): SeedDatabaseActivityComponent
    }

    companion object {
        operator fun invoke(target: SeedDatabaseActivity) {
            DaggerSeedDatabaseActivityComponent.factory()
                .create(
                    activity = target,
                    androidComponent = target.androidComponent
                )
                .inject(target)
        }
    }
}
