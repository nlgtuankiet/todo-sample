package com.sample.todo.ui.taskdetail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.sample.todo.di.FragmentScoped
import com.sample.todo.di.ViewModelKey
import com.sample.todo.ui.bottomnavigation.BottomNavigationModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class TaskDetailModule {
    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            TaskDetailBindingModule::class,
            BottomNavigationModule::class
        ]
    )
    abstract fun contributeTaskDetailFragment(): TaskDetailFragment
}

@Module
abstract class TaskDetailBindingModule {
    @Binds
    @IntoMap
    @ViewModelKey(TaskDetailViewModel::class)
    abstract fun bindTaskDetailViewModel(viewModel: TaskDetailViewModel): ViewModel

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideTaskDetailFragmentArgs(fragment: TaskDetailFragment): TaskDetailFragmentArgs =
            TaskDetailFragmentArgs.fromBundle(fragment.arguments ?: Bundle.EMPTY)
    }
}
