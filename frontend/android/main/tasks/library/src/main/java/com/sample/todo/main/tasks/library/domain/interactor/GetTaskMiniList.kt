package com.sample.todo.main.tasks.library.domain.interactor

import androidx.paging.PagedList
import com.sample.todo.main.tasks.library.domain.entity.TaskMini
import com.sample.todo.main.tasks.library.domain.repository.TasksRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTaskMiniList @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val getTaskFilterType: GetTaskFilterTypeObservable
) {
    operator fun invoke(
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Observable<PagedList<TaskMini>> {
        return getTaskFilterType().switchMap { tasksRepository.getTasksObservablePaged(it, pageSize) }
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}
