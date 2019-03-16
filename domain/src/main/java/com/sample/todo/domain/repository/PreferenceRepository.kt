package com.sample.todo.domain.repository

import io.reactivex.Flowable

interface PreferenceRepository {
    suspend fun getTotalTaskSeeded(): Int
    suspend fun increaseTotalTaskSeeded(amount: Int): Int
    suspend fun getTaskFilterTypeOrdinal(): Int
    suspend fun setTaskFilterTypeOrdinal(value: Int)
    fun getTaskFilterTypeOrdinalFlowable(): Flowable<Int>
}