package com.sample.todo.ui.addedit

import com.sample.todo.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

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
