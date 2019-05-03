package com.sample.todo.main.taskdetail.library.domain.repository

import com.sample.todo.domain.entity.Task
import io.reactivex.Observable

interface TaskDetailRepository {
    suspend fun deleteTask(id: String): Long
    fun getTaskWithIdObservable(id: String): Observable<List<Task>>
    suspend fun updateComplete(taskId: String, completed: Boolean, updateTime: Long): Long
}