package com.sample.todo.domain.usecase

import com.sample.todo.domain.exception.DeleteTaskFailException
import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.domain.model.TaskId
import com.sample.todo.domain.model.isValid
import com.sample.todo.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

// TODO implement loading...
class DeleteTask @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: TaskId): Result<Unit> {
        return withContext(Dispatchers.IO) {
            doInvoke(taskId)
        }
    }

    private suspend fun doInvoke(taskId: TaskId): Result<Unit> {
        if (!taskId.isValid) return Result.failure(InvalidTaskIdException(taskId))
        return runCatching {
            taskRepository.deleteTask(taskId.value)
        }.fold(
            onSuccess = { rowDeleted ->
                if (rowDeleted == 1L) {
                    Result.success(Unit)
                } else {
                    Result.failure(DeleteTaskFailException())
                }
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }
}