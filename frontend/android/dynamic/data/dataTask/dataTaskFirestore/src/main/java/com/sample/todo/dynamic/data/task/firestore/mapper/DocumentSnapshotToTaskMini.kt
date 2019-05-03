package com.sample.todo.dynamic.data.task.firestore.mapper

import com.google.firebase.firestore.DocumentSnapshot
import com.sample.todo.data.core.DataScope
import com.sample.todo.data.core.Mapper
import com.sample.todo.dynamic.data.task.firestore.entity.TaskFieldName
import com.sample.todo.dynamic.data.task.firestore.getOrThrow
import com.sample.todo.main.tasks.library.domain.entity.TaskMini
import javax.inject.Inject

@DataScope
class DocumentSnapshotToTaskMini @Inject constructor() : Mapper<DocumentSnapshot, TaskMini> {
    override fun invoke(from: DocumentSnapshot): TaskMini {
        return TaskMini(
            id = from.id,
            title = from.data.getOrThrow(TaskFieldName.title),
            isCompleted = from.data.getOrThrow(TaskFieldName.isCompleted)
        )
    }
}
