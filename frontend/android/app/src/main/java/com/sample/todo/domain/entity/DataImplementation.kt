package com.sample.todo.domain.entity

enum class DataImplementation {
    ROOM,
    FIRESTORE,
    SQLDELIGHT;

    companion object {
        fun parse(value: Int): DataImplementation? {
            return DataImplementation.values().find { it.ordinal == value }
        }
    }
}
