package com.sample.todo.main.taskdetail

import android.os.Bundle
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.todo.base.di.FragmentComponent
import com.sample.todo.base.di.FragmentScoped
import com.sample.todo.base.di.ViewModelKey
import com.sample.todo.base.entity.Holder
import com.sample.todo.base.message.MessageManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(
    modules = [
        TaskDetailComponent.Binding::class,
        TaskDetailComponent.Provision::class
    ]
)
@FragmentScoped
interface TaskDetailComponent : FragmentComponent<TaskDetailFragment> {
    @Subcomponent.Factory
    interface Factory : FragmentComponent.Factory<TaskDetailComponent>

    @Module
    object Provision {

        @Provides
        @JvmStatic
        @FragmentScoped
        fun fragment(
            messageManager: MessageManager,
            notificationManager: NotificationManagerCompat,
            viewModelFactory: ViewModelProvider.Factory,
            holder: Holder<TaskDetailFragment>
        ) = TaskDetailFragment(
            messageManager = messageManager,
            notificationManager = notificationManager,
            viewModelFactory = viewModelFactory
        ).also { holder.instance = it }

        @Provides
        @JvmStatic
        @FragmentScoped
        fun holder() = Holder<TaskDetailFragment>()


        @Provides
        @JvmStatic
        @FragmentScoped
        fun args(holder: Holder<TaskDetailFragment>) =
            TaskDetailFragmentArgs.fromBundle(holder.instance.arguments ?: Bundle.EMPTY)
    }

    @Module
    interface Binding {
        @Binds
        @IntoMap
        @ViewModelKey(TaskDetailViewModel::class)
        fun viewModel(instance: TaskDetailViewModel): ViewModel
    }
}