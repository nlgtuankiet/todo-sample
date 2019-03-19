package com.sample.todo.tasks;

import dagger.Binds;
import dagger.Module;

@Module
interface TasksBindingModule {
    @Binds
    TasksViewModel.Factory bind(TasksViewModel_AssistedFactory factory);
}
