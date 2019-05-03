package com.sample.todo.main.taskdetail.library.domain.interactor

import com.sample.todo.domain.entity.TaskId
import com.sample.todo.domain.entity.isValid
import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.main.taskdetail.library.domain.repository.TaskDetailRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class DeleteTask @Inject constructor(
    private val taskDetailRepository: TaskDetailRepository
) {
    suspend operator fun invoke(taskId: TaskId): Boolean {
        if (!taskId.isValid)
            throw InvalidTaskIdException(taskId)
        delay(2000) // fake loading
        return when (taskDetailRepository.deleteTask(taskId.value)) {
            0L -> false
            1L -> true
            else -> TODO()
        }
    }
}
