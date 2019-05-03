package com.sample.todo.main.tasks.ui

import com.sample.todo.main.tasks.library.domain.entity.TaskMini

data class TaskItem(
    val taskMini: TaskMini?,
    val isSelected: Boolean,
    val isInEditMode: Boolean
)
