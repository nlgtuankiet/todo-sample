package com.sample.todo.domain.repository

import io.reactivex.Observable

interface SettingsPreferenceRepository {
    fun getNameObservable(): Observable<String>
    suspend fun setName(name: String)
}