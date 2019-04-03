package com.sample.todo.domain.di

import com.sample.todo.domain.repository.PreferenceRepository
import com.sample.todo.domain.repository.TaskRepository
import com.sample.todo.domain.usecase.*
import com.sample.todo.domain.util.lorem.Lorem
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DomainModule::class
    ]
)
@DomainScope
interface DomainComponent {
    fun GetTask(): GetTask
    fun GetTaskFilterTypeLiveData(): GetTaskFilterTypeObservable
    fun GetTaskMiniList(): GetTaskMiniList
    fun GetTasksCountLive(): GetTasksCountObservable
    fun GetTaskStatLive(): GetTaskStatObservable
    fun InsertAllTasks(): InsertAllTasks
    fun InsertNewTask(): InsertNewTask
    fun SeedDatabase(): SeedDatabase
    fun SetTaskFilterType(): SetTaskFilterType
    fun UpdateComplete(): UpdateComplete
    fun UpdateTask(): UpdateTask
    fun provideLore(): Lorem

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance taskRepository: TaskRepository,
            @BindsInstance preferenceRepository: PreferenceRepository
        ): DomainComponent
    }

    companion object {
        operator fun invoke(
            taskRepository: TaskRepository,
            preferenceRepository: PreferenceRepository
        ): DomainComponent = DaggerDomainComponent.factory().create(
            taskRepository,
            preferenceRepository
        )
    }
}