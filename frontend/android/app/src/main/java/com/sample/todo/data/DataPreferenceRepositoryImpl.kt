package com.sample.todo.data

import android.content.Context

import android.content.SharedPreferences
import com.sample.todo.TodoApplication
import com.sample.todo.base.di.ApplicationScope
import com.sample.todo.data.preference.IntPreference
import com.sample.todo.domain.repository.DataPreferenceRepository
import javax.inject.Inject

@ApplicationScope
class DataPreferenceRepositoryImpl @Inject constructor(
    private val application: TodoApplication
): DataPreferenceRepository {
    private val sharedPreferences: SharedPreferences by lazy {
        application.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }
    private var _dataImplementationOrdinal by IntPreference(sharedPreferences, DATA_IMPL_KEY, -1)

    override fun setDataImplementationOrdinal(ordinal: Int) {
        _dataImplementationOrdinal = ordinal
    }

    override fun getDataImplementationOrdinal(): Int {
        return _dataImplementationOrdinal
    }
    companion object {
        private const val SHARE_PREFERENCE_NAME = "data_implementation"
        private const val DATA_IMPL_KEY = "data_impl"
    }
}