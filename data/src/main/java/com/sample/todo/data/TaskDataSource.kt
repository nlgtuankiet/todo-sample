package com.sample.todo.data

import androidx.paging.PagedList
import com.sample.todo.domain.model.SearchResult
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.model.TaskStat
import io.reactivex.Observable

interface TaskDataSource {
    suspend fun findTaskById(taskId: String): Task?
    suspend fun insert(entity: Task): Long
    suspend fun insertAll(entities: List<Task>): Long
    suspend fun updateComplete(taskId: String, completed: Boolean): Long
    suspend fun update(task: Task): Long
    fun taskStat(): Observable<TaskStat>
    fun tasksCountLive(): Observable<Long>

    fun getTaskMini(pageSize: Int): Observable<PagedList<TaskMini>>
    fun getCompletedTaskMini(pageSize: Int): Observable<PagedList<TaskMini>>
    fun getActiveTaskMini(pageSize: Int): Observable<PagedList<TaskMini>>
    fun getSearchResultPaged(query: String, pageSize: Int): Observable<PagedList<SearchResult>>

    fun findByIdFlowable(id: String): Observable<List<Task>>
    suspend fun deleteTask(id: String): Long
}