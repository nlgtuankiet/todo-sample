package com.sample.todo.data.task.sqldelight

import androidx.paging.PagedList
import androidx.paging.toObservable
import com.sample.todo.data.TaskDataSource
import com.sample.todo.data.core.DataScope
import com.sample.todo.data.core.Mapper
import com.sample.todo.domain.model.SearchResult
import com.sample.todo.domain.model.SearchResultStatistics
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.model.TaskStatistics
import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.android.paging.QueryDataSourceFactory
import com.squareup.sqldelight.runtime.rx.asObservable
import io.reactivex.Observable
import javax.inject.Inject

@DataScope
class TaskSqlDelightDataSource @Inject constructor(
    private val taskQueries: TaskQueries,
    private val database: TodoSqlDelightDatabase,
    private val sqlDelightTaskMapper: Mapper<TaskEntity, Task>,
    private val taskMapper: Mapper<Task, TaskEntity>,
    private val selectTaskMiniMapper: Mapper<SelectTaskMini, TaskMini>,
    private val selectActiveTaskMiniMapper: Mapper<SelectActiveTaskMini, TaskMini>,
    private val selectCompletedTaskMiniMapper: Mapper<SelectCompletedTaskMini, TaskMini>,
    private val sqlDelightTaskStatisticsMapper: Mapper<TaskStatisticsEntity, TaskStatistics>
) : TaskDataSource {
    override fun getTaskMiniObservablePaged(pageSize: Int): Observable<PagedList<TaskMini>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getCompletedTaskMiniObservablePaged(pageSize: Int): Observable<PagedList<TaskMini>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getActiveTaskMiniObservablePaged(pageSize: Int): Observable<PagedList<TaskMini>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getSearchResultStatisticsObservable(query: String): Observable<SearchResultStatistics> {
        TODO()
    }

    override suspend fun findTaskById(taskId: String): Task? {
        return taskQueries.selectTaskById(taskId).executeAsOneOrNull()
            ?.let { sqlDelightTaskMapper(it) }
    }

    override suspend fun insert(entity: Task): Long {
        return database.inTransaction {
            taskQueries.insert(taskMapper(entity))
            taskQueries.changes().executeAsOne()
        }
    }

    override suspend fun insertAll(entities: List<Task>): Long {
        return database.inTransaction {
            entities.forEach { task ->
                taskQueries.insert(taskMapper(task))
            }
            taskQueries.changes().executeAsOne()
        }
    }

    override suspend fun update(task: Task): Long {
        return database.inTransaction {
            taskQueries.update(
                task.id,
                task.title,
                task.description,
                if (task.isCompleted) 1 else 0
            )
            taskQueries.changes().executeAsOne()
        }
    }

    override fun getTaskStatisticsObservable(): Observable<TaskStatistics> {
        return taskQueries.taskStatistics()
            .asObservable()
            .map { query ->
                query.executeAsOne().let {
                    sqlDelightTaskStatisticsMapper(it)
                }
            }
    }

    override fun findByIdObservable(id: String): Observable<List<Task>> {
        return taskQueries.selectTaskById(id)
            .asObservable()
            .map {
                it.executeAsList().map {
                    sqlDelightTaskMapper(it)
                }
            }
    }

    override suspend fun updateComplete(taskId: String, completed: Boolean, updateTime: Long): Long {
        return database.inTransaction {
            taskQueries.updateComplete(
                taskId,
                if (completed) 1 else 0
            )
            taskQueries.changes().executeAsOne()
        }
    }

    override fun tasksCountObservable(): Observable<Long> {
        return taskQueries.countTasks()
            .asObservable()
            .map {
                it.executeAsOne()
            }
    }

    override fun getSearchResultObservablePaged(
        query: String,
        pageSize: Int
    ): Observable<PagedList<SearchResult>> {
        val ftsQuery = query
            .splitToSequence(" ")
            .map { "$it*" }
            .joinToString(" ", "\"", "\"")
        return QueryDataSourceFactory(
            queryProvider = taskQueries::selectTaskMini,
            countQuery = taskQueries.countTasks()
        ).map {
            SearchResult(
                id = it.id,
                snippets = it.title,
                title = it.title
            )
        }.toObservable(pageSize)
    }

    override suspend fun deleteTask(id: String): Long {
        return database.inTransaction {
            taskQueries.delete(id)
            taskQueries.changes().executeAsOne()
        }
    }
}

inline fun <T> Transacter.inTransaction(crossinline block: () -> T): T {
    var value: T? = null
    transaction {
        value = block()
    }
    return value ?: throw RuntimeException("how?")
}