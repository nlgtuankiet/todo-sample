package com.sample.todo.data

import com.sample.todo.domain.repository.DataPreferenceRepository
import dagger.Binds
import dagger.Module

@Module
interface DataPreferenceBindingModule {
    @Binds
    fun bind(impl: DataPreferenceRepositoryImpl): DataPreferenceRepository
}
