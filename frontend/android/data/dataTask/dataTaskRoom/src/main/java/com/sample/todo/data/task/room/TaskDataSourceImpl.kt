package com.sample.todo.data.task.room

import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toObservable
import com.sample.todo.data.Mapper
import com.sample.todo.data.TaskDataSource
import com.sample.todo.data.core.DataScope
import com.sample.todo.data.task.room.entity.SearchResultEntity
import com.sample.todo.data.task.room.entity.SearchResultStatisticsEntity
import com.sample.todo.data.task.room.entity.TaskEntity
import com.sample.todo.data.task.room.entity.TaskMiniEntity
import com.sample.todo.data.task.room.entity.TaskStatisticsEntity
import com.sample.todo.domain.model.SearchResult
import com.sample.todo.domain.model.SearchResultStatistics
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.model.TaskStatistics
import io.reactivex.Observable
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
    private val searchResultMapper: Mapper<SearchResultEntity, SearchResult>,
    private val searchResultStatisticsEntityMapper: Mapper<SearchResultStatisticsEntity, SearchResultStatistics>
) : TaskDataSource {
    override fun getSearchResultStatisticsObservable(query: String): Observable<SearchResultStatistics> {
        return taskDao.getSearchResultStatisticsObservable(query)
            .map(searchResultStatisticsEntityMapper::map)
    }

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

    override fun getTaskStatisticsObservable(): Observable<TaskStatistics> {
        return taskDao.getTaskStatistics().map(taskStatisticsEntityMapper::map)
    }

    override fun findByIdObservable(id: String): Observable<List<Task>> {
        return taskDao.findByIdObservable(id).map { it.map(taskEntityMapper::map) }
    }

    override fun getTaskMiniObservablePaged(pageSize: Int): Observable<PagedList<TaskMini>> {
        return taskDao
            .getTaskMiniDataSourceFactory()
            .map(taskMiniMapper::map)
            .toObservable(Config(pageSize = pageSize, enablePlaceholders = false))
    }

    override fun getCompletedTaskMiniObservablePaged(pageSize: Int): Observable<PagedList<TaskMini>> {
        return taskDao
            .getCompletedTaskMiniDataSourceFactory()
            .map(taskMiniMapper::map)
            .toObservable(Config(pageSize = pageSize, enablePlaceholders = false))
    }

    override fun getActiveTaskMiniObservablePaged(pageSize: Int): Observable<PagedList<TaskMini>> {
        return taskDao
            .getActiveTaskMiniDataSourceFactory()
            .map(taskMiniMapper::map)
            .toObservable(Config(pageSize = pageSize, enablePlaceholders = false))
    }

    override fun getSearchResultObservablePaged(
        query: String,
        pageSize: Int
    ): Observable<PagedList<SearchResult>> {
        return taskDao
            .getSearchResultDataSourceWith(query)
            .map(searchResultMapper::map)
            .toObservable(pageSize)
    }

    override suspend fun deleteTask(id: String): Long {
        return withContext(Dispatchers.IO) {
            taskDao.deleteTaskAndCountChanges(id)
        }
    }

    override fun tasksCountObservable(): Observable<Long> {
        return taskDao.getTasksCountObservable()
    }

    override suspend fun updateComplete(taskId: String, completed: Boolean, updateTime: Long): Long {
        return withContext(Dispatchers.IO) {
            taskDao.updateCompleteAndCountChanges(taskId, completed, updateTime)
        }
    }
}