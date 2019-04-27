package com.sample.todo.di.application

import com.sample.todo.di.android.AndroidComponent
import com.sample.todo.domain.usecase.GetDataComponent
import com.sample.todo.domain.usecase.SetDataImplementation

interface ApplicationComponent {

    fun provideGetDataComponent(): GetDataComponent
    fun provideSetDataComponent(): SetDataImplementation

    companion object {
        operator fun invoke(androidComponent: AndroidComponent): ApplicationComponent {
            return DaggerApplicationComponentImpl.factory().create(androidComponent)
        }
    }
}
