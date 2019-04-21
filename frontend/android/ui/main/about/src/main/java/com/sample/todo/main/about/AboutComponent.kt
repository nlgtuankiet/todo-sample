package com.sample.todo.main.about

import androidx.work.WorkManager
import com.sample.todo.base.di.FragmentComponent
import com.sample.todo.base.di.FragmentScoped
import com.sample.todo.base.entity.Holder
import dagger.Module
import dagger.Provides
import dagger.Subcomponent


@Subcomponent(
    modules = [
        AboutComponent.Provision::class
    ]
)
@FragmentScoped
interface AboutComponent : FragmentComponent<AboutFragment> {

    @Subcomponent.Factory
    interface Factory: FragmentComponent.Factory<AboutComponent>

    @Module
    object Provision {
        @JvmStatic
        @Provides
        @FragmentScoped
        fun fragment(
            viewModelFactory: AboutViewModelFactory,
            workManager: WorkManager,
            holder: Holder<AboutFragment>,
            navigator: AboutNavigator
        ): AboutFragment {
            return AboutFragment(
                viewModelFactory,
                workManager,
                holder,
                navigator
            )
        }
        @JvmStatic
        @Provides
        @FragmentScoped
        fun holder(): Holder<AboutFragment> = Holder()
    }
}