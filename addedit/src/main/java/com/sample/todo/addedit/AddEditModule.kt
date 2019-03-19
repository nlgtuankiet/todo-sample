package com.sample.todo.addedit

import com.sample.todo.base.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AddEditModule {
    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            AddEditBindingModule::class
        ]
    )
    internal abstract fun contributeAddEditFragment(): AddEditFragment
}
