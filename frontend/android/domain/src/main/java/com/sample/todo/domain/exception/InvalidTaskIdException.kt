package com.sample.todo.domain.exception

import com.sample.todo.domain.model.TaskId
import java.lang.IllegalArgumentException

class InvalidTaskIdException(message: CharSequence) : IllegalArgumentException(message.toString()) {
    constructor(taskIds: List<String>) : this("Invalid task ids: $taskIds")
    constructor(taskId: TaskId) : this("Invalid task id: $taskId")
}
