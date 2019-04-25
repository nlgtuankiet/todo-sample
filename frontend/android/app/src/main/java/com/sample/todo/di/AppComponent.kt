package com.sample.todo.di

import com.sample.todo.TodoApplication
import com.sample.todo.base.di.AppScope
import com.sample.todo.base.message.MessageManagerBindingModule
import com.sample.todo.data.DataComponent
import com.sample.todo.domain.di.DomainComponent
import com.sample.todo.initializer.InitializerBindingModule
import com.sample.todo.main.about.AboutComponent
import com.sample.todo.main.addedit.AddEditComponent
import com.sample.todo.main.search.SearchComponent
import com.sample.todo.main.statistics.StatisticsComponent
import com.sample.todo.main.taskdetail.TaskDetailComponent
import com.sample.todo.main.tasks.TasksComponent
import com.sample.todo.ui.UiModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

// TODO make UiModule to UiComponent
@AppScope
@Component(
    modules = [
        UiModule::class,
        AndroidSupportInjectionModule::class,
        MessageManagerBindingModule::class,
        WorkModule::class,
        InitializerBindingModule::class,
        ApplicationBindingModule::class
    ],
    dependencies = [
        DataComponent::class,
        DomainComponent::class,
        AndroidComponent::class
    ]
)
interface AppComponent : AndroidInjector<TodoApplication> {
    fun aboutFactory(): AboutComponent.Factory
    fun addEditFactory(): AddEditComponent.Factory
    fun searchFactory(): SearchComponent.Factory
    fun statisticsFactory(): StatisticsComponent.Factory
    fun tasksFactory(): TasksComponent.Factory
    fun taskDetail(): TaskDetailComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            androidComponent: AndroidComponent,
            dataComponent: DataComponent,
            domainComponent: DomainComponent
        ): AppComponent
    }

    companion object {
        operator fun invoke(
            androidComponent: AndroidComponent,
            dataComponent: DataComponent,
            domainComponent: DomainComponent
        ): AppComponent = DaggerAppComponent.factory().create(
            androidComponent,
            dataComponent,
            domainComponent
        )
    }
}
