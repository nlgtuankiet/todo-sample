package com.sample.todo.ui.statistics

import com.sample.todo.core.BaseViewModel
import com.sample.todo.domain.usecase.GetTaskStatFlowable
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import com.sample.todo.util.asLiveData
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    getTaskStatLive: GetTaskStatFlowable
) : BaseViewModel() {
    private val stat = getTaskStatLive().asLiveData()

    val taskCount = stat.map { it.taskCount.toString() }.distinctUntilChanged()

    val completedTaskCount = stat.map { it.completedTaskCount.toString() }.distinctUntilChanged()

    val activeTaskCount = stat.map { it.activeTaskCount.toString() }.distinctUntilChanged()
}