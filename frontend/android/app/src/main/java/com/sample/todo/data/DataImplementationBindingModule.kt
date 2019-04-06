package com.sample.todo.data

import com.sample.todo.domain.repository.DataImplementationRepository
import com.sample.todo.domain.repository.DataPreferenceRepository
import dagger.Binds
import dagger.Module

@Module
interface DataImplementationBindingModule {
    @Binds
    fun bind(impl: DataImplementationRepositoryImpl): DataImplementationRepository
}