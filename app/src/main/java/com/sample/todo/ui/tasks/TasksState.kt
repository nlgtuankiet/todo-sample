package com.sample.todo.ui.tasks

import androidx.paging.PagedList
import com.airbnb.mvrx.MvRxState
import com.sample.todo.domain.model.TaskFilterType
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.util.FabData
import com.sample.todo.util.ToolbarData

data class TasksState(
    val toolbarData: ToolbarData?,
    val fabData: FabData?,
    val isInEditState: Boolean,
    val selectAll: Boolean,
    val selectedIds: HashSet<String>?,
    val filterType: TaskFilterType?,
    val tasksMini: PagedList<TaskMini>?,
    val isNoTasks: Boolean?,
    val noTaskIcon: Int?,
    val noTaskLabel: Int?,
    val isToolbarSelected: Boolean,
    val toolbarSubTitle: Int?
) : MvRxState