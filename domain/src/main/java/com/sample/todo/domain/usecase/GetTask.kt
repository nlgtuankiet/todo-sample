package com.sample.todo.domain.usecase

import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.domain.exception.TaskNotFoundException
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskId
import com.sample.todo.domain.model.isValid
import com.sample.todo.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTask @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: TaskId): Result<Task> {
        return withContext(Dispatchers.IO) {
            doInvoke(taskId)
        }
    }

    private suspend fun doInvoke(taskId: TaskId): Result<Task> {
        if (!taskId.isValid) return Result.failure(InvalidTaskIdException(taskId))
        val task =
            taskRepository.getTask(taskId.value) ?: return Result.failure(TaskNotFoundException())
        return Result.success(task)
    }
}
