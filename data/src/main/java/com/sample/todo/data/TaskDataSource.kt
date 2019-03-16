package com.sample.todo.data

import androidx.paging.PagedList
import com.sample.todo.domain.model.SearchResult
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.model.TaskStatistics
import io.reactivex.Flowable

interface TaskDataSource {
    suspend fun findTaskById(taskId: String): Task?
    suspend fun insert(entity: Task): Long
    suspend fun insertAll(entities: List<Task>): Long
    suspend fun updateComplete(taskId: String, completed: Boolean): Long
    suspend fun update(task: Task): Long
    fun getTaskStatisticsFlowable(): Flowable<TaskStatistics>
    fun tasksCountLive(): Flowable<Long>

    fun getTaskMiniFlowablePaged(pageSize: Int): Flowable<PagedList<TaskMini>>
    fun getCompletedTaskMiniFlowablePaged(pageSize: Int): Flowable<PagedList<TaskMini>>
    fun getActiveTaskMiniFlowablePaged(pageSize: Int): Flowable<PagedList<TaskMini>>
    fun getSearchResultFlowablePaged(query: String, pageSize: Int): Flowable<PagedList<SearchResult>>

    fun findByIdFlowable(id: String): Flowable<List<Task>>
    suspend fun deleteTask(id: String): Long
}