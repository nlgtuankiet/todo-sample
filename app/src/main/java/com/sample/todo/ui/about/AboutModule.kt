package com.sample.todo.ui.about

import com.sample.todo.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class AboutModule {
    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
        ]
    )
    internal abstract fun contributeFragment(): AboutFragment
}
