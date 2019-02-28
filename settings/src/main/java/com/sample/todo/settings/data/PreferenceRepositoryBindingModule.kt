package com.sample.todo.settings.data

import com.sample.todo.settings.domain.repository.PreferenceRepository
import dagger.Binds
import dagger.Module

@Module
interface PreferenceRepositoryBindingModule {
    @Binds
    fun bind(preferenceRepositoryImpl: PreferenceRepositoryImpl): PreferenceRepository
}