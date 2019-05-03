package com.sample.todo.main.tasks.ui

import com.sample.todo.base.di.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

// TODO: why the viewmodel cannot be annotate with FragmentScoped

@Module
abstract class TasksModule {
    @FragmentScope
    @ContributesAndroidInjector(
        modules = [
            TasksBindingModule::class
        ]
    )
    abstract fun contributeTasksFragment(): TasksFragment
}
