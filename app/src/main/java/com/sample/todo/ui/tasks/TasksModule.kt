package com.sample.todo.ui.tasks

import com.sample.todo.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

// TODO: why the viewmodel cannot be annotate with FragmentScoped

@Module
internal abstract class TasksModule {
    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            TasksBindingModule::class
        ]
    )
    abstract fun contributeTasksFragment(): TasksFragment
}
