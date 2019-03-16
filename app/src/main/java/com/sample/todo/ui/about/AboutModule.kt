package com.sample.todo.ui.about

import androidx.lifecycle.ViewModel
import com.sample.todo.di.FragmentScoped
import com.sample.todo.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class AboutModule {
    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            AboutBindingModule::class
        ]
    )
    internal abstract fun contributeFragment(): AboutFragment
}

@Module
abstract class AboutBindingModule {
    @Binds
    @IntoMap
    @ViewModelKey(AboutViewModel::class)
    abstract fun bindViewModel(viewModel: AboutViewModel): ViewModel
}