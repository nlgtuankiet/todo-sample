package com.sample.todo.dynamic.settings.di

import com.sample.todo.androidComponent
import com.sample.todo.di.AndroidComponent
import com.sample.todo.dynamic.settings.data.SettingsPreferenceRepositoryBindingModule
import com.sample.todo.dynamic.settings.ui.SettingsActivity
import com.sample.todo.dynamic.settings.ui.SettingsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    dependencies = [
        AndroidComponent::class
    ],
    modules = [
        AndroidSupportInjectionModule::class,
        SettingsModule::class,
        SettingsPreferenceRepositoryBindingModule::class
    ]
)
@SettingsScope
interface SettingsComponent {

    fun inject(activity: SettingsActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: SettingsActivity,
            androidComponent: AndroidComponent
        ): SettingsComponent
    }

    companion object {
        fun inject(activity: SettingsActivity) {
            DaggerSettingsComponent.factory().create(activity, activity.androidComponent).inject(activity)
        }
    }
}