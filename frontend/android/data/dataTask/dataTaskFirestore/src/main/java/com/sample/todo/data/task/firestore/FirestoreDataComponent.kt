package com.sample.todo.data.task.firestore

import android.content.Context
import com.sample.todo.data.DataComponent
import com.sample.todo.data.TaskRepositoryBindingModule
import com.sample.todo.data.core.DataScope
import com.sample.todo.data.preference.PreferenceModule
import com.sample.todo.data.task.firestore.function.FirebaseCloudFunctionModule
import com.sample.todo.data.task.firestore.function.MoshiModule
import com.sample.todo.data.task.firestore.function.SearchResultDataSourceFactoryFactoryBindingModule
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
interface FirestoreDataComponent : DataComponent {
    @Component.Factory
    interface Factory : DataComponent.Factory

    companion object {
        operator fun invoke(context: Context): DataComponent {
            return DaggerFirestoreDataComponent.factory().create(context)
        }
    }
}