package com.sample.todo.main.tasks.library.domain.interactor

import com.sample.todo.main.tasks.library.domain.entity.TaskFilterType
import com.sample.todo.main.tasks.library.domain.repository.TasksRepository
import io.reactivex.Observable
import javax.inject.Inject

// TODO write test for it
class GetTaskFilterTypeObservable @Inject constructor(
    private val tasksRepository: TasksRepository
) {
    operator fun invoke(): Observable<TaskFilterType> {
        return tasksRepository
            .getTaskFilterTypeOrdinalObservable()
            .distinctUntilChanged()
            .map {
                TaskFilterType.parse(it) ?: TaskFilterType.ALL
            }
    }
}
