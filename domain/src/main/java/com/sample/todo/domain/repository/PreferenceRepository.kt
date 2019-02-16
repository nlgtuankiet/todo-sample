package com.sample.todo.domain.repository

import io.reactivex.Observable

interface PreferenceRepository {
    fun getTotalTaskSeeded(): Int
    fun increaseTotalTaskSeeded(amount: Int): Int
    fun getTaskFilterTypeOrdinal(): Int
    fun setTaskFilterTypeOrdinal(value: Int)
    fun getTaskFilterTypeOrdinalFlowable(): Observable<Int>
}