package com.sample.todo.domain.usecase

import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskId
import com.sample.todo.domain.repository.TaskRepository
import javax.inject.Inject

// TODO check title is empty
class InsertNewTask @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(entity: Task): Boolean {
        if (!TaskId.validate(entity.id)) throw InvalidTaskIdException(entity.id)
        return taskRepository.insert(entity) == 1L
    }
}