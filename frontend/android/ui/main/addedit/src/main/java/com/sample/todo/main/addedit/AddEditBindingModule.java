package com.sample.todo.main.addedit;


import dagger.Binds;
import dagger.Module;

@Module
interface AddEditBindingModule {
    @Binds
    AddEditViewModel.Factory bind(AddEditViewModel_AssistedFactory factory);
}
