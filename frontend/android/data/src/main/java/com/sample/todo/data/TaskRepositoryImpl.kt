package com.sample.todo.data

import androidx.paging.PagedList
import com.sample.todo.data.core.DataScope
import com.sample.todo.domain.entity.Task
import com.sample.todo.domain.repository.PreferenceRepository
import com.sample.todo.domain.repository.TaskRepository
import com.sample.todo.domain.util.checkAllMatched
import com.sample.todo.main.about.library.repository.AddEditRepository
import com.sample.todo.main.search.library.domain.entity.SearchResult
import com.sample.todo.main.search.library.domain.entity.SearchResultStatistics
import com.sample.todo.main.search.library.domain.repository.SearchRepository
import com.sample.todo.main.statistics.domain.entity.TaskStatistics
import com.sample.todo.main.statistics.domain.repository.StatisticsRepository
import com.sample.todo.main.taskdetail.library.domain.repository.TaskDetailRepository
import com.sample.todo.main.tasks.library.domain.entity.TaskFilterType
import com.sample.todo.main.tasks.library.domain.entity.TaskMini
import com.sample.todo.main.tasks.library.domain.repository.TasksRepository
import io.reactivex.Observable
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

/**
 * TODO: fix bug where PagedList<TaskMini> empty cause isNoTask to true, then PagedList update itself, mind blown
 */
@DataScope
class TaskRepositoryImpl @Inject constructor(
    private val taskDataSource: TaskDataSource,
    private val preferenceRepository: PreferenceRepository
) : TasksRepository,
    TaskDetailRepository,
    AddEditRepository,
    SearchRepository,
    StatisticsRepository,
    TaskRepository {
    override suspend fun insertAll(tasks: List<com.sample.todo.domain.entity.Task>): Long {
        return taskDataSource.insertAll(tasks)
    }

    override fun getTaskFilterTypeOrdinalObservable(): Observable<Int> {
        return preferenceRepository.getTaskFilterTypeOrdinalObservable()
    }

    override suspend fun setTaskFilterTypeOrdinal(value: Int) {
        return preferenceRepository.setTaskFilterTypeOrdinal(value)
    }

    override suspend fun getTaskFilterTypeOrdinal(): Int {
        return preferenceRepository.getTaskFilterTypeOrdinal()
    }

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

    override fun getTaskStatisticsObservable(): Observable<TaskStatistics> {
        return taskDataSource.getTaskStatisticsObservable()
    }

    override suspend fun update(task: Task): Long {
        return taskDataSource.update(task)
    }

    override suspend fun updateComplete(taskId: String, completed: Boolean, updateTime: Long): Long {
        return taskDataSource.updateComplete(taskId, completed, updateTime)
    }

    override suspend fun getTask(taskId: String): Task? {
        delay(2000) // fake load
        return taskDataSource.findTaskById(taskId)
    }

    override suspend fun insert(entity: Task): Long {
        return taskDataSource.insert(entity)
    }
}
