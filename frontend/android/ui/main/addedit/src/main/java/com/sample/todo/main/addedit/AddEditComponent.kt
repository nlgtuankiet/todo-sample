package com.sample.todo.main.addedit

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.todo.base.di.FragmentComponent
import com.sample.todo.base.di.FragmentScoped
import com.sample.todo.base.di.ViewModelKey
import com.sample.todo.base.Holder
import com.sample.todo.base.message.MessageManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(
    modules = [
        AddEditComponent.Provision::class,
        AddEditComponent.Binding::class
    ]
)
@FragmentScoped
interface AddEditComponent : FragmentComponent<AddEditFragment> {
    @Subcomponent.Factory
    interface Factory : FragmentComponent.Factory<AddEditComponent>

    @Module
    object Provision {
        @JvmStatic
        @FragmentScoped
        @Provides
        fun args(holder: Holder<AddEditFragment>): AddEditFragmentArgs {
            return AddEditFragmentArgs.fromBundle(holder.instance.arguments ?: Bundle.EMPTY)
        }

        @JvmStatic
        @FragmentScoped
        @Provides
        fun holder(): Holder<AddEditFragment> = Holder()

        @JvmStatic
        @FragmentScoped
        @Provides
        fun fragment(
            viewModelFactory: ViewModelProvider.Factory,
            messageManager: MessageManager,
            holder: Holder<AddEditFragment>,
            navigator: AddEditNavigator
        ): AddEditFragment {
            return AddEditFragment(
                viewModelFactory = viewModelFactory,
                messageManager = messageManager,
                navigator = navigator
            ).also { holder.instance = it }
        }
    }

    @Module
    interface Binding {

        @Binds
        @IntoMap
        @ViewModelKey(AddEditViewModel::class)
        fun viewModel(instance: AddEditViewModel): ViewModel
    }
}
