package com.sample.todo.domain.usecase

import com.sample.todo.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateComplete @Inject constructor(
    private val taskRepository: TaskRepository
) {
    // TODO undo button
    // TODO check for complete first then update, ex: task is active then mask as active again
    suspend operator fun invoke(taskId: String, oldComplete: Boolean?, newCompleted: Boolean) {
        if (oldComplete == null) throw IllegalArgumentException("Unknown current task complete")
        if (oldComplete != newCompleted) {
            val rowsUpdated = taskRepository.updateComplete(taskId, newCompleted)
            if (rowsUpdated > 0) {
//                val message = Message(
//                    messageId = if (newCompleted) R.string.task_marked_complete else R.string.task_marked_active,
//                    longDuration = true
//                )
//                messageManager.addMessage(message)
            }
        }
    }
}

sealed class UpdateCompleteResult {
    object TaskMarkedComplete : UpdateCompleteResult()
    object TaskMarkedActive : UpdateCompleteResult()
    object TaskUpdateError : UpdateCompleteResult()
}