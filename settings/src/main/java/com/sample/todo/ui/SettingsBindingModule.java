package com.sample.todo.ui;

import com.sample.todo.ui.tasks.TasksViewModel;
import com.sample.todo.ui.tasks.TasksViewModel_AssistedFactory;

import dagger.Binds;
import dagger.Module;

@Module
interface SettingsBindingModule {
    @Binds
    SettingsViewModel.Factoryy bind(SettingsViewModel_AssistedFactory factory);
}
