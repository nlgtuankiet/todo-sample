package com.sample.todo.work.downloadmodule;

import com.sample.todo.base.di.WorkerKey;
import com.sample.todo.work.ListenableWorkerFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public interface DownloadModuleWorkerFactoryBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(DownloadModuleWorker.class)
    ListenableWorkerFactory bindDownloadModuleWorker(DownloadModuleWorker_AssistedFactory factory);
}
