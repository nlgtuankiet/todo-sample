package com.sample.todo.tasks

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.sample.todo.base.Event
import com.sample.todo.base.MvRxViewModel
import com.sample.todo.base.extension.getFragment
import com.sample.todo.base.extension.postNewMessage
import com.sample.todo.base.extension.setValueIfNew
import com.sample.todo.base.message.Message
import com.sample.todo.base.widget.FabData
import com.sample.todo.base.widget.ToolbarData
import com.sample.todo.domain.model.TaskFilterType
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.usecase.GetTaskFilterTypeObservable
import com.sample.todo.domain.usecase.GetTaskMiniList
import com.sample.todo.domain.usecase.SetTaskFilterType
import com.sample.todo.domain.usecase.UpdateComplete
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

/**
 * ViewModel for [TasksFragment]
 */
@Keep
class TasksViewModel @AssistedInject constructor(
    @Assisted initialState: TasksState,
    private val updateComplete: UpdateComplete,
    private val setLastTaskFilterType: SetTaskFilterType,
    getLastTaskFilterTypeObservable: GetTaskFilterTypeObservable,
    getAllTaskMini: GetTaskMiniList
) : MvRxViewModel<TasksState>(initialState) {

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: TasksState): TasksViewModel
    }

    companion object : MvRxViewModelFactory<TasksViewModel, TasksState> {
        @Keep
        override fun create(
            viewModelContext: ViewModelContext,
            state: TasksState
        ): TasksViewModel? {
            return viewModelContext.getFragment<TasksFragment>().viewModelFactory.create(state)
        }

        @Keep
        override fun initialState(viewModelContext: ViewModelContext): TasksState? {

            return TasksState(
                toolbarData = null,
                fabData = null,
                isInEditState = false,
                selectAll = false,
                selectedIds = null,
                filterType = null,
                tasksMini = null,
                isNoTasks = null,
                noTaskIcon = null,
                noTaskLabel = null,
                isToolbarSelected = false,
                toolbarSubTitle = null
            )
        }
    }

    init {
        logStateChanges()
        getLastTaskFilterTypeObservable().execute {
            val filter = it() ?: return@execute this
            val noTaskIcon = when (filter) {
                TaskFilterType.ALL -> R.drawable.ic_assignment_24dp
                TaskFilterType.ACTIVE -> R.drawable.ic_assignment_late_24dp
                TaskFilterType.COMPLETED -> R.drawable.ic_assignment_turned_in_24dp
            }
            val noTaskLabel = when (filter) {
                TaskFilterType.ALL -> R.string.tasks_no_task_all
                TaskFilterType.ACTIVE -> R.string.tasks_no_task_active
                TaskFilterType.COMPLETED -> R.string.tasks_no_task_completed
            }
            val toolbarSubTitle = when (filter) {
                TaskFilterType.ALL -> R.string.tasks_filter_all_header
                TaskFilterType.ACTIVE -> R.string.tasks_filter_active_header
                TaskFilterType.COMPLETED -> R.string.tasks_filter_completed_header
            }
            copy(
                filterType = filter,
                noTaskIcon = noTaskIcon,
                noTaskLabel = noTaskLabel,
                toolbarSubTitle = toolbarSubTitle,
                tasksMini = null,
                isNoTasks = true
            )
        }

        getAllTaskMini()
            .execute {
                copy(
                    tasksMini = it(),
                    isNoTasks = it()?.isEmpty() ?: true
                )
            }
    }

    private val toolbarDataInNormalState = run {
        ToolbarData(
            menu = R.menu.tasks_toolbar_normal,
            menuItemClickHandler = this::onToolbarMenuClickInNormalState
        )
    }.also {
        setState { copy(toolbarData = it) }
    }

    private val fabDataInNormalState = run {
        FabData(
            icon = R.drawable.ic_add_24dp,
            onClickHandler = this::onFabButtonClickInNormalState
        )
    }.also {
        setState { copy(fabData = it) }
    }

    private val fabDataInEditState = FabData(
        icon = R.drawable.check_box,
        onClickHandler = this::onFabButtonClickInEditState
    )

    private val toolbarDataInEditState = ToolbarData(
        menu = R.menu.tasks_toolbar_editing,
        menuItemClickHandler = this::onToolbarMenuClickInEditState
    )

    private fun onToolbarMenuClickInNormalState(menuId: Int): Boolean {
        when (menuId) {
            R.id.edit -> {
                setState {
                    copy(
                        toolbarData = toolbarDataInEditState,
                        fabData = fabDataInEditState,
                        isInEditState = true,
                        selectAll = false,
                        selectedIds = HashSet()
                    )
                }
            }
            R.id.filter -> {
                _openFilterPopupEvent.value = Event(Unit)
            }
        }
        return true
    }

    private fun onToolbarMenuClickInEditState(menuId: Int): Boolean {
        when (menuId) {
            R.id.cancel -> {
                setState {
                    copy(
                        toolbarData = toolbarDataInNormalState,
                        fabData = fabDataInNormalState,
                        isInEditState = false,
                        selectAll = false,
                        selectedIds = null
                    )
                }
            }
        }
        return true
    }

    // TODO what the heck is this?
    fun onSelectCheckedChange(taskId: String, checked: Boolean) {
        if (checked) {
            setState {
                val newSelectedIds = selectedIds
                newSelectedIds?.add(taskId)
                copy(
                    selectedIds = newSelectedIds
                )
            }
        } else {
            setState {
                val newSelectedIds = selectedIds
                newSelectedIds?.remove(taskId)
                copy(
                    selectedIds = newSelectedIds
                )
            }
        }
    }

    private val _snackBarMessage = MutableLiveData<Event<Message>>()
    val snackBarMessage: LiveData<Event<Message>>
        get() = _snackBarMessage

    private val _navigationEvent = MutableLiveData<Event<NavDirections>>()
    val navigationEvent: LiveData<Event<NavDirections>>
        get() = _navigationEvent

    private val _openFilterPopupEvent = MutableLiveData<Event<Unit>>()
    val openFilterPopupEvent: LiveData<Event<Unit>>
        get() = _openFilterPopupEvent

    private val _isToolbarSelected = MutableLiveData<Boolean>()
    val isToolbarSelected: LiveData<Boolean>
        get() = _isToolbarSelected

    fun onTasksRecyclerViewScroll(canScrollUp: Boolean) {
        _isToolbarSelected.setValueIfNew(canScrollUp)
    }

    fun onTaskItemClick(taskId: String?, checked: Boolean?) {
        withState {
            if (it.isInEditState) {
                if (taskId == null || checked == null)
                    TODO()
                onSelectCheckedChange(taskId, checked)
            } else {
                if (taskId != null) {
                    _navigationEvent.postValue(
                        Event(
                            TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment(
                                taskId
                            )
                        )
                    )
                }
            }
        }
    }

    private fun onFabButtonClickInNormalState() {
        _navigationEvent.value =
            Event(TasksFragmentDirections.actionTasksFragmentToAddEditFragment(null))
    }

    private fun onFabButtonClickInEditState() {
    }

    fun onFilterMenuSelected(menuId: Int) {
        val filterType = when (menuId) {
            R.id.all -> TaskFilterType.ALL
            R.id.active -> TaskFilterType.ACTIVE
            R.id.completed -> TaskFilterType.COMPLETED
            else -> throw IllegalArgumentException("Unknown menuId: $menuId")
        }
        viewModelScope.launch {
            // TODO handle error
            val result = setLastTaskFilterType(filterType)
        }
    }

    fun onTaskCheckBoxClick(taskMini: TaskMini?, checked: Boolean?) {
        if (taskMini == null || checked == null) return
        if (taskMini.isCompleted == checked) return
        viewModelScope.launch {
            runCatching { updateComplete(taskMini.id, checked) }
                .onSuccess {
                    _snackBarMessage.postNewMessage(messageId = R.string.tasks_update_complete_success)
                }
                .onFailure {
                    _snackBarMessage.postNewMessage(messageId = R.string.tasks_update_complete_fail)
                }
        }
    }
}
