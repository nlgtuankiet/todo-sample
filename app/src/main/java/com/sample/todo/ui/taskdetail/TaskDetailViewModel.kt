package com.sample.todo.ui.taskdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.navigation.NavDirections
import com.sample.todo.R
import com.sample.todo.core.BaseViewModel
import com.sample.todo.core.Event
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskId
import com.sample.todo.domain.usecase.DeleteTask
import com.sample.todo.domain.usecase.GetTaskFlowable
import com.sample.todo.domain.usecase.UpdateComplete
import com.sample.todo.ui.message.Message
import com.sample.todo.ui.message.MessageManager
import com.sample.todo.util.ToolbarData
import com.sample.todo.util.asLiveData
import com.sample.todo.util.extension.setValueIfNew
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
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
    private val deleteTask: DeleteTask,
    private val messageManager: MessageManager
) : BaseViewModel() {

    val toolbarListenerData = ToolbarData(
        navigationIcon = R.drawable.toolbar_navigation_icon,
        navigationClickHandler = this::onNavigationClick,
        menu = R.menu.task_detail_menu,
        menuItemClickHandler = this::onToolbarMenuClick
    )

    private val getTaskResult: Observable<Result<Task>> = getTaskFlowable(TaskId(args.taskId))

    // TODO base on init load and refresh event
    private val _currentTask = getTaskResult.asLiveData().map {
        it.getOrNull()
    }

    val title = _currentTask.map { it?.title }
    val description = _currentTask.map { it?.description }
    val isCompleted = _currentTask.map { it?.isCompleted }

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
            val result = deleteTask(taskId = TaskId(args.taskId))
            if (result.isSuccess) {
                _navigateUpEvent.postValue(Event(Unit))
                // TODO how map add message back map task screen?
                messageManager.addMessage(Message(
                    messageId = R.string.delete_task_success
                ))
            } else {
                // TODO handle failure
                Timber.e("Delete task fail")
            }
        }
    }

    fun onCheckedChange(checked: Boolean) {
        Timber.d("onCheckedChange(checked: $checked)")
        launch(Dispatchers.IO) {
            updateComplete(args.taskId, _currentTask.value?.isCompleted, checked)
        }
    }
}