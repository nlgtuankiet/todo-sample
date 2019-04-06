package com.sample.todo.data

import com.sample.todo.TodoApplication
import com.sample.todo.base.di.ApplicationScope
import com.sample.todo.data.task.firestore.FirestoreDataComponent
import com.sample.todo.data.task.room.RoomDataComponent
import com.sample.todo.data.task.sqldelight.SqlDelightDataComponent
import com.sample.todo.domain.entity.DataImplementation
import com.sample.todo.domain.repository.DataImplementationRepository
import javax.inject.Inject

@ApplicationScope
class DataImplementationRepositoryImpl @Inject constructor(
    private val todoApplication: TodoApplication
): DataImplementationRepository {
    override fun currentImplementation() = when (todoApplication.dataComponent) {
        is RoomDataComponent ->  DataImplementation.ROOM
        is FirestoreDataComponent -> DataImplementation.FIRESTORE
        is SqlDelightDataComponent -> DataImplementation.SQLDELIGHT
        else -> throw RuntimeException("Unknown data component")
    }
}