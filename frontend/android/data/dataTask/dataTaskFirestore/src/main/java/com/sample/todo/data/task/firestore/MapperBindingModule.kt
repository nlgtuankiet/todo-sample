package com.sample.todo.data.task.firestore

import com.google.firebase.firestore.DocumentSnapshot
import com.sample.todo.data.Mapper
import com.sample.todo.data.task.firestore.mapper.TaskMiniSnapshotMapper
import com.sample.todo.data.task.firestore.mapper.TaskSnapshotMapper
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskMini
import dagger.Binds
import dagger.Module

@Module
interface MapperBindingModule {
    @Binds
    fun bindTaskMapper(mapper: TaskSnapshotMapper): Mapper<DocumentSnapshot, Task>

    @Binds
    fun bindTaskMiniMapper(mapper: TaskMiniSnapshotMapper): Mapper<DocumentSnapshot, TaskMini>
}
