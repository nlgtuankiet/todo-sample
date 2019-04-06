package com.sample.todo.domain.repository

import io.reactivex.Observable

interface PreferenceRepository {
    suspend fun getTotalTaskSeeded(): Int
    suspend fun increaseTotalTaskSeeded(amount: Int): Int
    suspend fun getTaskFilterTypeOrdinal(): Int
    suspend fun setTaskFilterTypeOrdinal(value: Int)
    fun getTaskFilterTypeOrdinalObservable(): Observable<Int>
    fun getDataImplementationOrdinal(): Int
    fun setDataImplementationOrdinal(value: Int)
}