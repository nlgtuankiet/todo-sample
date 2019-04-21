package com.sample.todo.main.tasks

import com.sample.todo.base.di.FragmentComponent
import com.sample.todo.base.di.FragmentScoped
import com.sample.todo.base.Holder
import com.sample.todo.base.message.MessageManager
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(
    modules = [
        TasksComponent.Provision::class,
        TasksBindingModule::class
    ]
)
@FragmentScoped
interface TasksComponent : FragmentComponent<TasksFragment> {
    @Subcomponent.Factory
    interface Factory : FragmentComponent.Factory<TasksComponent>

    @Module
    object Provision {
        @JvmStatic
        @Provides
        @FragmentScoped
        fun fragment(
            holder: Holder<TasksFragment>,
            messageManager: MessageManager,
            viewModelFactory: TasksViewModel.Factory,
            tasksController: TasksController,
            navigator: TasksNavigator
        ) = TasksFragment(
            messageManager = messageManager,
            viewModelFactory = viewModelFactory,
            tasksController = tasksController,
            navigator = navigator
        ).also { holder.instance = it }

        @JvmStatic
        @Provides
        @FragmentScoped
        fun holder() = Holder<TasksFragment>()
    }
}
