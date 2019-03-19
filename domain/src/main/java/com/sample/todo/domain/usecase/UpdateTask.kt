package com.sample.todo.domain.usecase

import com.sample.todo.domain.model.Task
import com.sample.todo.domain.repository.TaskRepository
import org.threeten.bp.Instant
import javax.inject.Inject

class UpdateTask @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task) {
        val updatedRows = taskRepository.update(task.copy(updateTime = Instant.now()))
//        messageManager.addMessage(
//            Message(
//                messageId = if (updatedRows == 1)
//                    R.string.update_task_successful
//                else
//                    R.string.update_task_fail
//            )
//        )
    }
}