package com.sample.todo.dynamic.seeddatabase.domain.interactor

import com.sample.todo.domain.entity.Task
import com.sample.todo.domain.entity.TaskId
import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.domain.repository.TaskRepository
import com.sample.todo.dynamic.seeddatabase.library.di.SeedDatabaseWorkerScope
import javax.inject.Inject

@SeedDatabaseWorkerScope
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
