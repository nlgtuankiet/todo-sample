package com.sample.todo.ui.taskdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.sample.todo.R
import com.sample.todo.core.Event
import com.sample.todo.core.MvRxViewModel
import com.sample.todo.core.ViewModelArgumentFactory
import com.sample.todo.domain.model.TaskId
import com.sample.todo.domain.usecase.DeleteTask
import com.sample.todo.domain.usecase.GetTaskObservable
import com.sample.todo.domain.usecase.UpdateComplete
import com.sample.todo.ui.message.Message
import com.sample.todo.util.ToolbarData
import com.sample.todo.util.extension.arguments
import com.sample.todo.util.extension.getFragment
import com.sample.todo.util.extension.postNewEvent
import com.sample.todo.util.extension.postNewMessage
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * [ViewModel] for [TaskDetailFragment]
 */
class TaskDetailViewModel @AssistedInject constructor(
    @Assisted private val initialState: TaskDetailState,
    @Assisted private val args: TaskDetailFragmentArgs,
    private val getTaskFlowable: GetTaskObservable,
    private val updateComplete: UpdateComplete,
    private val deleteTask: DeleteTask
) : MvRxViewModel<TaskDetailState>(initialState = initialState) {

    @AssistedInject.Factory
    interface Factory :
        ViewModelArgumentFactory<TaskDetailViewModel, TaskDetailState, TaskDetailFragmentArgs>

    companion object : MvRxViewModelFactory<TaskDetailViewModel, TaskDetailState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: TaskDetailState
        ): TaskDetailViewModel? {
            val fragment = viewModelContext.getFragment<TaskDetailFragment>()
            val args: TaskDetailFragmentArgs = TaskDetailFragmentArgs.fromBundle(
                viewModelContext.arguments
            )
            return fragment.viewModelFactory.create(state, args)
        }

        override fun initialState(viewModelContext: ViewModelContext): TaskDetailState? {
            return TaskDetailState(
                originalTask = null,
                isLoading = null,
                taskNotFound = null
            )
        }
    }

    init {
        setState {
            copy(isLoading = true)
        }
        getTaskFlowable(TaskId(args.taskId)).execute { asyncResult ->
            when (val result = asyncResult()) {
                is GetTaskObservable.Result.Found -> copy(
                    isLoading = false,
                    originalTask = result.task,
                    taskNotFound = false
                )
                is GetTaskObservable.Result.TaskNotFound -> copy(
                    isLoading = false,
                    originalTask = null,
                    taskNotFound = true
                )
                else -> this
            }
        }
    }

    val toolbarData = ToolbarData(
        navigationIcon = R.drawable.toolbar_navigation_icon,
        navigationClickHandler = this::onNavigationClick,
        menu = R.menu.task_detail_menu,
        menuItemClickHandler = this::onToolbarMenuClick
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

    private val _addNotificationEvent = MutableLiveData<Event<String>>()
    val addNotificationEvent: LiveData<Event<String>>
        get() = _addNotificationEvent

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
        withState { state ->
            if (state.originalTask?.isCompleted != checked) {
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
    }
}