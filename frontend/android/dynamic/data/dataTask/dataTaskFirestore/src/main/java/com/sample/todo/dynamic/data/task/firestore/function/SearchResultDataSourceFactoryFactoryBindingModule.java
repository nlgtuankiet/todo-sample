package com.sample.todo.dynamic.data.task.firestore.function;

import dagger.Binds;
import dagger.Module;

@Module
public interface SearchResultDataSourceFactoryFactoryBindingModule {
    @Binds
    SearchResultDataSourcefactory.Factory bind(SearchResultDataSourcefactory_AssistedFactory factory);
}
