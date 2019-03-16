package com.sample.todo.ui.tasks

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.toLiveData
import androidx.navigation.NavDirections
import androidx.paging.PagedList
import com.sample.todo.R
import com.sample.todo.core.BaseViewModel
import com.sample.todo.core.Event
import com.sample.todo.core.checkAllMatched
import com.sample.todo.domain.model.TaskFilterType
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.usecase.GetTaskFilterTypeFlowable
import com.sample.todo.domain.usecase.GetTaskMiniList
import com.sample.todo.domain.usecase.SetTaskFilterType
import com.sample.todo.domain.usecase.UpdateComplete
import com.sample.todo.ui.message.Message
import com.sample.todo.util.FabData
import com.sample.todo.util.ToolbarData
import com.sample.todo.util.extension.postNewMessage
import com.sample.todo.util.extension.setValueIfNew
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel for [TasksFragment]
 *
 */
class TasksViewModel @Inject constructor(
    private val getAllTaskMini: GetTaskMiniList,
    private val updateComplete: UpdateComplete,
    private val getLastTaskFilterTypeLive: GetTaskFilterTypeFlowable,
    private val setLastTaskFilterType: SetTaskFilterType
) : BaseViewModel() {

//    val bottomAppBarListenerData = ToolbarData(
//        navigationIcon = R.drawable.bottom_app_bar_navigation_icon,
//        navigationClickHandler = this::onNavigationOnClicked,
//        menu = R.menu.tasks,
//        menuItemClickHandler = this::onBottomAppBarMenuItemClickInNormalState
//    )

    private val toolbarDataInNormalState = ToolbarData(
        menu = R.menu.tasks_toolbar_normal,
        menuItemClickHandler = this::onToolbarMenuClickInNormalState
    )

    val fabDataInNormalState = FabData(
        icon = R.drawable.ic_add_24dp,
        onClickHandler = this::onFabButtonClickInNormalState
    )

    val fabDataInEditState = FabData(
        icon = R.drawable.check_box,
        onClickHandler = this::onFabButtonClickInEditState
    )

    private val normalViewState = TasksViewState.Normal(
        toolbarData = toolbarDataInNormalState,
        floatingActionButtonData = fabDataInNormalState
    )

    private val toolbarDataInEditState = ToolbarData(
        menu = R.menu.tasks_toolbar_editing,
        menuItemClickHandler = this::onToolbarMenuClickInEditState
    )

    private val editViewState = TasksViewState.Edit(
        toolbarData = toolbarDataInEditState,
        floatingActionButtonData = fabDataInEditState,
        selectedId = HashSet(),
        selectAll = false
    )

    private fun onToolbarMenuClickInNormalState(menuId: Int): Boolean {
        when (menuId) {
            R.id.edit -> {
                editViewState.selectedId.clear()
                viewState.value = editViewState
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
                viewState.value = normalViewState
            }
        }
        return true
    }

    val viewState = MutableLiveData<TasksViewState>(normalViewState)

    val selectedId: LiveData<Set<String>> = viewState.map { state ->
        if (state is TasksViewState.Edit) {
            return@map state.selectedId
        }
        return@map emptySet<String>()
    }

    val isInEditState: LiveData<Boolean> = viewState.map {
        if (it == null) return@map false
        it is TasksViewState.Edit
    }

    val selectAll: LiveData<Boolean> = viewState.map { state ->
        if (state is TasksViewState.Edit) {
            return@map state.selectAll
        }
        return@map false
    }

    val fabData = viewState.map {
        return@map when (it) {
            is TasksViewState.Normal -> {
                it.floatingActionButtonData
            }
            is TasksViewState.Edit -> {
                it.floatingActionButtonData
            }
        }.checkAllMatched
    }

    val toolbarData = viewState.map {
        return@map when (it) {
            is TasksViewState.Normal -> {
                it.toolbarData
            }
            is TasksViewState.Edit -> {
                it.toolbarData
            }
        }.checkAllMatched
    }

    fun onSelectCheckedChange(taskId: String, checked: Boolean) {
        Timber.d("taskId=$taskId, checked=$checked")
        val state = viewState.value as? TasksViewState.Edit ?: return
        val oldSet = state.selectedId
        var setChanged = false
        if (checked) {
            if (!oldSet.contains(taskId)) {
                oldSet.add(taskId)
                setChanged = true
            }
        } else {
            setChanged = oldSet.remove(taskId)
        }

        if (setChanged) {
            viewState.value = state.copy(selectedId = oldSet)
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

    private val _showBottomSheetEvent = MutableLiveData<Event<Unit>>()
    val showBottomSheetEvent: LiveData<Event<Unit>>
        get() = _showBottomSheetEvent

    val currentFilter: LiveData<TaskFilterType> = getLastTaskFilterTypeLive()
        .toLiveData()
        .distinctUntilChanged()

    val filterChangeEvent = currentFilter.map { Event(Unit) }

    val currentTasks: LiveData<PagedList<TaskMini>> = currentFilter.switchMap {
        Timber.d("new task filter: $it trigger new tasks")
        getAllTaskMini(it).toLiveData()
    }

    val isNoTasks = currentTasks.map {
        it.isEmpty()
    }

    val noTaskIconDrawableRes = currentFilter.map {
        when (it) {
            TaskFilterType.ALL -> R.drawable.ic_assignment_24dp
            TaskFilterType.ACTIVE -> R.drawable.ic_assignment_late_24dp
            TaskFilterType.COMPLETED -> R.drawable.ic_assignment_turned_in_24dp
        }
    }

    val noTaskLabelStringRes = currentFilter.map {
        return@map when (it) {
            TaskFilterType.ALL -> R.string.tasks_no_task_all
            TaskFilterType.ACTIVE -> R.string.tasks_no_task_active
            TaskFilterType.COMPLETED -> R.string.tasks_no_task_completed
        }
    }

    private val _isToolbarSelected = MutableLiveData<Boolean>()
    val isToolbarSelected: LiveData<Boolean>
        get() = _isToolbarSelected

    @StringRes
    val toolbarSubTitle = currentFilter.map {
        when (it) {
            TaskFilterType.ALL -> R.string.tasks_filter_all_header
            TaskFilterType.ACTIVE -> R.string.tasks_filter_active_header
            TaskFilterType.COMPLETED -> R.string.tasks_filter_completed_header
        }
    }

    fun onTasksRecyclerViewScroll(canScrollUp: Boolean) {
        Timber.d("onTasksRecyclerViewScroll(canScrollUp=$canScrollUp)")
        _isToolbarSelected.setValueIfNew(canScrollUp)
    }

    // affected by view state
    fun onTaskItemClick(taskId: String?) {
        Timber.d("onTaskItemClick(taskId=$taskId)")
        if (taskId == null) return
        _navigationEvent.value =
            Event(TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment(taskId))
    }

    private fun onFabButtonClickInNormalState() {
        _navigationEvent.value =
            Event(TasksFragmentDirections.actionTasksFragmentToAddEditFragment(null))
    }

    private fun onFabButtonClickInEditState() {
        Timber.d("onFabButtonClickInEditState()")
    }

    fun onFilterMenuSelected(menuId: Int) {
        val filterType = when (menuId) {
            R.id.all -> TaskFilterType.ALL
            R.id.active -> TaskFilterType.ACTIVE
            R.id.completed -> TaskFilterType.COMPLETED
            else -> throw IllegalArgumentException("Unknown menuId: $menuId")
        }
        launch {
            // TODO handle error
            val result = setLastTaskFilterType(filterType)
            Timber.d("setLastTaskFilterType result: $result")
        }
    }

//    private fun onBottomAppBarMenuItemClickInNormalState(menuId: Int): Boolean {
//        Timber.d("onBottomAppBarMenuItemClickInNormalState()")
//        when (menuId) {
//            R.id.filter -> _openFilterPopupEvent.value = Event(Unit)
//            else -> {
//                Timber.w("Unknown menuId: $menuId")
//                return false
//            }
//        }
//        return true
//    }
//
//    private fun onBottomAppBarMenuItemClickInEditState(menuId: Int): Boolean {
//        Timber.d("onBottomAppBarMenuItemClickInEditState()")
//        return true
//    }

    fun onTaskCheckBoxClick(taskMini: TaskMini?, checked: Boolean?) {
        Timber.d("onTaskCheckBoxClick(taskMini: $taskMini, checked: $checked)")
        if (taskMini == null || checked == null) return
            launch {
                runCatching { updateComplete(taskMini.id, checked) }
                    .onSuccess {
                        _snackBarMessage.postNewMessage(messageId = R.string.task_detail_update_complete_success)
                    }
                    .onFailure {
                        _snackBarMessage.postNewMessage(messageId = R.string.task_detail_update_complete_fail)
                    }
            }
    }

    fun onNavigationOnClicked() {
        _showBottomSheetEvent.value = Event(Unit)
    }
}
