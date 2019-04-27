package com.sample.todo.domain.entity

enum class DataImplementation {
    ROOM,
    FIRESTORE,
    SQLDELIGHT;

    val componentClassName: String
        get() = when (this) {
            ROOM -> "com.sample.todo.data.task.room.RoomDataComponent"
            FIRESTORE -> "com.sample.todo.dynamic.data.task.firestore.FirestoreDataComponent"
            SQLDELIGHT -> "com.sample.todo.data.task.sqldelight.SqlDelightDataComponent"
        }
    companion object {
        fun fromOdinal(ordinal: Int): DataImplementation? {
            return values().find { it.ordinal == ordinal }
        }
    }
}
/**
 *         private const val roomClassName = "com.sample.todo.data.task.room.RoomDataComponent"
private const val fireStoreClassName = "com.sample.todo.data.task.firestore.FirestoreDataComponent"
private const val sqlDelightClassName = "com.sample.todo.data.task.sqldelight.SqlDelightDataComponent"
fun from(className: String): DataImplementation? = DataImplementation::class.nestedClasses.find {
(it.objectInstance as DataImplementation).className == className
}?.objectInstance as? DataImplementation
 */
