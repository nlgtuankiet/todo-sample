package com.sample.todo.dynamic.settings.data

import com.sample.todo.dynamic.settings.domain.repository.SettingsPreferenceRepository
import dagger.Binds
import dagger.Module

@Module
interface SettingsPreferenceRepositoryBindingModule {
    @Binds
    fun bind(preferenceRepositoryImpl: SettingsPreferenceRepositoryImpl): SettingsPreferenceRepository
}
