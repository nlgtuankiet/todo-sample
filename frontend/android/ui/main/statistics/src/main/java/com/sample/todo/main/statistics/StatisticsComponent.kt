package com.sample.todo.main.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.todo.base.di.FragmentComponent
import com.sample.todo.base.di.FragmentScoped
import com.sample.todo.base.di.ViewModelKey
import com.sample.todo.base.Holder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(
    modules = [
        StatisticsComponent.Provision::class,
        StatisticsComponent.Binding::class
    ]
)
@FragmentScoped
interface StatisticsComponent : FragmentComponent<StatisticsFragment> {
    @Subcomponent.Factory
    interface Factory : FragmentComponent.Factory<StatisticsComponent>

    @Module
    object Provision {
        @JvmStatic
        @Provides
        @FragmentScoped
        fun fragment(
            viewModelFactory: ViewModelProvider.Factory,
            holder: Holder<StatisticsFragment>
        ): StatisticsFragment {
            return StatisticsFragment(
                viewModelFactory = viewModelFactory
            ).also { holder.instance = it }
        }

        @JvmStatic
        @Provides
        @FragmentScoped
        fun holder(): Holder<StatisticsFragment> = Holder()
    }

    @Module
    interface Binding {
        @Binds
        @IntoMap
        @ViewModelKey(StatisticsViewModel::class)
        fun bindViewModel(instance: StatisticsViewModel): ViewModel
    }
}
