package com.sample.todo.domain.usecase

import com.sample.todo.domain.repository.TaskRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetTasksCountFlowable @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Flowable<Long> {
        return taskRepository.getTaskCount().distinctUntilChanged()
    }
}