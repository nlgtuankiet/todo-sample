package com.sample.todo.main.tasks

import com.sample.todo.base.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

// TODO: why the viewmodel cannot be annotate with FragmentScoped

@Module
abstract class TasksModule {
    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            TasksBindingModule::class
        ]
    )
    abstract fun contributeTasksFragment(): TasksFragment
}
