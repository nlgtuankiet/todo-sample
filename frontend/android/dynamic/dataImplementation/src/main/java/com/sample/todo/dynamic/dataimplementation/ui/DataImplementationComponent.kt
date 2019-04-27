package com.sample.todo.dynamic.dataimplementation.ui

import com.sample.todo.androidComponent
import com.sample.todo.base.di.ActivityScoped
import com.sample.todo.di.android.AndroidComponent
import com.sample.todo.data.DataPreferenceBindingModule
import dagger.Component
import dagger.android.AndroidInjector

@Component(
    dependencies = [
        AndroidComponent::class
    ],
    modules = [
        DataPreferenceBindingModule::class
    ]
)
@ActivityScoped
interface DataImplementationComponent : AndroidInjector<DataImplementationActivity> {

    @Component.Factory
    interface Factory {
        fun create(androidComponent: AndroidComponent): AndroidInjector<DataImplementationActivity>
    }

    companion object {
        fun inject(activity: DataImplementationActivity) {
            DaggerDataImplementationComponent
                .factory()
                .create(activity.androidComponent)
                .inject(activity)
        }
    }
}
