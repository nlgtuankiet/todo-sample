package com.sample.todo.ui.statistics;

import com.sample.todo.ui.addedit.AddEditViewModel;
import com.sample.todo.ui.addedit.AddEditViewModel_AssistedFactory;

import dagger.Binds;
import dagger.Module;

@Module
interface StatisticsBindingModule {
    @Binds
    StatisticsViewModel.Factory bind(StatisticsViewModel_AssistedFactory factory);
}
