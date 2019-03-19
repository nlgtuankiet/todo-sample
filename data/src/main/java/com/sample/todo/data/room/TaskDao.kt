package com.sample.todo.data.room

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.sample.todo.data.room.entity.SearchResultEntity
import com.sample.todo.data.room.entity.SearchResultStatisticsEntity
import com.sample.todo.data.room.entity.TaskEntity
import com.sample.todo.data.room.entity.TaskMiniEntity
import com.sample.todo.data.room.entity.TaskStatisticsEntity
import io.reactivex.Observable

@Dao
abstract class TaskDao {

    @Query("SELECT changes()")
    abstract fun changes(): Long

    @Query("SELECT * FROM task")
    abstract fun getAll(): Observable<List<TaskEntity>>

    @Insert
    abstract fun insert(entity: TaskEntity)

    @Transaction
    open fun insertAndCountChanges(entity: TaskEntity): Long {
        insert(entity)
        return changes()
    }

    @Insert
    abstract fun insertAll(entities: List<TaskEntity>)

    @Transaction
    open fun insertAllAndCountChanges(entities: List<TaskEntity>): Long {
        insertAll(entities)
        return changes()
    }

    @Query("SELECT * FROM task WHERE id = :taskId")
    abstract fun findById(taskId: String): TaskEntity?

    @Query("UPDATE task SET completed = :completed, update_time = :updateTime WHERE id = :taskId")
    abstract fun updateComplete(taskId: String, completed: Boolean, updateTime: Long)

    @Transaction
    open fun updateCompleteAndCountChanges(taskId: String, completed: Boolean, updateTime: Long): Long {
        updateComplete(taskId, completed, updateTime)
        return changes()
    }

    @Query("SELECT completed FROM task WHERE id = :taskId")
    abstract fun getComplete(taskId: String): Boolean

    @Query("SELECT [id], [title], [completed] FROM task ORDER BY create_time ASC")
    abstract fun getTaskMiniDataSourceFactory(): DataSource.Factory<Int, TaskMiniEntity>

    @Query("SELECT [id], [title], [completed] FROM task WHERE completed = 1 ORDER BY create_time ASC")
    abstract fun getCompletedTaskMiniDataSourceFactory(): DataSource.Factory<Int, TaskMiniEntity>

    @Query("SELECT [id], [title], [completed] FROM task WHERE completed = 0 ORDER BY create_time ASC")
    abstract fun getActiveTaskMiniDataSourceFactory(): DataSource.Factory<Int, TaskMiniEntity>

    @Update
    abstract fun update(taskEntity: TaskEntity)

    @Transaction
    open fun updateAndCountChanges(taskEntity: TaskEntity): Long {
        update(taskEntity)
        return changes()
    }

    @Query("""
        SELECT
            snippet(task_fts) AS snippet,
            task.id AS id,
            task.title AS title
        FROM
            task JOIN task_fts ON (task.rowid = task_fts.rowid)
        WHERE
            task_fts MATCH :query
        ORDER BY create_time ASC
    """)
    abstract fun getSearchResultDataSourceWith(query: String): DataSource.Factory<Int, SearchResultEntity>

    @Query("DELETE FROM task")
    abstract fun deleteAllTasks()

    @Transaction
    open fun deleteAllCountChanges(): Long {
        deleteAllTasks()
        return changes()
    }

    @Query("SELECT COUNT(*) FROM task")
    abstract fun tasksCount(): Long

    @Query("""
        SELECT *
        FROM
            (SELECT COUNT(*) AS task_count FROM task),
            (SELECT COUNT(*) AS completed_task_count FROM task WHERE completed = 1),
            (SELECT COUNT(*) AS active_task_count FROM task WHERE completed = 0)
    """)
    abstract fun getTaskStatistics(): Observable<TaskStatisticsEntity>

    @Query("SELECT COUNT(*) FROM task")
    abstract fun getTasksCountObservable(): Observable<Long>

    @Query("SELECT * FROM task WHERE id = :id LIMIT 1")
    abstract fun findByIdObservable(id: String): Observable<List<TaskEntity>>

    @Query("DELETE FROM task WHERE id = :id")
    abstract fun deleteTask(id: String)

    @Transaction
    open fun deleteTaskAndCountChanges(id: String): Long {
        deleteTask(id)
        return changes()
    }

    @Query("""
        SELECT * FROM
            (SELECT COUNT(*) AS total_result_count FROM task_fts WHERE task_fts MATCH :query) ,
            (SELECT COUNT(*) AS total_task_count FROM task)
    """)
    abstract fun getSearchResultStatisticsObservable(query: String): Observable<SearchResultStatisticsEntity>
}