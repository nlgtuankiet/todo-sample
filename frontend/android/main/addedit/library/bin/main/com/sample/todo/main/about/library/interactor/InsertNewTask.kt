package com.sample.todo.main.about.library.interactor

import com.sample.todo.domain.entity.Task
import com.sample.todo.domain.entity.TaskId
import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.main.about.library.repository.AddEditRepository
import javax.inject.Inject

// TODO check title is empty
class InsertNewTask @Inject constructor(
    private val addEditRepository: AddEditRepository
) {
    suspend operator fun invoke(entity: Task): Boolean {
        if (!TaskId.validate(entity.id)) throw InvalidTaskIdException(entity.id)
        return addEditRepository.insert(entity) == 1L
    }
}
