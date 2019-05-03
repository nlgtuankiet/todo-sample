package com.sample.todo.main.tasks.library.domain.repository

import androidx.paging.PagedList
import com.sample.todo.main.tasks.library.domain.entity.TaskFilterType
import com.sample.todo.main.tasks.library.domain.entity.TaskMini
import io.reactivex.Observable

interface TasksRepository {
    fun getTaskFilterTypeOrdinalObservable(): Observable<Int>
    fun getTasksObservablePaged(taskFilterType: TaskFilterType, pageSize: Int): Observable<PagedList<TaskMini>>
    suspend fun setTaskFilterTypeOrdinal(value: Int)
    suspend fun getTaskFilterTypeOrdinal(): Int
}