package com.sample.todo.main.about.library.repository

import com.sample.todo.domain.entity.Task

interface AddEditRepository {
    suspend fun getTask(value: String): Task?
    suspend fun insert(entity: Task): Long
    suspend fun update(task: Task): Long
}