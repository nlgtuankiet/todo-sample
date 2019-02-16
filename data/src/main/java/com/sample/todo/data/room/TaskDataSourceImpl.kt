package com.sample.todo.data.room

import androidx.paging.PagedList
import androidx.paging.toObservable
import com.sample.todo.data.Mapper
import com.sample.todo.data.TaskDataSource
import com.sample.todo.data.core.DataScope
import com.sample.todo.data.room.entity.SearchResultEntity
import com.sample.todo.data.room.entity.TaskEntity
import com.sample.todo.data.room.entity.TaskMiniEntity
import com.sample.todo.data.room.entity.TaskStatEntity
import com.sample.todo.domain.model.SearchResult
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.model.TaskStat
import io.reactivex.Observable
import javax.inject.Inject

@DataScope
class TaskDataSourceImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val taskEntityMapper: Mapper<TaskEntity, Task>,
    private val taskStatEntityMapper: Mapper<TaskStatEntity, TaskStat>,
    private val taskMapper: Mapper<Task, TaskEntity>,
    private val taskMiniMapper: Mapper<TaskMiniEntity, TaskMini>,
    private val searchResultMapper: Mapper<SearchResultEntity, SearchResult>
) : TaskDataSource {
    override suspend fun findTaskById(taskId: String): Task? {
        return taskDao.findById(taskId)?.let { taskEntityMapper.map(it) }
    }

    override suspend fun insert(entity: Task): Long {
        taskDao.insert(taskMapper.map(entity))
        return 1L
    }

    override suspend fun insertAll(entities: List<Task>): Long {
        taskDao.insertAll(entities.map(taskMapper::map))
        return entities.size.toLong()
    }

    override suspend fun update(task: Task): Long {
        return taskDao.update(taskMapper.map(task)).toLong()
    }

    override fun taskStat(): Observable<TaskStat> {
        return taskDao.taskStat().map(taskStatEntityMapper::map)
    }

    override fun findByIdFlowable(id: String): Observable<List<Task>> {
        return taskDao.findByIdFlowable(id).map { it.map(taskEntityMapper::map) }
    }

    override fun getTaskMini(pageSize: Int): Observable<PagedList<TaskMini>> {
        return taskDao
            .getAllTaskMiniDataSourceFactory()
            .map(taskMiniMapper::map)
            .toObservable(pageSize)
    }

    override fun getCompletedTaskMini(pageSize: Int): Observable<PagedList<TaskMini>> {
        return taskDao
            .getAllCompletedTaskMiniDataSourceFactory()
            .map(taskMiniMapper::map)
            .toObservable(pageSize)
    }

    override fun getActiveTaskMini(pageSize: Int): Observable<PagedList<TaskMini>> {
        return taskDao
            .getAllActiveTaskMiniDataSourceFactory()
            .map(taskMiniMapper::map)
            .toObservable(pageSize)
    }

    override fun getSearchResultPaged(
        query: String,
        pageSize: Int
    ): Observable<PagedList<SearchResult>> {
        return taskDao
            .searchTask(query)
            .map(searchResultMapper::map)
            .toObservable(pageSize)
    }

    override suspend fun deleteTask(id: String): Long {
        return taskDao.deleteTask(id).toLong()
    }

//    override fun findByIdFlowable(id: String): Flowable<List<TaskEntity>> {
//        return taskDao.findByIdFlowable(id)
//    }

    override fun tasksCountLive(): Observable<Long> {
        return taskDao.tasksCountLive()
    }

//    override fun taskStat(): Flowable<TaskStatEntity> {
//        return taskDao.taskStat().distinct()
//    }

//    override suspend fun update(task: TaskEntity): Int {
//        return taskDao.update(task)
//    }

//    override suspend fun insertAll(entities: List<TaskEntity>): List<Long> {
//        return taskDao.insertAll(entities)
//    }

    override suspend fun updateComplete(taskId: String, completed: Boolean): Long {
        val oldCompleted = taskDao.getComplete(taskId)
        return if (oldCompleted == completed) {
            0
        } else {
            taskDao.updateComplete(taskId, completed).toLong()
        }
    }

//    override suspend fun insert(entity: TaskEntity) {
//        taskDao.insert(entity)
//    }

//    override suspend fun findTaskById(taskId: String): TaskEntity? {
//        return taskDao.findById(taskId)
//    }
}