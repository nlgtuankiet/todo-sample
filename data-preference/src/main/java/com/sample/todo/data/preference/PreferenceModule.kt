package com.sample.todo.data.preference

import android.content.Context
import android.content.SharedPreferences
import com.sample.todo.data.core.DataScope
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        PreferenceRepositoryBindingModule::class,
        PreferenceStorageBindingModule::class
    ]
)
object PreferenceModule {
    @Provides
    @JvmStatic
    @DataScope
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.applicationContext.getSharedPreferences(
            SharedPreferenceStorage.PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }
}