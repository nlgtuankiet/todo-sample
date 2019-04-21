package com.sample.todo.data.task.firestore.mapper

import com.google.firebase.firestore.DocumentSnapshot
import com.sample.todo.data.Mapper
import com.sample.todo.data.core.DataScope
import com.sample.todo.data.task.firestore.entity.TaskFieldName
import com.sample.todo.data.task.firestore.getOrThrow
import com.sample.todo.domain.model.Task
import org.threeten.bp.Instant
import javax.inject.Inject

@DataScope
class TaskSnapshotMapper @Inject constructor() : Mapper<DocumentSnapshot, Task> {
    override fun map(from: DocumentSnapshot): Task {
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
