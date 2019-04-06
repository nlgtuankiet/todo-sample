@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.sample.todo.data.preference

import io.reactivex.Observable

// TODO how to make android studio notify about exception could be throw?
interface PreferenceStorage {
    suspend fun getTotalTaskSeeded(): Int
    suspend fun increaseTotalTaskSeeded(amount: Int): Int
    suspend fun getTaskFilterTypeOrdinal(): Int
    suspend fun setTaskFilterTypeOrdinal(value: Int)
    fun getTaskFilterTypeOrdinalObservable(): Observable<Int>
    fun getDataImplementationOrdinal(): Int
    fun setDataImplementationOrdinal(value: Int)
}

