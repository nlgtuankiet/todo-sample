package com.sample.todo.data.room

import androidx.paging.PagedList
import androidx.paging.toFlowable
import com.sample.todo.data.Mapper
import com.sample.todo.data.TaskDataSource
import com.sample.todo.data.core.DataScope
import com.sample.todo.data.room.entity.SearchResultEntity
import com.sample.todo.data.room.entity.TaskEntity
import com.sample.todo.data.room.entity.TaskMiniEntity
import com.sample.todo.data.room.entity.TaskStatisticsEntity
import com.sample.todo.domain.model.SearchResult
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.model.TaskStatistics
import io.reactivex.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * for Rx return type, no need to specify background scheduler other than main thread because is
 * already set in Room when at creation time
 *      Ref: room/rxjava2/RxRoom.java
 *      Scheduler scheduler = Schedulers.from(database.getQueryExecutor());
 */
@DataScope
class TaskDataSourceImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val taskEntityMapper: Mapper<TaskEntity, Task>,
    private val taskStatisticsEntityMapper: Mapper<TaskStatisticsEntity, TaskStatistics>,
    private val taskMapper: Mapper<Task, TaskEntity>,
    private val taskMiniMapper: Mapper<TaskMiniEntity, TaskMini>,
    private val searchResultMapper: Mapper<SearchResultEntity, SearchResult>
) : TaskDataSource {
    override suspend fun findTaskById(taskId: String): Task? {
        return withContext(Dispatchers.IO) {
            taskDao.findById(taskId)?.let { taskEntityMapper.map(it) }
        }
    }

    override suspend fun insert(entity: Task): Long {
        return withContext(Dispatchers.IO) {
            taskDao.insertAndCountChanges(taskMapper.map(entity))
        }
    }

    override suspend fun insertAll(entities: List<Task>): Long {
        return withContext(Dispatchers.IO) {
            taskDao.insertAll(entities.map(taskMapper::map))
            return@withContext entities.size.toLong()
        }
    }

    override suspend fun update(task: Task): Long {
        return withContext(Dispatchers.IO) {
            taskDao.updateAndCountChanges(taskMapper.map(task))
        }
    }

    override fun getTaskStatisticsFlowable(): Flowable<TaskStatistics> {
        return taskDao.getTaskStatistics().map(taskStatisticsEntityMapper::map)
    }

    override fun findByIdFlowable(id: String): Flowable<List<Task>> {
        return taskDao.findByIdFlowable(id).map { it.map(taskEntityMapper::map) }
    }

    override fun getTaskMiniFlowablePaged(pageSize: Int): Flowable<PagedList<TaskMini>> {
        return taskDao
            .getTaskMiniDataSourceFactory()
            .map(taskMiniMapper::map)
            .toFlowable(pageSize)
    }

    override fun getCompletedTaskMiniFlowablePaged(pageSize: Int): Flowable<PagedList<TaskMini>> {
        return taskDao
            .getCompletedTaskMiniDataSourceFactory()
            .map(taskMiniMapper::map)
            .toFlowable(pageSize)
    }

    override fun getActiveTaskMiniFlowablePaged(pageSize: Int): Flowable<PagedList<TaskMini>> {
        return taskDao
            .getActiveTaskMiniDataSourceFactory()
            .map(taskMiniMapper::map)
            .toFlowable(pageSize)
    }

    override fun getSearchResultFlowablePaged(
        query: String,
        pageSize: Int
    ): Flowable<PagedList<SearchResult>> {
        return taskDao
            .getSearchResultDataSourceWith(query)
            .map(searchResultMapper::map)
            .toFlowable(pageSize)
    }

    override suspend fun deleteTask(id: String): Long {
        return withContext(Dispatchers.IO) {
            taskDao.deleteTaskAndCountChanges(id)
        }
    }

    override fun tasksCountLive(): Flowable<Long> {
        return taskDao.getTasksCountFlowable()
    }

    override suspend fun updateComplete(taskId: String, completed: Boolean): Long {
        return withContext(Dispatchers.IO) {
            taskDao.updateCompleteAndCountChanges(taskId, completed)
        }
    }
}