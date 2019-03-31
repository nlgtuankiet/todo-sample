package com.sample.todo.data.task.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = TaskEntity::class)
@Entity(tableName = "task_fts")
data class TaskFtsEntity(
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String? = null
)