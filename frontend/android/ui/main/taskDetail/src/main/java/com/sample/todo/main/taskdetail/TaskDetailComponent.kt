package com.sample.todo.main.taskdetail

import android.os.Bundle
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.todo.base.di.FragmentComponent
import com.sample.todo.base.di.FragmentScope
import com.sample.todo.base.di.ViewModelKey
import com.sample.todo.base.Holder
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
@FragmentScope
interface TaskDetailComponent : FragmentComponent<TaskDetailFragment> {
    @Subcomponent.Factory
    interface Factory : FragmentComponent.Factory<TaskDetailComponent>

    @Module
    object Provision {

        @Provides
        @JvmStatic
        @FragmentScope
        fun fragment(
            messageManager: com.sample.todo.domain.repository.MessageManager,
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
        @FragmentScope
        fun holder() = Holder<TaskDetailFragment>()

        @Provides
        @JvmStatic
        @FragmentScope
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
