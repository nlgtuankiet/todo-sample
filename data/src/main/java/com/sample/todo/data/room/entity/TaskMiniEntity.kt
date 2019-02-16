package com.sample.todo.data.room.entity

import androidx.room.ColumnInfo

data class TaskMiniEntity(
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "completed")
    val isCompleted: Boolean
)