package com.sample.todo.ui.bottomnavigation

import androidx.lifecycle.ViewModel
import com.sample.todo.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class BottomNavigationModule {
    @ContributesAndroidInjector(
        modules = [
            BottomNavigationBindingModule::class
        ]
    )
    internal abstract fun contributeBottomNavigationFragment(): BottomNavigationFragment
}

@Module
internal abstract class BottomNavigationBindingModule {
    @Binds
    @IntoMap
    @ViewModelKey(BottomNavigationViewModel::class)
    internal abstract fun bindBottomNavigationViewModel(viewModel: BottomNavigationViewModel): ViewModel
}