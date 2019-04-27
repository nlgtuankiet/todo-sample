package com.sample.todo.data.preference

import com.sample.todo.domain.repository.PreferenceRepository
import dagger.Binds
import dagger.Module

@Module
interface PreferenceRepositoryBindingModule {
    @Binds
    fun bind(preferenceRepository: PreferenceRepositoryImpl): PreferenceRepository
}
