package com.sample.todo.tasks

import com.sample.todo.domain.model.TaskMini

data class TaskItem(
    val taskMini: TaskMini?,
    val isSelected: Boolean,
    val isInEditMode: Boolean
)