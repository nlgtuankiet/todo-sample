package com.sample.todo.domain.usecase

import androidx.paging.PagedList
import com.sample.todo.domain.model.TaskFilterType
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.repository.TaskRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetTaskMiniList @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(
        filter: TaskFilterType = TaskFilterType.ALL,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Flowable<PagedList<TaskMini>> {
        return taskRepository.getTaskMiniFlowablePaged(filter, pageSize)
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}