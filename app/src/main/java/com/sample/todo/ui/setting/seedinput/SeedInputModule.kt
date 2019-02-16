package com.sample.todo.ui.setting

import androidx.lifecycle.ViewModel
import com.sample.todo.di.ViewModelKey
import com.sample.todo.ui.setting.seedinput.SeedInputScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SeedInputModule {
    @SeedInputScoped
    @ContributesAndroidInjector(
        modules = [
            SeedInputBindingModule::class
        ]
    )
    abstract fun contributeSeedInputFragment(): SeedInputFragment
}

@Module
abstract class SeedInputBindingModule {
    @Binds
    @IntoMap
    @ViewModelKey(SeedInputViewModel::class)
    abstract fun bindSeedInputViewModel(viewModel: SeedInputViewModel): ViewModel
}