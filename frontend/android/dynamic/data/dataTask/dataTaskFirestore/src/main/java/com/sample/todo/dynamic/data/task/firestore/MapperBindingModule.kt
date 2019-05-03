package com.sample.todo.dynamic.data.task.firestore

import com.google.firebase.firestore.DocumentSnapshot
import com.sample.todo.data.core.Mapper
import com.sample.todo.domain.entity.Task
import com.sample.todo.dynamic.data.task.firestore.mapper.DocumentSnapshotToTask
import com.sample.todo.dynamic.data.task.firestore.mapper.DocumentSnapshotToTaskMini
import com.sample.todo.main.tasks.library.domain.entity.TaskMini
import dagger.Binds
import dagger.Module

@Module
interface MapperBindingModule {
    @Binds
    fun bindTaskMapper(mapper: DocumentSnapshotToTask): Mapper<DocumentSnapshot, Task>

    @Binds
    fun bindTaskMiniMapper(mapper: DocumentSnapshotToTaskMini): Mapper<DocumentSnapshot, TaskMini>
}
