package com.sample.todo.ui.search;

import com.sample.todo.ui.addedit.AddEditViewModel;
import com.sample.todo.ui.addedit.AddEditViewModel_AssistedFactory;

import dagger.Binds;
import dagger.Module;

@Module
interface SearchBindingModule {
    @Binds
    SearchViewModel.Factory bind(SearchViewModel_AssistedFactory factory);
}
