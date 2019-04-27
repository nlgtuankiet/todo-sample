package com.sample.todo.di.application

import com.sample.todo.data.DataPreferenceBindingModule
import com.sample.todo.di.android.AndroidComponent
import dagger.Component

@Component(
    modules = [
        DataPreferenceBindingModule::class
    ],
    dependencies = [
        AndroidComponent::class
    ]
)
@ApplicationScope
interface ApplicationComponentImpl : ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(androidComponent: AndroidComponent): ApplicationComponent
    }
}
