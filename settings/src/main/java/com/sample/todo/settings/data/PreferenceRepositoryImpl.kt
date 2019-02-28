package com.sample.todo.settings.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.sample.todo.data.preference.observableOf
import com.sample.todo.settings.domain.repository.PreferenceRepository
import io.reactivex.Observable
import javax.inject.Inject

class PreferenceRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): PreferenceRepository {
    override suspend fun setName(name: String) {
        sharedPreferences.edit(commit = true) {
            putString("name", name)
        }
    }

    override fun getNameObservable(): Observable<String> {
        return sharedPreferences.observableOf("name", "")
    }
}