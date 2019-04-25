package com.sample.todo.data.task.firestore

import com.google.firebase.firestore.DocumentSnapshot
import com.sample.todo.data.core.Mapper
import com.sample.todo.data.task.firestore.mapper.DocumentSnapshotToTaskMini
import com.sample.todo.data.task.firestore.mapper.DocumentSnapshotToTask
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskMini
import dagger.Binds
import dagger.Module

@Module
interface MapperBindingModule {
    @Binds
    fun bindTaskMapper(mapper: DocumentSnapshotToTask): Mapper<DocumentSnapshot, Task>

    @Binds
    fun bindTaskMiniMapper(mapper: DocumentSnapshotToTaskMini): Mapper<DocumentSnapshot, TaskMini>
}
