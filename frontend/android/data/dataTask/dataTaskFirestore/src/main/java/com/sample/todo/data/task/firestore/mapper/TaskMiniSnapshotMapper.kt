package com.sample.todo.data.task.firestore.mapper

import com.google.firebase.firestore.DocumentSnapshot
import com.sample.todo.data.Mapper
import com.sample.todo.data.core.DataScope
import com.sample.todo.data.task.firestore.entity.TaskFieldName
import com.sample.todo.data.task.firestore.getOrThrow
import com.sample.todo.domain.model.TaskMini
import javax.inject.Inject

@DataScope
class TaskMiniSnapshotMapper @Inject constructor() : Mapper<DocumentSnapshot, TaskMini> {
    override fun map(from: DocumentSnapshot): TaskMini {
        return TaskMini(
            id = from.id,
            title = from.data.getOrThrow(TaskFieldName.title),
            isCompleted = from.data.getOrThrow(TaskFieldName.isCompleted)
        )
    }
}
