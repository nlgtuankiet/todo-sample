package com.sample.todo.data

import com.sample.todo.domain.repository.SettingsPreferenceRepository
import dagger.Binds
import dagger.Module

@Module
interface SettingsPreferenceRepositoryBindingModule {
    @Binds
    fun bind(preferenceRepositoryImpl: SettingsPreferenceRepositoryImpl): SettingsPreferenceRepository
}