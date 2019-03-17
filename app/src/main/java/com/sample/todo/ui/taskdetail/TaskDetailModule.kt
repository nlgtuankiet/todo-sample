package com.sample.todo.ui.taskdetail

import com.sample.todo.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TaskDetailModule {
    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            TaskDetailBindingModule::class
        ]
    )
    abstract fun contributeTaskDetailFragment(): TaskDetailFragment
}