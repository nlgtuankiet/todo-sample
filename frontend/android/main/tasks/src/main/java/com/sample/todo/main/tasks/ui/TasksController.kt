
package com.sample.todo.main.tasks.ui

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.airbnb.mvrx.withState
import com.sample.todo.base.di.FragmentScope
import com.sample.todo.base.Holder
import com.sample.todo.main.tasks.TaskMiniItemBindingModel_
import com.sample.todo.main.tasks.library.domain.entity.TaskMini
import javax.inject.Inject

@FragmentScope
class TasksController @Inject constructor(
    private val holder: Holder<TasksFragment>
) : PagedListEpoxyController<TaskMini>() {
    private val tasksViewModel: TasksViewModel by lazy { holder.instance.tasksViewModel }

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
