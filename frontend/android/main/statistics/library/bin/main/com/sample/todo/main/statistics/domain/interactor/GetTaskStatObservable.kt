package com.sample.todo.main.statistics.domain.interactor

import com.sample.todo.main.statistics.domain.entity.TaskStatistics
import com.sample.todo.main.statistics.domain.repository.StatisticsRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTaskStatObservable @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) {
    operator fun invoke(): Observable<TaskStatistics> {
        return statisticsRepository.getTaskStatisticsObservable().distinctUntilChanged()
    }
}
