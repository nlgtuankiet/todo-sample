package com.sample.todo.ui.tasks

import androidx.lifecycle.ViewModel
import com.sample.todo.di.FragmentScoped
import com.sample.todo.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

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

@Module
internal abstract class TasksBindingModule {
    @Binds
    @IntoMap
    @ViewModelKey(TasksViewModel::class)
    abstract fun bindTasksViewModel(viewModel: TasksViewModel): ViewModel
}
