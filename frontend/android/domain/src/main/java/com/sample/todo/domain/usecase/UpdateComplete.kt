package com.sample.todo.domain.usecase

import com.sample.todo.domain.repository.TaskRepository
import org.threeten.bp.Instant
import javax.inject.Inject

class UpdateComplete @Inject constructor(
    private val taskRepository: TaskRepository
) {
    // TODO undo button
    // TODO check for complete first then update, ex: task is active then mask as active again
    suspend operator fun invoke(taskId: String, newCompleted: Boolean): Boolean {
        val rowsUpdated = taskRepository.updateComplete(taskId, newCompleted, Instant.now().epochSecond)
        return rowsUpdated == 1L
    }
}
