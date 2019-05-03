package com.sample.todo.main.about

import androidx.work.WorkManager
import com.sample.todo.base.di.FragmentComponent
import com.sample.todo.base.di.FragmentScope
import com.sample.todo.base.Holder
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(
    modules = [
        AboutComponent.Provision::class
    ]
)
@FragmentScope
interface AboutComponent : FragmentComponent<AboutFragment> {

    @Subcomponent.Factory
    interface Factory : FragmentComponent.Factory<AboutComponent>

    @Module
    object Provision {
        @JvmStatic
        @Provides
        @FragmentScope
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
        @FragmentScope
        fun holder(): Holder<AboutFragment> = Holder()
    }
}
