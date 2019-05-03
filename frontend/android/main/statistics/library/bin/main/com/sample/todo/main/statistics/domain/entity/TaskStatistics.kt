package com.sample.todo.main.statistics.domain.entity

data class TaskStatistics(
    val taskCount: Long,
    val completedTaskCount: Long,
    val activeTaskCount: Long
)
