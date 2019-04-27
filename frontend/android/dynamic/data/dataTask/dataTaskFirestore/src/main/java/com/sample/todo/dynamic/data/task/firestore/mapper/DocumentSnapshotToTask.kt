package com.sample.todo.dynamic.data.task.firestore.mapper

import com.google.firebase.firestore.DocumentSnapshot
import com.sample.todo.data.core.DataScope
import com.sample.todo.data.core.Mapper
import com.sample.todo.domain.model.Task
import com.sample.todo.dynamic.data.task.firestore.entity.TaskFieldName
import com.sample.todo.dynamic.data.task.firestore.getOrThrow
import org.threeten.bp.Instant
import javax.inject.Inject

@DataScope
class DocumentSnapshotToTask @Inject constructor() : Mapper<DocumentSnapshot, Task> {
    override fun invoke(from: DocumentSnapshot): Task {
        val createTime = from.getLong(TaskFieldName.createTime) ?: throw RuntimeException("")
        val updateTime = from.getLong(TaskFieldName.updateTime) ?: throw RuntimeException("")
        return Task(
            id = from.id,
            title = from.data.getOrThrow(TaskFieldName.title),
            description = from.data.getOrThrow(TaskFieldName.description),
            isCompleted = from.data.getOrThrow(TaskFieldName.isCompleted),
            createTime = Instant.ofEpochSecond(createTime),
            updateTime = Instant.ofEpochSecond(updateTime)
        )
    }
}
