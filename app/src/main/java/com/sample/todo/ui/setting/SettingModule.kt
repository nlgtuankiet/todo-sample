package com.sample.todo.ui.setting

import androidx.lifecycle.ViewModel
import com.sample.todo.di.FragmentScoped
import com.sample.todo.di.ViewModelKey
import com.sample.todo.ui.bottomnavigation.BottomNavigationModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SettingModule {
    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            SettingBindingModule::class,
            SeedInputModule::class,
            BottomNavigationModule::class
        ]
    )
    abstract fun contributeSettingFragment(): SettingFragment
}

@Module
abstract class SettingBindingModule {
    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    abstract fun bindSettingViewModel(viewModel: SettingViewModel): ViewModel
}