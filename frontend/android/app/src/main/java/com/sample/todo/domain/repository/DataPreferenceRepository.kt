package com.sample.todo.domain.repository

import com.sample.todo.domain.entity.DataImplementation

interface DataPreferenceRepository {
    fun setDataImplementationOrdinal(ordinal: Int)
    fun getDataImplementationOrdinal(): Int
}