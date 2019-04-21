package com.sample.todo.domain.repository

interface DataPreferenceRepository {
    fun setDataImplementationOrdinal(ordinal: Int)
    fun getDataImplementationOrdinal(): Int
}
