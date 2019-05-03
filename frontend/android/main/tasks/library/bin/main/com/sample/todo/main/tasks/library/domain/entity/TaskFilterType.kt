package com.sample.todo.main.tasks.library.domain.entity

enum class TaskFilterType {
    ALL,
    COMPLETED,
    ACTIVE;

    companion object {
        fun parse(ordinal: Int): TaskFilterType? {
            return TaskFilterType.values().firstOrNull { it.ordinal == ordinal }
        }
    }
}
