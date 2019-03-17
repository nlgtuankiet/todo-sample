package com.sample.todo.ui.search

import com.sample.todo.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchModule {
    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            SearchBindingModule::class
        ]
    )
    abstract fun contributeSearchFragment(): SearchFragment
}
