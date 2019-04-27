package com.sample.todo.di.app

import com.sample.todo.TodoApplication
import com.sample.todo.data.DataComponent
import com.sample.todo.di.android.AndroidComponent
import com.sample.todo.domain.di.DomainComponent
import com.sample.todo.main.about.AboutComponent
import com.sample.todo.main.addedit.AddEditComponent
import com.sample.todo.main.search.SearchComponent
import com.sample.todo.main.statistics.StatisticsComponent
import com.sample.todo.main.taskdetail.TaskDetailComponent
import com.sample.todo.main.tasks.TasksComponent
import dagger.android.AndroidInjector

interface AppComponent : AndroidInjector<TodoApplication> {
    fun aboutFactory(): AboutComponent.Factory
    fun addEditFactory(): AddEditComponent.Factory
    fun searchFactory(): SearchComponent.Factory
    fun statisticsFactory(): StatisticsComponent.Factory
    fun tasksFactory(): TasksComponent.Factory
    fun taskDetail(): TaskDetailComponent.Factory

    companion object {
        operator fun invoke(
            androidComponent: AndroidComponent,
            dataComponent: DataComponent,
            domainComponent: DomainComponent
        ): AppComponent = DaggerAppComponentImpl.factory().create(
            androidComponent,
            dataComponent,
            domainComponent
        )
    }
}
