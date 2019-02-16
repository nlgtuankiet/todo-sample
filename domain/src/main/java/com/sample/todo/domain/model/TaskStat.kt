package com.sample.todo.domain.model

data class TaskStat(
    val taskCount: Long,
    val completedTaskCount: Long,
    val activeTaskCount: Long
)