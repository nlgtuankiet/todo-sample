package com.sample.todo.domain.usecase

import com.sample.todo.domain.model.TaskStatistics
import com.sample.todo.domain.repository.TaskRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTaskStatObservable @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Observable<TaskStatistics> {
        return taskRepository.getTaskStatisticsObservable().distinctUntilChanged()
    }
}