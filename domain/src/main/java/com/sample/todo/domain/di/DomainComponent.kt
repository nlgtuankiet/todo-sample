package com.sample.todo.domain.di

import com.sample.todo.domain.repository.PreferenceRepository
import com.sample.todo.domain.repository.TaskRepository
import com.sample.todo.domain.usecase.GetTask
import com.sample.todo.domain.usecase.GetTaskFilterType
import com.sample.todo.domain.usecase.GetTaskMiniList
import com.sample.todo.domain.usecase.GetTaskStatFlowable
import com.sample.todo.domain.usecase.GetTasksCountFlowable
import com.sample.todo.domain.usecase.InsertAllTasks
import com.sample.todo.domain.usecase.InsertNewTask
import com.sample.todo.domain.usecase.SeedDatabase
import com.sample.todo.domain.usecase.SetTaskFilterType
import com.sample.todo.domain.usecase.UpdateComplete
import com.sample.todo.domain.usecase.UpdateTask
import com.sample.todo.domain.util.lorem.Lorem
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DomainModule::class
    ]
)
@DomainScope
abstract class DomainComponent {
    abstract fun GetTask(): GetTask
    abstract fun GetTaskFilterTypeLiveData(): GetTaskFilterType
    abstract fun GetTaskMiniList(): GetTaskMiniList
    abstract fun GetTasksCountLive(): GetTasksCountFlowable
    abstract fun GetTaskStatLive(): GetTaskStatFlowable
    abstract fun InsertAllTasks(): InsertAllTasks
    abstract fun InsertNewTask(): InsertNewTask
    abstract fun SeedDatabase(): SeedDatabase
    abstract fun SetTaskFilterType(): SetTaskFilterType
    abstract fun UpdateComplete(): UpdateComplete
    abstract fun UpdateTask(): UpdateTask
    abstract fun provideLore(): Lorem

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun taskRepository(taskRepository: TaskRepository): Builder
        @BindsInstance
        fun preferenceRepository(preferenceRepository: PreferenceRepository): Builder
        fun build(): DomainComponent
    }

    companion object {
        fun builder(): Builder = DaggerDomainComponent.builder()
    }
}