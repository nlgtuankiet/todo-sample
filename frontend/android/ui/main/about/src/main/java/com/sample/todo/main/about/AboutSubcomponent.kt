package com.sample.todo.main.about

import androidx.work.WorkManager
import com.sample.todo.base.di.FragmentScoped
import dagger.Module
import dagger.Provides
import dagger.Subcomponent


@Subcomponent(
    modules = [
        AboutSubcomponent.Provision::class
    ]
)
@FragmentScoped
interface AboutSubcomponent {


    fun fragment(): AboutFragment
    @Subcomponent.Factory
    interface Factory {
        fun create(): AboutSubcomponent
    }


    @Module
    object Provision {
        @JvmStatic
        @Provides
        @FragmentScoped
        fun fragment(
            viewModelFactory: AboutViewModelFactory,
            workManager: WorkManager,
            provider: AboutFragmentProvider,
            navigator: AboutNavigator
        ): AboutFragment {
            return AboutFragment(
                viewModelFactory,
                workManager,
                provider,
                navigator
            )
        }
    }
}