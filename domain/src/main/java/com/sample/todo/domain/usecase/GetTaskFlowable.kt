package com.sample.todo.domain.usecase

import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskId
import com.sample.todo.domain.model.isValid
import com.sample.todo.domain.repository.TaskRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetTaskFlowable @Inject constructor(
    private val taskRepository: TaskRepository
) {

    sealed class Result {
        object TaskNotFound : Result()
        data class Found(val task: Task) : Result()

        fun getOrNull(): Task? = (this as? Found)?.task
    }
    operator fun invoke(taskId: TaskId): Flowable<Result> {
        if (!taskId.isValid)
            throw InvalidTaskIdException(taskId)
        return taskRepository
            .getTaskWithIdFlowable(taskId.value)
            .map { taskList ->
                if (taskList.isEmpty()) {
                    Result.TaskNotFound
                } else {
                    Result.Found(taskList.first())
                }
            }
            .distinctUntilChanged()
    }
}