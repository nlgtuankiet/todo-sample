package com.sample.todo.ui.statistics

import com.airbnb.mvrx.MvRxState

data class StatisticsState(
    val tasksCount: String,
    val completedTasksCount: String,
    val activeTasksCount: String
) : MvRxState