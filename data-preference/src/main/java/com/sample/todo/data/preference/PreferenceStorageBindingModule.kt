package com.sample.todo.data.preference

import dagger.Binds
import dagger.Module

@Module
interface PreferenceStorageBindingModule {
    @Binds
    fun bind(preferenceStorage: SharedPreferenceStorage): PreferenceStorage
}