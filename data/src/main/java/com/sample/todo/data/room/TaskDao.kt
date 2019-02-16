package com.sample.todo.data.room

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sample.todo.data.room.entity.SearchResultEntity
import com.sample.todo.data.room.entity.TaskEntity
import com.sample.todo.data.room.entity.TaskMiniEntity
import com.sample.todo.data.room.entity.TaskStatEntity
import io.reactivex.Observable

@Dao
abstract class TaskDao {

    @Query("SELECT * FROM task")
    abstract fun getAll(): Observable<List<TaskEntity>>

    @Insert
    abstract suspend fun insert(entity: TaskEntity)

    @Insert
    abstract suspend fun insertAll(entities: List<TaskEntity>)

    @Query("SELECT * FROM task WHERE id = :taskId")
    abstract suspend fun findById(taskId: String): TaskEntity?

    @Query("UPDATE task SET completed = :completed WHERE id = :taskId")
    abstract suspend fun updateComplete(taskId: String, completed: Boolean): Int

    @Query("SELECT completed FROM task WHERE id = :taskId")
    abstract suspend fun getComplete(taskId: String): Boolean

    @Query("SELECT [id], [title], [completed] FROM task")
    abstract fun getAllTaskMiniDataSourceFactory(): DataSource.Factory<Int, TaskMiniEntity>

    @Query("SELECT [id], [title], [completed] FROM task WHERE completed = 1")
    abstract fun getAllCompletedTaskMiniDataSourceFactory(): DataSource.Factory<Int, TaskMiniEntity>

    @Query("SELECT [id], [title], [completed] FROM task WHERE completed = 0")
    abstract fun getAllActiveTaskMiniDataSourceFactory(): DataSource.Factory<Int, TaskMiniEntity>

    @Update
    abstract fun update(taskEntity: TaskEntity): Int

    @Query("""
        SELECT
            snippet(task_fts) AS snippet,
            task.id AS id,
            task.title AS title
        FROM
            task JOIN task_fts ON (task.rowid = task_fts.rowid)
        WHERE
            task_fts MATCH :query
    """)
    abstract fun searchTask(query: String): DataSource.Factory<Int, SearchResultEntity>

    @Query("DELETE FROM task")
    abstract fun deleteAllTasks(): Int

    @Query("SELECT COUNT(*) FROM task")
    abstract fun tasksCount(): Long

    @Query("""
        SELECT *
        FROM
            (SELECT COUNT(*) AS task_count FROM task),
            (SELECT COUNT(*) AS completed_task_count FROM task WHERE completed = 1),
            (SELECT COUNT(*) AS active_task_count FROM task WHERE completed = 0)
    """)
    abstract fun taskStat(): Observable<TaskStatEntity>

    @Query("SELECT COUNT(*) FROM task")
    abstract fun tasksCountLive(): Observable<Long>

    @Query("SELECT * FROM task WHERE id = :id LIMIT 1")
    abstract fun findByIdFlowable(id: String): Observable<List<TaskEntity>>

    @Query("DELETE FROM task WHERE id = :id")
    abstract suspend fun deleteTask(id: String): Int
}