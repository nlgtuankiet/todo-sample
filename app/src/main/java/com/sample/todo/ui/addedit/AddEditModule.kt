package com.sample.todo.ui.addedit

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.sample.todo.di.FragmentScoped
import com.sample.todo.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class AddEditModule {
    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            AddEditBindingModule::class
        ]
    )
    internal abstract fun contributeAddEditFragment(): AddEditFragment
}

@Module
abstract class AddEditBindingModule {
    @Binds
    @IntoMap
    @ViewModelKey(AddEditViewModel::class)
    abstract fun bindAddEditViewModel(viewModel: AddEditViewModel): ViewModel

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideAddEditFragmentArgs(fragment: AddEditFragment): AddEditFragmentArgs {
            return AddEditFragmentArgs.fromBundle(fragment.arguments ?: Bundle.EMPTY)
        }
    }
}