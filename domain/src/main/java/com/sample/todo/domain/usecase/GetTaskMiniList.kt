package com.sample.todo.domain.usecase

import androidx.paging.PagedList
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.repository.TaskRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTaskMiniList @Inject constructor(
    private val taskRepository: TaskRepository,
    private val getTaskFilterType: GetTaskFilterTypeObservable
) {
    operator fun invoke(
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Observable<PagedList<TaskMini>> {
        return getTaskFilterType().switchMap { taskRepository.getTasksObservablePaged(it, pageSize) }
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}