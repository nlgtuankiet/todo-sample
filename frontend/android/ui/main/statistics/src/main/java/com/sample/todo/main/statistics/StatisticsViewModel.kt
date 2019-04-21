package com.sample.todo.main.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.sample.todo.base.extension.map
import com.sample.todo.domain.model.TaskStatistics
import com.sample.todo.domain.usecase.GetTaskStatObservable
import io.reactivex.BackpressureStrategy
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    getTaskStatLive: GetTaskStatObservable
) : ViewModel() {

    private val stat = getTaskStatLive()
        .startWith(TaskStatistics(0, 0, 0))
        .toFlowable(BackpressureStrategy.LATEST)
        .toLiveData()

    val tasksCount = stat.map { it.taskCount.toString() }

    val completedTasksCount = stat.map { it.completedTaskCount.toString() }

    val activeTasksCount = stat.map { it.activeTaskCount.toString() }
}
