package com.sample.todo.di

import com.sample.todo.TodoApplication
import com.sample.todo.data.DataComponent
import com.sample.todo.domain.di.DomainComponent
import com.sample.todo.domain.di.DomainModule
import com.sample.todo.service.ServiceBindingModule
import com.sample.todo.ui.HostActivityModule
import com.sample.todo.ui.message.MessageManagerBindingModule
import com.sample.todo.worker.WorkerModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(
    modules = [
        HostActivityModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        MessageManagerBindingModule::class,
        ViewModelFactoryBindingModule::class,
        AssistedInjectModule::class,
        WorkerModule::class,
        DomainModule::class,
        ServiceBindingModule::class
    ],
    dependencies = [
        DataComponent::class,
        DomainComponent::class
    ]
)
interface AppComponent : AndroidInjector<TodoApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TodoApplication>() {
        abstract fun dataComponent(dataComponent: DataComponent): Builder
        abstract fun domainComponent(domainComponent: DomainComponent): Builder
    }
    companion object {
        fun builder(): Builder = DaggerAppComponent.builder()
    }
}