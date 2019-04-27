package com.sample.todo.domain.di

import com.sample.todo.domain.repository.PreferenceRepository
import com.sample.todo.domain.repository.TaskRepository
import com.sample.todo.domain.usecase.GetTask
import com.sample.todo.domain.usecase.GetTaskFilterTypeObservable
import com.sample.todo.domain.usecase.GetTaskMiniList
import com.sample.todo.domain.usecase.GetTaskStatObservable
import com.sample.todo.domain.usecase.GetTasksCountObservable
import com.sample.todo.domain.usecase.InsertNewTask
import com.sample.todo.domain.usecase.SetTaskFilterType
import com.sample.todo.domain.usecase.UpdateComplete
import com.sample.todo.domain.usecase.UpdateTask
import dagger.BindsInstance
import dagger.Component

@Component()
@DomainScope
interface DomainComponent {
    fun GetTask(): GetTask
    fun GetTaskFilterTypeLiveData(): GetTaskFilterTypeObservable
    fun GetTaskMiniList(): GetTaskMiniList
    fun GetTasksCountLive(): GetTasksCountObservable
    fun GetTaskStatLive(): GetTaskStatObservable
    fun InsertNewTask(): InsertNewTask
    fun SetTaskFilterType(): SetTaskFilterType
    fun UpdateComplete(): UpdateComplete
    fun UpdateTask(): UpdateTask

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
