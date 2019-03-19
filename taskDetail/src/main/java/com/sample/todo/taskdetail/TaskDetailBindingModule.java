package com.sample.todo.taskdetail;

import dagger.Binds;
import dagger.Module;

@Module
interface TaskDetailBindingModule {
    @Binds
    TaskDetailViewModel.Factory bind(TaskDetailViewModel_AssistedFactory factory);
}
