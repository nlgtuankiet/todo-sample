package com.sample.todo.work.seeddatabase;

import com.sample.todo.base.di.WorkerKey;
import com.sample.todo.work.ListenableWorkerFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public interface SeedDatabaseWorkerFactoryBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(SeedDatabaseWorker.class)
    ListenableWorkerFactory bindSeedDatabaseWorker(SeedDatabaseWorker_AssistedFactory factory);
}
