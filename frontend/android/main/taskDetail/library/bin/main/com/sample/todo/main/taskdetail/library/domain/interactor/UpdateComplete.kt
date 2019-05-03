package com.sample.todo.main.taskdetail.library.domain.interactor

import com.sample.todo.main.taskdetail.library.domain.repository.TaskDetailRepository
import org.threeten.bp.Instant
import javax.inject.Inject

class UpdateComplete @Inject constructor(
    private val taskDetailRepository: TaskDetailRepository
) {
    // TODO undo button
    // TODO check for complete first then update, ex: task is active then mask as active again
    suspend operator fun invoke(taskId: String, newCompleted: Boolean): Boolean {
        val rowsUpdated = taskDetailRepository
            .updateComplete(taskId, newCompleted, Instant.now().epochSecond)
        return rowsUpdated == 1L
    }
}
