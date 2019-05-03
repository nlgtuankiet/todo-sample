package com.sample.todo.main.taskdetail.library.domain.interactor

import com.sample.todo.domain.entity.Task
import com.sample.todo.domain.entity.TaskId
import com.sample.todo.domain.entity.isValid
import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.main.taskdetail.library.domain.repository.TaskDetailRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTaskObservable @Inject constructor(
    private val taskDetailRepository: TaskDetailRepository
) {

    sealed class Result {
        object TaskNotFound : Result()
        data class Found(val task: Task) : Result()

        fun getOrNull(): Task? = (this as? Found)?.task
    }

    operator fun invoke(taskId: TaskId): Observable<Result> {
        if (!taskId.isValid)
            throw InvalidTaskIdException(taskId)
        return taskDetailRepository
            .getTaskWithIdObservable(taskId.value)
            .map { taskList ->
                if (taskList.isEmpty()) {
                    Result.TaskNotFound
                } else {
                    Result.Found(taskList.first())
                }
            }
            .distinctUntilChanged()
    }
}
