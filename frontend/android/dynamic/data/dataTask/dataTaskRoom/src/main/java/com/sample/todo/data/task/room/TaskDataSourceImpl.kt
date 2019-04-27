package com.sample.todo.data.task.room

import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toObservable
import com.sample.todo.data.TaskDataSource
import com.sample.todo.data.core.DataScope
import com.sample.todo.data.core.Mapper
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
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
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
    private val taskEntityToTask: Mapper<TaskEntity, Task>,
    private val taskStatisticsEntityToTaskStatistics: Mapper<TaskStatisticsEntity, TaskStatistics>,
    private val taskToTaskEntity: Mapper<Task, TaskEntity>,
    private val taskMiniEntityToTaskMini: Mapper<TaskMiniEntity, TaskMini>,
    private val searchResultEntityToSearchResult: Mapper<SearchResultEntity, SearchResult>,
    private val searchResultStatisticsEntityToSearchResultStatistics: Mapper<SearchResultStatisticsEntity, SearchResultStatistics>
) : TaskDataSource {
    private val ex = Executors.newSingleThreadExecutor()

    override fun getSearchResultStatisticsObservable(query: String): Observable<SearchResultStatistics> {
        return taskDao.getSearchResultStatisticsObservable(query)
            .map(searchResultStatisticsEntityToSearchResultStatistics::invoke)
    }

    override suspend fun findTaskById(taskId: String): Task? {
        return withContext(Dispatchers.IO) {
            taskDao.findById(taskId)?.let { taskEntityToTask(it) }
        }
    }

    override suspend fun insert(entity: Task): Long {
        return withContext(Dispatchers.IO) {
            taskDao.insertAndCountChanges(taskToTaskEntity(entity))
        }
    }

    override suspend fun insertAll(entities: List<Task>): Long {
        return withContext(Dispatchers.IO) {
            taskDao.insertAll(entities.map(taskToTaskEntity::invoke))
            return@withContext entities.size.toLong()
        }
    }

    override suspend fun update(task: Task): Long {
        return withContext(Dispatchers.IO) {
            taskDao.updateAndCountChanges(taskToTaskEntity(task))
        }
    }

    override fun getTaskStatisticsObservable(): Observable<TaskStatistics> {
        return taskDao.getTaskStatistics().map(taskStatisticsEntityToTaskStatistics::invoke)
    }

    override fun findByIdObservable(id: String): Observable<List<Task>> {
        return taskDao.findByIdObservable(id).map { it.map(taskEntityToTask::invoke) }
    }

    override fun getTaskMiniObservablePaged(pageSize: Int): Observable<PagedList<TaskMini>> {
        return taskDao
            .getTaskMiniDataSourceFactory()
            .map(taskMiniEntityToTaskMini::invoke)
            .toObservable(Config(pageSize = pageSize, enablePlaceholders = false))
    }

    override fun getCompletedTaskMiniObservablePaged(pageSize: Int): Observable<PagedList<TaskMini>> {
        return taskDao
            .getCompletedTaskMiniDataSourceFactory()
            .map(taskMiniEntityToTaskMini::invoke)
            .toObservable(Config(pageSize = pageSize, enablePlaceholders = false))
    }

    override fun getActiveTaskMiniObservablePaged(pageSize: Int): Observable<PagedList<TaskMini>> {
        return taskDao
            .getActiveTaskMiniDataSourceFactory()
            .map(taskMiniEntityToTaskMini::invoke)
            .toObservable(Config(pageSize = pageSize, enablePlaceholders = false))
    }

    override fun getSearchResultObservablePaged(
        query: String,
        pageSize: Int
    ): Observable<PagedList<SearchResult>> {
        val ftsQuery = query
            .splitToSequence(" ")
            .map { "$it*" }
            .joinToString(" ", "\"", "\"")
        return taskDao
            .getSearchResultDataSourceWith(ftsQuery)
            .map(searchResultEntityToSearchResult::invoke)
            .toObservable(pageSize, fetchScheduler = Schedulers.io())
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
