package com.sample.todo.main.statistics.domain.repository

import com.sample.todo.main.statistics.domain.entity.TaskStatistics
import io.reactivex.Observable

interface StatisticsRepository {
    fun getTaskStatisticsObservable(): Observable<TaskStatistics>
}