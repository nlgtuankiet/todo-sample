package com.sample.todo.domain.model

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