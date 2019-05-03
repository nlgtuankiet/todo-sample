package com.sample.todo.main.addedit

import android.os.Bundle
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
        AddEditComponent.Provision::class,
        AddEditComponent.Binding::class
    ]
)
@FragmentScope
interface AddEditComponent : FragmentComponent<AddEditFragment> {
    @Subcomponent.Factory
    interface Factory : FragmentComponent.Factory<AddEditComponent>

    @Module
    object Provision {
        @JvmStatic
        @FragmentScope
        @Provides
        fun args(holder: Holder<AddEditFragment>): AddEditFragmentArgs {
            return AddEditFragmentArgs.fromBundle(holder.instance.arguments ?: Bundle.EMPTY)
        }

        @JvmStatic
        @FragmentScope
        @Provides
        fun holder(): Holder<AddEditFragment> = Holder()

        @JvmStatic
        @FragmentScope
        @Provides
        fun fragment(
            viewModelFactory: ViewModelProvider.Factory,
            messageManager: com.sample.todo.domain.repository.MessageManager,
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
