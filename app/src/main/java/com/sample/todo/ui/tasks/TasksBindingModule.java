package com.sample.todo.ui.tasks;

import com.sample.todo.ui.addedit.AddEditViewModel;
import com.sample.todo.ui.addedit.AddEditViewModel_AssistedFactory;

import dagger.Binds;
import dagger.Module;

@Module
interface TasksBindingModule {
    @Binds
    TasksViewModel.Factory bind(TasksViewModel_AssistedFactory factory);
}
