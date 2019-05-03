package com.sample.todo.dynamic.data.task.firestore

import com.sample.todo.data.DataComponent
import com.sample.todo.data.RepositoryBindingModule
import com.sample.todo.data.core.DataScope
import com.sample.todo.data.preference.PreferenceModule
import com.sample.todo.dynamic.data.task.firestore.function.FirebaseCloudFunctionModule
import com.sample.todo.dynamic.data.task.firestore.function.MoshiModule
import com.sample.todo.dynamic.data.task.firestore.function.SearchResultDataSourceFactoryFactoryBindingModule
import dagger.Component

@Component(
    modules = [
        FirestoreModule::class,
        MapperBindingModule::class,
        TaskDataSourceBindingModule::class,
        RepositoryBindingModule::class,
        PreferenceModule::class,
        MoshiModule::class,
        FirebaseCloudFunctionModule::class,
        SearchResultDataSourceFactoryFactoryBindingModule::class
    ]
)
@DataScope
interface FirestoreDataComponent : DataComponent {
    @Component.Factory
    interface Factory : DataComponent.Factory

    companion object : DataComponent.Companion(DaggerFirestoreDataComponent.factory()) {
        fun main() {
            var a: FirestoreDataComponent.Companion = TODO()
            var b: DataComponent.Companion = TODO()
            b = a
        }
    }
}
