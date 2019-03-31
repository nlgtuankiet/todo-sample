package com.sample.todo.main.tasks

import com.sample.todo.base.widget.FabData
import com.sample.todo.base.widget.ToolbarData

sealed class TasksViewState {
    data class Normal(
        val toolbarData: ToolbarData,
        val floatingActionButtonData: FabData
    ) : TasksViewState()
    data class Edit(
        val toolbarData: ToolbarData,
        val floatingActionButtonData: FabData,
        val selectedId: HashSet<String>,
        val selectAll: Boolean
    ) : TasksViewState()
}