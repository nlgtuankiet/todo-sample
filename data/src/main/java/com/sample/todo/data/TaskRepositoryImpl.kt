package com.sample.todo.data

import androidx.paging.PagedList
import com.sample.todo.core.checkAllMatched
import com.sample.todo.data.core.DataScope
import com.sample.todo.domain.model.SearchResult
import com.sample.todo.domain.model.SearchResultStatistics
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskFilterType
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.model.TaskStatistics
import com.sample.todo.domain.repository.TaskRepository
import io.reactivex.Observable
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

@DataScope
class TaskRepositoryImpl @Inject constructor(
    private val taskDataSource: TaskDataSource
) : TaskRepository {
    override fun getSearchResultStatisticsObservable(query: String): Observable<SearchResultStatistics> {
        return taskDataSource.getSearchResultStatisticsObservable(query)
    }

    override suspend fun deleteTask(id: String): Long {
        delay(2000) // fake load
        return taskDataSource.deleteTask(id)
    }

    override fun getTaskWithIdObservable(id: String): Observable<List<Task>> {
        return taskDataSource.findByIdObservable(id)
    }

    override fun getTasksObservablePaged(
        taskFilterType: TaskFilterType,
        pageSize: Int
    ): Observable<PagedList<TaskMini>> {
        Timber.d("getTasksObservablePaged(taskFilterType=$taskFilterType, pageSize=$pageSize)")
        return when (taskFilterType) {
            TaskFilterType.ALL -> taskDataSource.getTaskMiniObservablePaged(pageSize)
            TaskFilterType.COMPLETED -> taskDataSource.getCompletedTaskMiniObservablePaged(pageSize)
            TaskFilterType.ACTIVE -> taskDataSource.getActiveTaskMiniObservablePaged(pageSize)
        }.checkAllMatched
    }

    override fun getSearchResultObservablePaged(
        query: String,
        pageSize: Int
    ): Observable<PagedList<SearchResult>> {
        return taskDataSource.getSearchResultObservablePaged(query, pageSize)
    }

    override fun getTaskCount(): Observable<Long> {
        return taskDataSource.tasksCountObservable()
    }

    override fun getTaskStatisticsObservable(): Observable<TaskStatistics> {
        return taskDataSource.getTaskStatisticsObservable()
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