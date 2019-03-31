
package com.sample.todo.main.tasks

import com.sample.todo.domain.model.TaskMini
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.airbnb.mvrx.withState

class TasksController(
    private val tasksViewModel: TasksViewModel
) : PagedListEpoxyController<TaskMini>() {
    override fun buildItemModel(currentPosition: Int, item: TaskMini?): EpoxyModel<*> {
        return TaskMiniItemBindingModel_().apply {
            id(item?.id ?: "")
            viewModel(tasksViewModel)
            val taskItem = withState(tasksViewModel) { state ->
                val isSelected = item?.id?.let { id ->
                    state.selectedIds?.contains(id) ?: false
                } ?: false
                TaskItem(
                    taskMini = item,
                    isSelected = isSelected,
                    isInEditMode = state.isInEditState
                )
            }
            item(taskItem)
        }
    }

    override fun onExceptionSwallowed(exception: RuntimeException) {
        throw exception
    }
}
