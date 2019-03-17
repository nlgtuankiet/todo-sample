package com.sample.todo.ui.taskdetail;

import com.sample.todo.ui.addedit.AddEditViewModel;
import com.sample.todo.ui.addedit.AddEditViewModel_AssistedFactory;

import dagger.Binds;
import dagger.Module;

@Module
interface TaskDetailBindingModule {
    @Binds
    TaskDetailViewModel.Factory bind(TaskDetailViewModel_AssistedFactory factory);
}
