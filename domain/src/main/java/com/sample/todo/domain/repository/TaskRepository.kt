package com.sample.todo.domain.repository

import androidx.paging.PagedList
import com.sample.todo.domain.model.SearchResult
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskFilterType
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.model.TaskStatistics
import io.reactivex.Flowable

interface TaskRepository {
    /**
     * @return number of row inserted
     */
    suspend fun insert(entity: Task): Long

    /**
     * @return number of row inserted
     */
    suspend fun insertAll(entities: List<Task>): Long

    suspend fun getTask(taskId: String): Task?

    /**
     * @return number of row updated
     */
    suspend fun updateComplete(taskId: String, completed: Boolean): Long

    /**
     * @return number of row updated
     */
    suspend fun update(task: Task): Long

    fun getTaskStatisticsFlowable(): Flowable<TaskStatistics>

    fun getTaskCount(): Flowable<Long>

    fun getTaskMiniFlowablePaged(taskFilterType: TaskFilterType, pageSize: Int): Flowable<PagedList<TaskMini>>

    fun getSearchResultFlowablePaged(query: String, pageSize: Int): Flowable<PagedList<SearchResult>>

    fun getTaskWithIdFlowable(id: String): Flowable<List<Task>>

    /**
     * @return number of row deleted
     */
    suspend fun deleteTask(id: String): Long
}