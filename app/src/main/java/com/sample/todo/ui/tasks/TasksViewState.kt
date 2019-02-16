package com.sample.todo.ui.tasks

import com.sample.todo.util.FabData
import com.sample.todo.util.ToolbarData

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