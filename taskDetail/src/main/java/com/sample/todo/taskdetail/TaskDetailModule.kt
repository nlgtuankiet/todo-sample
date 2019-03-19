package com.sample.todo.taskdetail

import com.sample.todo.base.di.FragmentScoped
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