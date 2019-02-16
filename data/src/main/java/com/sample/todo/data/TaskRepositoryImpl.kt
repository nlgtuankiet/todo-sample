package com.sample.todo.data

import androidx.paging.PagedList
import com.sample.todo.core.checkAllMatched
import com.sample.todo.data.core.DataScope
import com.sample.todo.domain.model.SearchResult
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskFilterType
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.model.TaskStat
import com.sample.todo.domain.repository.TaskRepository
import io.reactivex.Observable
import kotlinx.coroutines.delay
import javax.inject.Inject

@DataScope
class TaskRepositoryImpl @Inject constructor(
    private val taskDataSource: TaskDataSource
) : TaskRepository {
    override suspend fun deleteTask(id: String): Long {
        delay(2000) // fake load
        return taskDataSource.deleteTask(id)
    }

    override fun getTaskWithId(id: String): Observable<List<Task>> {
        return taskDataSource.findByIdFlowable(id)
    }

    override fun getTaskMini(
        taskFilterType: TaskFilterType,
        pageSize: Int
    ): Observable<PagedList<TaskMini>> {
        return when (taskFilterType) {
            TaskFilterType.ALL -> taskDataSource.getTaskMini(pageSize)
            TaskFilterType.COMPLETED -> taskDataSource.getCompletedTaskMini(pageSize)
            TaskFilterType.ACTIVE -> taskDataSource.getActiveTaskMini(pageSize)
        }.checkAllMatched
    }

    override fun getSearchResult(
        query: String,
        pageSize: Int
    ): Observable<PagedList<SearchResult>> {
        return taskDataSource.getSearchResultPaged(query, pageSize)
    }

    override fun getTaskCount(): Observable<Long> {
        return taskDataSource.tasksCountLive()
    }

    override fun getTaskStatistics(): Observable<TaskStat> {
        return taskDataSource.taskStat()
    }

    override suspend fun update(task: Task): Long {
        return taskDataSource.update(task)
    }

    override suspend fun insertAll(entities: List<Task>): Long {
        return taskDataSource.insertAll(entities)
    }

    override suspend fun updateComplete(taskId: String, completed: Boolean): Long {
        return taskDataSource.updateComplete(taskId, completed)
    }

    override suspend fun getTask(taskId: String): Task? {
        delay(2000) // fake load
        return taskDataSource.findTaskById(taskId)
    }

    override suspend fun insert(entity: Task): Long {
        return taskDataSource.insert(entity)
    }
}