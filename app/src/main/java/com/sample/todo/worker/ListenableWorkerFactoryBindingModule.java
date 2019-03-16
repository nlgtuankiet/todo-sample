package com.sample.todo.worker;

import com.sample.todo.di.WorkerKey;
import com.sample.todo.worker.downloadmodule.DownloadModuleWorker;
import com.sample.todo.worker.downloadmodule.DownloadModuleWorker_AssistedFactory;
import com.sample.todo.worker.seeddatabase.SeedDatabaseWorker;
import com.sample.todo.worker.seeddatabase.SeedDatabaseWorker_AssistedFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


// TODO bug report, cannot reproduce same setup with kotlin
@Module
public interface ListenableWorkerFactoryBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(SeedDatabaseWorker.class)
    ListenableWorkerFactory bindSeedDatabaseWorker(SeedDatabaseWorker_AssistedFactory factory);

    @Binds
    @IntoMap
    @WorkerKey(DownloadModuleWorker.class)
    ListenableWorkerFactory bindDownloadModuleWorker(DownloadModuleWorker_AssistedFactory factory);
}
