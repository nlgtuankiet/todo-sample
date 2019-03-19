package com.sample.todo.statistics;

import dagger.Binds;
import dagger.Module;

@Module
interface StatisticsBindingModule {
    @Binds
    StatisticsViewModel.Factory bind(StatisticsViewModel_AssistedFactory factory);
}
