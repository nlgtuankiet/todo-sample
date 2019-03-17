package com.sample.todo.domain.usecase

import com.sample.todo.domain.repository.TaskRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTasksCountObservable @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Observable<Long> {
        return taskRepository.getTaskCount().distinctUntilChanged()
    }
}