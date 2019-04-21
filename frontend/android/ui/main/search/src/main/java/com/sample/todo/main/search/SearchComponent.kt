package com.sample.todo.main.search

import com.sample.todo.base.di.FragmentComponent
import com.sample.todo.base.di.FragmentScoped
import com.sample.todo.base.entity.Holder
import com.sample.todo.base.message.MessageManager
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(
    modules = [
        SearchComponent.Provision::class,
        SearchBindingModule::class
    ]
)
@FragmentScoped
interface SearchComponent : FragmentComponent<SearchFragment> {
    @Subcomponent.Factory
    interface Factory: FragmentComponent.Factory<SearchComponent>

    @Module
    object Provision {
        @JvmStatic
        @Provides
        @FragmentScoped
        fun fragment(
            viewModelFactory: SearchViewModel.Factory,
            messageManager: MessageManager,
            searchController: SearchController,
            holder: Holder<SearchFragment>
        ): SearchFragment {
            return SearchFragment(
                viewModelFactory = viewModelFactory,
                messageManager = messageManager,
                searchController = searchController
            ).also { holder.instance = it }
        }

        @JvmStatic
        @Provides
        @FragmentScoped
        fun holder(): Holder<SearchFragment> = Holder()

        @JvmStatic
        @Provides
        fun viewModel(fragment: SearchFragment): SearchViewModel = fragment.searchViewModel
    }
}