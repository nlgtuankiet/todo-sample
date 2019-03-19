package com.sample.todo

import com.sample.todo.about.AboutModule
import com.sample.todo.addedit.AddEditModule
import com.sample.todo.base.di.ActivityScoped
import com.sample.todo.main.MainActivity
import com.sample.todo.search.SearchModule
import com.sample.todo.setting.SettingModule
import com.sample.todo.statistics.StatisticsModule
import com.sample.todo.taskdetail.TaskDetailModule
import com.sample.todo.tasks.TasksModule
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
            SettingModule::class,
            AboutModule::class
        ]
    )
    abstract fun contrubuteHostActivity(): MainActivity
}
