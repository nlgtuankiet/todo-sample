package com.sample.todo.main.tasks

import com.sample.todo.base.di.FragmentComponent
import com.sample.todo.base.di.FragmentScope
import com.sample.todo.base.Holder
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(
    modules = [
        TasksComponent.Provision::class,
        TasksBindingModule::class
    ]
)
@FragmentScope
interface TasksComponent : FragmentComponent<TasksFragment> {
    @Subcomponent.Factory
    interface Factory : FragmentComponent.Factory<TasksComponent>

    @Module
    object Provision {
        @JvmStatic
        @Provides
        @FragmentScope
        fun fragment(
            holder: Holder<TasksFragment>,
            messageManager: com.sample.todo.domain.repository.MessageManager,
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
        @FragmentScope
        fun holder() = Holder<TasksFragment>()
    }
}
