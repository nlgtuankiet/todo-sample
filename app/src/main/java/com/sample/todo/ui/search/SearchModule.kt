package com.sample.todo.ui.search

import androidx.lifecycle.ViewModel
import com.sample.todo.di.FragmentScoped
import com.sample.todo.di.ViewModelKey
import com.sample.todo.ui.bottomnavigation.BottomNavigationModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SearchModule {
    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            SearchBindingModule::class,
            BottomNavigationModule::class
        ]
    )
    abstract fun contributeSearchFragment(): SearchFragment
}

@Module
abstract class SearchBindingModule {
    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel
//
//    @Module
//    companion object {
//        @Provides
//        @JvmStatic
//        fun provideAddEditFragmentArgs(fragment: AddEditFragment): AddEditFragmentArgs {
//            return AddEditFragmentArgs.fromBundle(fragment.arguments ?: Bundle.EMPTY)
//        }
//    }
}