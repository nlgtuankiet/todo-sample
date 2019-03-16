package com.sample.todo.data

import androidx.paging.PagedList
import com.sample.todo.core.checkAllMatched
import com.sample.todo.data.core.DataScope
import com.sample.todo.domain.model.SearchResult
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskFilterType
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.model.TaskStatistics
import com.sample.todo.domain.repository.TaskRepository
import io.reactivex.Flowable
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

    override fun getTaskWithIdFlowable(id: String): Flowable<List<Task>> {
        return taskDataSource.findByIdFlowable(id)
    }

    override fun getTaskMiniFlowablePaged(
        taskFilterType: TaskFilterType,
        pageSize: Int
    ): Flowable<PagedList<TaskMini>> {
        return when (taskFilterType) {
            TaskFilterType.ALL -> taskDataSource.getTaskMiniFlowablePaged(pageSize)
            TaskFilterType.COMPLETED -> taskDataSource.getCompletedTaskMiniFlowablePaged(pageSize)
            TaskFilterType.ACTIVE -> taskDataSource.getActiveTaskMiniFlowablePaged(pageSize)
        }.checkAllMatched
    }

    override fun getSearchResultFlowablePaged(
        query: String,
        pageSize: Int
    ): Flowable<PagedList<SearchResult>> {
        return taskDataSource.getSearchResultFlowablePaged(query, pageSize)
    }

    override fun getTaskCount(): Flowable<Long> {
        return taskDataSource.tasksCountLive()
    }

    override fun getTaskStatisticsFlowable(): Flowable<TaskStatistics> {
        return taskDataSource.getTaskStatisticsFlowable()
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