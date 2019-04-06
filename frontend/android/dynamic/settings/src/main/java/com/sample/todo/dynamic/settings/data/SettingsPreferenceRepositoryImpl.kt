package com.sample.todo.dynamic.settings.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.sample.todo.data.preference.observableOf
import com.sample.todo.dynamic.settings.domain.repository.SettingsPreferenceRepository
import io.reactivex.Observable
import javax.inject.Inject

class SettingsPreferenceRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SettingsPreferenceRepository {
    override suspend fun setName(name: String) {
        sharedPreferences.edit(commit = true) {
            putString("name", name)
        }
    }

    override fun getNameObservable(): Observable<String> {
        return sharedPreferences.observableOf("name", "")
    }
}