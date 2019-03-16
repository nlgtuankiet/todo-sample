package com.sample.todo.ui.statistics

import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.toLiveData
import com.sample.todo.core.BaseViewModel
import com.sample.todo.domain.usecase.GetTaskStatFlowable
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    getTaskStatLive: GetTaskStatFlowable
) : BaseViewModel() {
    private val stat = getTaskStatLive().toLiveData()

    val taskCount = stat.map { it.taskCount.toString() }.distinctUntilChanged()

    val completedTaskCount = stat.map { it.completedTaskCount.toString() }.distinctUntilChanged()

    val activeTaskCount = stat.map { it.activeTaskCount.toString() }.distinctUntilChanged()
}