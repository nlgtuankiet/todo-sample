package com.sample.todo.data

import android.content.SharedPreferences
import com.sample.todo.di.application.ApplicationScope
import com.sample.todo.data.preference.IntPreference
import com.sample.todo.domain.repository.DataPreferenceRepository
import javax.inject.Inject

@ApplicationScope
class DataPreferenceRepositoryImpl @Inject constructor(
    sharedPreferences: SharedPreferences
) : DataPreferenceRepository {
    private var _dataImplementationOrdinal by IntPreference(
        sharedPreferences,
        DATA_IMPL_KEY, -1
    )

    override fun setDataImplementationOrdinal(ordinal: Int) {
        _dataImplementationOrdinal = ordinal
    }

    override fun getDataImplementationOrdinal(): Int {
        return _dataImplementationOrdinal
    }

    companion object {
        private const val DATA_IMPL_KEY = "data_impl"
    }
}
