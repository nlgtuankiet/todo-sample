package com.sample.todo.domain.usecase

import com.sample.todo.domain.model.TaskStatistics
import com.sample.todo.domain.repository.TaskRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetTaskStatFlowable @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Flowable<TaskStatistics> {
        return taskRepository.getTaskStatisticsFlowable().distinctUntilChanged()
    }
}