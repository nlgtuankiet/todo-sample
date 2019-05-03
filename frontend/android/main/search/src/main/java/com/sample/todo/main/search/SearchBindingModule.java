package com.sample.todo.main.search;


import dagger.Binds;
import dagger.Module;

@Module
interface SearchBindingModule {
    @Binds
    SearchViewModel.Factory bind(SearchViewModel_AssistedFactory factory);
}
