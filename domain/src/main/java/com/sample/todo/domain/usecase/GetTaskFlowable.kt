package com.sample.todo.domain.usecase

import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.domain.exception.TaskNotFoundException
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskId
import com.sample.todo.domain.model.isValid
import com.sample.todo.domain.repository.TaskRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTaskFlowable @Inject constructor(
    private val taskRepository: TaskRepository
) {
    // what about threading?
    operator fun invoke(taskId: TaskId): Observable<Result<Task>> {
        if (!taskId.isValid)
            return Observable.just(Result.failure(InvalidTaskIdException(taskId)))
        return taskRepository
            .getTaskWithId(taskId.value)
//            .observeOn(null)
//            .subscribeOn(null)
            .map { taskList ->
                if (taskList.isEmpty()) {
                    Result.failure(TaskNotFoundException())
                } else {
                    Result.success(taskList.first())
                }
            }
            .distinctUntilChanged()
    }
}