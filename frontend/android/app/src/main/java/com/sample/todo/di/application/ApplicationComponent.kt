package com.sample.todo.di.application

import com.sample.todo.TodoApplication
import com.sample.todo.base.di.ApplicationScope
import com.sample.todo.data.DataImplementationBindingModule
import com.sample.todo.data.DataPreferenceBindingModule
import com.sample.todo.domain.usecase.GetDataComponent
import dagger.BindsInstance
import dagger.Component

@Component(modules = [
    DataPreferenceBindingModule::class,
    DataImplementationBindingModule::class
])
@ApplicationScope
interface ApplicationComponent {

    fun provideGetDataComponent(): GetDataComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: TodoApplication): ApplicationComponent
    }

    companion object {
        operator fun invoke(application: TodoApplication): ApplicationComponent {
            return DaggerApplicationComponent.factory().create(application)
        }
    }
}
