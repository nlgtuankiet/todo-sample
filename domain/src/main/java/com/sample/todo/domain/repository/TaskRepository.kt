package com.sample.todo.domain.repository

import androidx.paging.PagedList
import com.sample.todo.domain.model.SearchResult
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskFilterType
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.model.TaskStat
import io.reactivex.Observable

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

    fun getTaskStatistics(): Observable<TaskStat>

    fun getTaskCount(): Observable<Long>

    fun getTaskMini(taskFilterType: TaskFilterType, pageSize: Int): Observable<PagedList<TaskMini>>

    fun getSearchResult(query: String, pageSize: Int): Observable<PagedList<SearchResult>>

    fun getTaskWithId(id: String): Observable<List<Task>>

    /**
     * @return number of row deleted
     */
    suspend fun deleteTask(id: String): Long
}