package com.sample.todo.settings.ui;

import dagger.Binds;
import dagger.Module;

@Module
interface SettingsBindingModule {
    @Binds
    SettingsViewModel.Factory bind(SettingsViewModel_AssistedFactory factory);
}
