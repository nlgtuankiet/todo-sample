package com.sample.todo.di.app

import com.sample.todo.base.di.AppScope
import com.sample.todo.data.DataComponent
import com.sample.todo.di.ApplicationBindingModule
import com.sample.todo.di.WorkModule
import com.sample.todo.di.android.AndroidComponent
import com.sample.todo.domain.di.DomainComponent
import com.sample.todo.initializer.InitializerBindingModule
import com.sample.todo.ui.UiModule
import dagger.Component
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
interface AppComponentImpl : AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            androidComponent: AndroidComponent,
            dataComponent: DataComponent,
            domainComponent: DomainComponent
        ): AppComponentImpl
    }
}
