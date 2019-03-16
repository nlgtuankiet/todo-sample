package com.sample.todo.ui

import androidx.lifecycle.ViewModel
import com.sample.todo.di.ActivityScoped
import com.sample.todo.di.ViewModelKey
import com.sample.todo.ui.about.AboutModule
import com.sample.todo.ui.addedit.AddEditModule
import com.sample.todo.ui.search.SearchModule
import com.sample.todo.ui.setting.SettingModule
import com.sample.todo.ui.taskdetail.TaskDetailModule
import com.sample.todo.ui.tasks.TasksModule
import com.sample.todo.ui.statistics.StatisticsModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

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
            HostActivityBindingModule::class,
            SettingModule::class,
            AboutModule::class
        ]
    )
    abstract fun contrubuteHostActivity(): HostActivity
}

@Module
abstract class HostActivityBindingModule {
    @Binds
    @IntoMap
    @ViewModelKey(HostViewModel::class)
    abstract fun bindHostViewModel(viewModel: HostViewModel): ViewModel
}