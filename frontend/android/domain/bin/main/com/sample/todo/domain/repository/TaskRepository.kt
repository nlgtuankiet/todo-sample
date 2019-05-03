package com.sample.todo.domain.repository

import com.sample.todo.domain.entity.Task

interface TaskRepository {
    suspend fun insertAll(tasks: List<Task>): Long
}
