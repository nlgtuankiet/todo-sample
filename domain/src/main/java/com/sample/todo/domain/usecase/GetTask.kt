package com.sample.todo.domain.usecase

import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.domain.exception.TaskNotFoundException
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskId
import com.sample.todo.domain.model.isValid
import com.sample.todo.domain.repository.TaskRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetTask @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: TaskId): Task {
        if (!taskId.isValid)
            throw InvalidTaskIdException(taskId)
        delay(1500) // fake load
        return taskRepository.getTask(taskId.value) ?: throw TaskNotFoundException()
    }
}
