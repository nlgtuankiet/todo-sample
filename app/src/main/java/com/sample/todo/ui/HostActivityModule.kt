package com.sample.todo.ui

import com.sample.todo.di.ActivityScoped
import com.sample.todo.ui.about.AboutModule
import com.sample.todo.ui.addedit.AddEditModule
import com.sample.todo.ui.search.SearchModule
import com.sample.todo.ui.setting.SettingModule
import com.sample.todo.ui.taskdetail.TaskDetailModule
import com.sample.todo.ui.tasks.TasksModule
import com.sample.todo.ui.statistics.StatisticsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HostActivityModule {
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
    abstract fun contrubuteHostActivity(): HostActivity
}
