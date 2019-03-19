package com.sample.todo.search

import com.sample.todo.base.di.FragmentScoped
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
