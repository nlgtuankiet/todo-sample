package com.sample.todo.domain.entity

import org.threeten.bp.Instant

data class Task(
    val id: String = TaskId.newInstance().value,
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false,
    val createTime: Instant = Instant.now(),
    val updateTime: Instant = Instant.now()
) {
    companion object {
        val DEFAULT = Task(
            id = "",
            title = "",
            description = null,
            isCompleted = false,
            createTime = Instant.MIN,
            updateTime = Instant.MIN
        )
    }
}
