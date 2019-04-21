package com.sample.todo.domain.repository

import com.sample.todo.domain.entity.DataImplementation

interface DataImplementationRepository {
    fun currentImplementation(): DataImplementation
}
