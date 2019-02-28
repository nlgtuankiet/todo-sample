package com.sample.todo.settings.domain.repository

import io.reactivex.Observable

interface PreferenceRepository {
    fun getNameObservable(): Observable<String>
    suspend fun setName(name: String)
}