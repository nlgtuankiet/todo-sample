package com.sample.todo.data.room

import androidx.room.Database
import com.sample.todo.data.room.entity.TaskEntity
import com.sample.todo.data.room.entity.TaskFtsEntity

@Database(
    entities = [
        TaskEntity::class,
        TaskFtsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase : com.sample.todo.data.room.LoggingRoomDatabase() {
    abstract fun taskDao(): TaskDao
}