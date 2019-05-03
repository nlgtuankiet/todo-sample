package com.sample.todo.main.tasks.ui;

import dagger.Binds;
import dagger.Module;

@Module
interface TasksBindingModule {
    @Binds
    TasksViewModel.Factory bind(TasksViewModel_AssistedFactory factory);
}
