package com.sample.todo.main.taskdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.sample.todo.base.entity.Event
import com.sample.todo.base.extension.map
import com.sample.todo.base.extension.postNewEvent
import com.sample.todo.base.extension.postNewMessage
import com.sample.todo.base.extension.postValueIfNew
import com.sample.todo.base.message.Message
import com.sample.todo.base.widget.ToolbarData
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskId
import com.sample.todo.domain.usecase.DeleteTask
import com.sample.todo.domain.usecase.GetTaskObservable
import com.sample.todo.domain.usecase.UpdateComplete
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * [ViewModel] for [TaskDetailFragment]
 */
class TaskDetailViewModel @Inject constructor(
    private val args: TaskDetailFragmentArgs,
    private val getTaskFlowable: GetTaskObservable,
    private val updateComplete: UpdateComplete,
    private val deleteTask: DeleteTask
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val toolbarData = ToolbarData(
        navigationIcon = R.drawable.toolbar_navigation_icon,
        navigationClickHandler = { onNavigationClick() },
        menu = R.menu.task_detail_menu,
        menuItemClickHandler = { id -> onToolbarMenuClick(id) }
    )

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

    private val _taskNotFound = MutableLiveData<Boolean>().apply { value = false }
    val taskNotFound: LiveData<Boolean>
        get() = _taskNotFound

    private val _addNotificationEvent = MutableLiveData<Event<String>>()
    val addNotificationEvent: LiveData<Event<String>>
        get() = _addNotificationEvent

    private var originalTask: Task? = null

    val currentTask = getTaskFlowable(TaskId(args.taskId))
        .doOnNext {
            _loading.postValueIfNew(false)
            val found = when (it) {
                is GetTaskObservable.Result.Found -> true
                is GetTaskObservable.Result.TaskNotFound -> false
            }
            _taskNotFound.postValueIfNew(!found)
        }
        .map {
            val task = when (it) {
                is GetTaskObservable.Result.Found -> it.task
                is GetTaskObservable.Result.TaskNotFound -> Task.DEFAULT
            }
            originalTask = task
            task
        }
        .toFlowable(BackpressureStrategy.LATEST)
        .toLiveData()

    val title = currentTask.map { it.title }

    val description = currentTask.map { it.description ?: "" }

    val isCompleted = currentTask.map { it.isCompleted }

    private val _loading = MutableLiveData<Boolean>().apply { value = true }
    val loading: LiveData<Boolean>
        get() = _loading

    fun onFabButtonClick() {
        _navigationEvent.value = Event(TaskDetailFragmentDirections.toAddEditFragment(args.taskId))
    }

    private fun onNavigationClick() {
        _navigateUpEvent.value = Event(Unit)
    }

    private fun onToolbarMenuClick(menuId: Int): Boolean {
        Timber.d("onToolbarMenuClick(menuId=$menuId)")
        when (menuId) {
            R.id.add_notification -> _addNotificationEvent.value =
                Event(args.taskId)
            R.id.delete -> deleteTask()
        }

        return true
    }

    private fun deleteTask() {
        viewModelScope.launch {
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
        if (originalTask?.isCompleted != checked) {
            viewModelScope.launch {
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
