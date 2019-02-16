package com.sample.todo.data.room.entity

import androidx.room.ColumnInfo

data class TaskStatEntity(
    @ColumnInfo(name = "task_count")
    val taskCount: Long,
    @ColumnInfo(name = "completed_task_count")
    val completedTaskCount: Long,
    @ColumnInfo(name = "active_task_count")
    val activeTaskCount: Long
)