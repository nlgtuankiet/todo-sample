package com.sample.todo.ui.taskdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.toLiveData
import androidx.navigation.NavDirections
import com.sample.todo.R
import com.sample.todo.core.BaseViewModel
import com.sample.todo.core.Event
import com.sample.todo.domain.model.TaskId
import com.sample.todo.domain.usecase.DeleteTask
import com.sample.todo.domain.usecase.GetTaskFlowable
import com.sample.todo.domain.usecase.UpdateComplete
import com.sample.todo.ui.message.Message
import com.sample.todo.util.ToolbarData
import com.sample.todo.util.extension.postNewEvent
import com.sample.todo.util.extension.postNewMessage
import com.sample.todo.util.extension.setValueIfNew
import io.reactivex.Flowable
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * [ViewModel] for [TaskDetailFragment]
 */
// TODO find solution for refreshing task
class TaskDetailViewModel @Inject constructor(
    private val args: TaskDetailFragmentArgs,
    private val getTaskFlowable: GetTaskFlowable,
    private val updateComplete: UpdateComplete,
    private val deleteTask: DeleteTask
) : BaseViewModel() {

    val toolbarListenerData = ToolbarData(
        navigationIcon = R.drawable.toolbar_navigation_icon,
        navigationClickHandler = this::onNavigationClick,
        menu = R.menu.task_detail_menu,
        menuItemClickHandler = this::onToolbarMenuClick
    )

    private val getTaskResult: Flowable<GetTaskFlowable.Result> =
        getTaskFlowable(TaskId(args.taskId))

    // TODO base on init load and refresh event
    private val _currentTask = getTaskResult.toLiveData()

    val title = _currentTask.map { it.getOrNull()?.title }
    val description = _currentTask.map { it.getOrNull()?.description }
    val isCompleted = _currentTask.map { it.getOrNull()?.isCompleted }

    private val _snackBarMessage = MutableLiveData<Event<Message>>()
    val snackBarMessage: LiveData<Event<Message>>
        get() = _snackBarMessage

    private val _navigationEvent = MutableLiveData<Event<NavDirections>>()
    val navigationEvent: LiveData<Event<NavDirections>>
        get() = _navigationEvent

    private val _navigateUpEvent = MutableLiveData<Event<Unit>>()
    val navigateUpEvent: LiveData<Event<Unit>>
        get() = _navigateUpEvent

    private val _loadErrorEvent = MutableLiveData<Event<Unit>>()
    val loadErrorEvent: LiveData<Event<Unit>>
        get() = _loadErrorEvent

    private val _addNotificationEvent = MutableLiveData<Event<String>>()
    val addNotificationEvent: LiveData<Event<String>>
        get() = _addNotificationEvent

    private val _isLoading = MediatorLiveData<Boolean>().apply {
        addSource(_currentTask) {
            setValueIfNew(it == null)
        }
        addSource(_loadErrorEvent) {
            setValueIfNew(false)
        }
    }
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val isUiVisible: LiveData<Boolean> = isLoading.map { !it }

    init {
    }

    fun onFabButtonClick() {
        _navigationEvent.value =
            Event(TaskDetailFragmentDirections.actionTaskDetailFragmentToAddEditFragment(args.taskId))
    }

    private fun onNavigationClick() {
        _navigateUpEvent.value = Event(Unit)
    }

    private fun onToolbarMenuClick(menuId: Int): Boolean {
        Timber.d("onToolbarMenuClick(menuId=$menuId)")
        when (menuId) {
            R.id.add_notification -> _addNotificationEvent.value = Event(args.taskId)
            R.id.delete -> deleteTask()
        }

        return true
    }

    private fun deleteTask() {
        launch {
            runCatching { deleteTask(taskId = TaskId(args.taskId)) }
                .onSuccess {
                    _navigateUpEvent.postNewEvent()
                    // TODO how map add message back map task screen?
                    _snackBarMessage.postNewMessage(messageId = R.string.task_detail_delete_task_success)
                }
                .onFailure {
                    _snackBarMessage.postNewMessage(messageId = R.string.task_detail_delete_task_fail)
                }
        }
    }

    fun onCheckedChange(checked: Boolean) {
        Timber.d("onCheckedChange(checked: $checked)")
        if (_currentTask.value?.getOrNull()?.isCompleted != checked) {
            launch {
                runCatching { updateComplete(args.taskId, checked) }
                    .onSuccess {
                        _snackBarMessage.postNewMessage(messageId = R.string.task_detail_update_complete_success)
                    }
                    .onFailure {
                        _snackBarMessage.postNewMessage(messageId = R.string.task_detail_update_complete_fail)
                    }
            }
        }
    }
}