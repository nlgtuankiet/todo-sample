package com.sample.todo.main

import com.sample.todo.main.about.AboutModule
import com.sample.todo.main.addedit.AddEditModule
import com.sample.todo.base.di.ActivityScoped
import com.sample.todo.main.search.SearchModule
import com.sample.todo.main.statistics.StatisticsModule
import com.sample.todo.main.taskdetail.TaskDetailModule
import com.sample.todo.main.tasks.TasksModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            TasksModule::class,
            TaskDetailModule::class,
            AddEditModule::class,
            SearchModule::class,
            StatisticsModule::class,
            AboutModule::class
        ]
    )
    abstract fun contrubuteHostActivity2(): MainActivity
}
