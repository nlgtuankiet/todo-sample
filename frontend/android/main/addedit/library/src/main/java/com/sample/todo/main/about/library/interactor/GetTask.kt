package com.sample.todo.main.about.library.interactor

import com.sample.todo.domain.entity.Task
import com.sample.todo.domain.entity.TaskId
import com.sample.todo.domain.entity.isValid
import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.main.about.library.exception.TaskNotFoundException
import com.sample.todo.main.about.library.repository.AddEditRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetTask @Inject constructor(
    private val addEditRepository: AddEditRepository
) {
    suspend operator fun invoke(taskId: TaskId): Task {
        if (!taskId.isValid)
            throw InvalidTaskIdException(taskId)
        delay(1500) // fake load
        return addEditRepository.getTask(taskId.value) ?: throw TaskNotFoundException()
    }
}
