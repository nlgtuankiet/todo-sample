package com.sample.todo.main.search

import com.sample.todo.base.di.FragmentComponent
import com.sample.todo.base.di.FragmentScope
import com.sample.todo.base.Holder
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(
    modules = [
        SearchComponent.Provision::class,
        SearchBindingModule::class
    ]
)
@FragmentScope
interface SearchComponent : FragmentComponent<SearchFragment> {
    @Subcomponent.Factory
    interface Factory : FragmentComponent.Factory<SearchComponent>

    @Module
    object Provision {
        @JvmStatic
        @Provides
        @FragmentScope
        fun fragment(
            viewModelFactory: SearchViewModel.Factory,
            messageManager: com.sample.todo.domain.repository.MessageManager,
            searchController: SearchController,
            holder: Holder<SearchFragment>,
            navigator: SearchNavigator
        ): SearchFragment {
            return SearchFragment(
                viewModelFactory = viewModelFactory,
                messageManager = messageManager,
                controller = searchController,
                navigator = navigator
            ).also { holder.instance = it }
        }

        @JvmStatic
        @Provides
        @FragmentScope
        fun holder(): Holder<SearchFragment> = Holder()

//        @JvmStatic
//        @Provides
//        fun viewModel(holder: Holder<SearchFragment>): SearchViewModel = holder.instance.searchViewModel
    }
}
