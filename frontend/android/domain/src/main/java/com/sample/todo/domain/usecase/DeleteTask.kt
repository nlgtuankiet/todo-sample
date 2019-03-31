package com.sample.todo.domain.usecase

import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.domain.model.TaskId
import com.sample.todo.domain.model.isValid
import com.sample.todo.domain.repository.TaskRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class DeleteTask @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: TaskId): Boolean {
        if (!taskId.isValid)
            throw InvalidTaskIdException(taskId)
        delay(2000) // fake loading
        return when (taskRepository.deleteTask(taskId.value)) {
            0L -> false
            1L -> true
            else -> TODO()
        }
    }
}