package com.sample.todo.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// TODO kotlin gen private constructor in java so we cannot make id type is TaskId
@Entity(
    tableName = "task",
    indices = [
        Index(
            value = ["completed"],
            unique = false
        )
    ]
)
data class TaskEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "completed")
    val isCompleted: Boolean
)