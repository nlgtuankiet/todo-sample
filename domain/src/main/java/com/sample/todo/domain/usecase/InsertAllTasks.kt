package com.sample.todo.domain.usecase

import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskId
import com.sample.todo.domain.repository.TaskRepository
import com.sample.todo.domain.util.runCatchingIO
import javax.inject.Inject

class InsertAllTasks @Inject constructor(
    private val taskRepository: TaskRepository
) {
    // return number of task inserted
    suspend operator fun invoke(entities: List<Task>): Result<Long> = runCatchingIO {
        doInvoke(entities)
    }

    private suspend inline fun doInvoke(entities: List<Task>): Long {
        if (entities.isEmpty()) return 0
        val invalidTaskIds = entities
            .filter { task -> !TaskId.validate(task.id) }
            .map { it.id }
        if (invalidTaskIds.isNotEmpty()) throw InvalidTaskIdException(invalidTaskIds)
        return taskRepository.insertAll(entities)
    }
}