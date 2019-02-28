package com.sample.todo.settings.di

import com.sample.todo.di.AppComponent
import com.sample.todo.domain.di.DomainComponent
import com.sample.todo.settings.data.PreferenceRepositoryBindingModule
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
        SettingsViewModelFactoryBindingModule::class,
        PreferenceRepositoryBindingModule::class
    ]
)
@SettingsScope
abstract class SettingsComponent {

    abstract fun inject(activity: SettingsActivity)


    @Component.Builder
    abstract class Builder {
        abstract fun appComponent(appComponent: AppComponent): Builder

        @BindsInstance
        abstract fun settingsActivity(activity: SettingsActivity): Builder

        abstract fun build(): SettingsComponent
    }
    companion object {
        fun builder(): Builder = DaggerSettingsComponent.builder()
    }
}