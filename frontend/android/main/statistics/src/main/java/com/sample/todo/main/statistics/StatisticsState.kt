package com.sample.todo.main.statistics

import com.airbnb.mvrx.MvRxState

data class StatisticsState(
    val tasksCount: String,
    val completedTasksCount: String,
    val activeTasksCount: String
) : MvRxState