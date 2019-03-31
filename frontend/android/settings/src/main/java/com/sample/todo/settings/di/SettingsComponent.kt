package com.sample.todo.settings.di

import com.sample.todo.TodoApplication
import com.sample.todo.di.AppComponent
import com.sample.todo.settings.data.SettingsPreferenceRepositoryBindingModule
import com.sample.todo.settings.ui.SettingsActivity
import com.sample.todo.settings.ui.SettingsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    dependencies = [
        AppComponent::class
    ],
    modules = [
        AndroidSupportInjectionModule::class,
        SettingsModule::class,
        SettingsPreferenceRepositoryBindingModule::class
    ]
)
@SettingsScope
abstract class SettingsComponent {

    abstract fun inject(activity: SettingsActivity)

    @Component.Builder
    abstract class Builder {
        @BindsInstance
        abstract fun settingsActivity(activity: SettingsActivity): Builder
        abstract fun appComponent(appComponent: AppComponent): Builder
        abstract fun build(): SettingsComponent
    }
    companion object {
        fun inject(activity: SettingsActivity): SettingsComponent {
            val todoApp = activity.application as? TodoApplication
                ?: TODO()
            val component = DaggerSettingsComponent
                .builder()
                .appComponent(todoApp.appComponent)
                .settingsActivity(activity)
                .build()
            component.inject(activity)
            return component
        }
    }
}