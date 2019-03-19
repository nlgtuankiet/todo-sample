package com.sample.todo.data.firestore

import com.sample.todo.data.DataComponent
import com.sample.todo.data.TaskRepositoryBindingModule
import com.sample.todo.data.core.DataScope
import com.sample.todo.data.firestore.function.FirebaseCloudFunctionModule
import com.sample.todo.data.firestore.function.MoshiModule
import com.sample.todo.data.firestore.function.SearchResultDataSourceFactoryFactoryBindingModule
import com.sample.todo.data.preference.PreferenceModule
import dagger.Component

@Component(
    modules = [
        FirestoreModule::class,
        MapperBindingModule::class,
        TaskDataSourceBindingModule::class,
        TaskRepositoryBindingModule::class,
        PreferenceModule::class,
        MoshiModule::class,
        FirebaseCloudFunctionModule::class,
        SearchResultDataSourceFactoryFactoryBindingModule::class
    ]
)
@DataScope
abstract class FirestoreDataComponent : DataComponent() {
    @Component.Builder
    abstract class Builder : DataComponent.Builder()

    companion object {
        fun builder(): Builder = DaggerFirestoreDataComponent.builder()
    }
}