package com.sample.todo.search;


import dagger.Binds;
import dagger.Module;

@Module
interface SearchBindingModule {
    @Binds
    SearchViewModel.Factory bind(SearchViewModel_AssistedFactory factory);
}
