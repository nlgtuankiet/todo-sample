package com.sample.todo.domain.usecase

import com.sample.todo.domain.model.TaskStat
import com.sample.todo.domain.repository.TaskRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTaskStatFlowable @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Observable<TaskStat> {
        return taskRepository.getTaskStatistics().distinctUntilChanged()
    }
}