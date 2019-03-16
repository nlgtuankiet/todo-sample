package com.sample.todo.domain.usecase

import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskId
import com.sample.todo.domain.repository.TaskRepository
import javax.inject.Inject

class InsertAllTasks @Inject constructor(
    private val taskRepository: TaskRepository
) {
    // return number of task inserted
    suspend operator fun invoke(entities: List<Task>): Boolean {
        if (entities.isEmpty()) return true
        val invalidTaskIds = entities
            .filter { task -> !TaskId.validate(task.id) }
            .map { it.id }
        if (invalidTaskIds.isNotEmpty())
            throw InvalidTaskIdException(invalidTaskIds)
        val rowInserted = taskRepository.insertAll(entities)
        return rowInserted == entities.size.toLong()
    }
}