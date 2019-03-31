package com.sample.todo.main.statistics;

import dagger.Binds;
import dagger.Module;

@Module
interface StatisticsBindingModule {
    @Binds
    StatisticsViewModel.Factory bind(StatisticsViewModel_AssistedFactory factory);
}
