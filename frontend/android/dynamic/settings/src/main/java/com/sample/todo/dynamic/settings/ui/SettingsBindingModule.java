package com.sample.todo.dynamic.settings.ui;

import com.sample.todo.dynamic.settings.ui.SettingsViewModel_AssistedFactory;
import dagger.Binds;
import dagger.Module;

@Module
interface SettingsBindingModule {
    @Binds
    SettingsViewModel.Factory bind(SettingsViewModel_AssistedFactory factory);
}
