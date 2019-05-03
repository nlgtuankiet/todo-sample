package com.sample.todo.main.about.library.interactor

import com.sample.todo.domain.entity.Task
import com.sample.todo.main.about.library.repository.AddEditRepository
import org.threeten.bp.Instant
import javax.inject.Inject

class UpdateTask @Inject constructor(
    private val addEditRepository: AddEditRepository
) {
    suspend operator fun invoke(task: Task) {
        val updatedRows = addEditRepository.update(task.copy(updateTime = Instant.now()))
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
