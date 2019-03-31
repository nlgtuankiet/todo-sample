package com.sample.todo.main.about

import com.sample.todo.base.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AboutModule {
    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
        ]
    )
    internal abstract fun contributeFragment(): AboutFragment
}
