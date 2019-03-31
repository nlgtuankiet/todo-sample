package com.sample.todo.main.addedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskId
import com.sample.todo.domain.usecase.GetTask
import com.sample.todo.domain.usecase.InsertNewTask
import com.sample.todo.domain.usecase.UpdateTask
import com.sample.todo.base.message.Message
import com.sample.todo.base.widget.ToolbarData
import com.sample.todo.base.extension.arguments
import com.sample.todo.base.extension.getFragment
import com.sample.todo.base.extension.postNewEvent
import com.sample.todo.base.extension.postNewMessage
import com.sample.todo.base.extension.postValueIfNew
import com.sample.todo.base.extension.setNewEvent
import com.sample.todo.core.autoId
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch
import timber.log.Timber

// need a way to disable the save button
class AddEditViewModel @AssistedInject constructor(
    @Assisted private val initialState: AddEditState,
    @Assisted private val args: AddEditFragmentArgs,
    private val getTask: GetTask,
    private val insertNewTask: InsertNewTask,
    private val updateTask: UpdateTask
) : com.sample.todo.base.MvRxViewModel<AddEditState>(initialState) {

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: AddEditState, args: AddEditFragmentArgs): AddEditViewModel
    }

    companion object : MvRxViewModelFactory<AddEditViewModel, AddEditState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: AddEditState
        ): AddEditViewModel? {
            val fragment = viewModelContext.getFragment<AddEditFragment>()
            val args: AddEditFragmentArgs = AddEditFragmentArgs.fromBundle(
                viewModelContext.arguments
            )
            return fragment.viewModelFactory.create(state, args)
        }

        override fun initialState(viewModelContext: ViewModelContext): AddEditState? {
            val args: AddEditFragmentArgs = AddEditFragmentArgs.fromBundle(
                viewModelContext.arguments
            )
            return AddEditState(
                toolbarTitle = if (args.taskId == null)
                    R.string.add_edit_add_title
                else R.string.add_edit_edit_title,
                isLoading = false
            )
        }
    }

    private val taskId: String? = args.taskId

    val toolbarListenerData = ToolbarData(
        navigationIcon = R.drawable.toolbar_navigation_icon,
        navigationClickHandler = this::onNavigationClick,
        menu = R.menu.add_edit_menu,
        menuItemClickHandler = this::onMenuClick
    )

    private val _navigateUpEvent = MutableLiveData<com.sample.todo.base.Event<Unit>>()
    val navigateUpEvent: LiveData<com.sample.todo.base.Event<Unit>>
        get() = _navigateUpEvent

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String?>()
    private var isCompleted = false

    private val _snackBarMessage = MutableLiveData<com.sample.todo.base.Event<Message>>()
    val snackBarMessage: LiveData<com.sample.todo.base.Event<Message>>
        get() = _snackBarMessage

    init {
        loadTask()
    }

    private fun loadTask() {
        if (taskId != null) {
            viewModelScope.launch {
                setState { copy(isLoading = true) }
                runCatching { getTask(TaskId(taskId)) }
                    .onSuccess { task ->
                        title.postValueIfNew(task.title)
                        description.postValueIfNew(task.description)
                        isCompleted = task.isCompleted
                    }
                    .onFailure {
                        Timber.e("cannot get task with id: $taskId, ex=$it")
                    }
                setState { copy(isLoading = false) }
            }
        }
    }

    private fun onSaveButtonClick() {
        val title = title.value
            ?: throw IllegalArgumentException("when this could happen?")
        val taskEntity = Task(
            id = taskId ?: autoId(),
            title = title,
            description = description.value,
            isCompleted = isCompleted
        )
        viewModelScope.launch {
            if (taskId == null) {
                runCatching {
                    insertNewTask(taskEntity)
                }.onFailure {
                    _snackBarMessage.postNewMessage(messageId = R.string.add_edit_insert_new_task_fail)
                }.onSuccess {
                    _navigateUpEvent.postNewEvent()
                }
            } else {
                runCatching {
                    updateTask(taskEntity)
                }.onFailure {
                    _snackBarMessage.postNewMessage(messageId = R.string.add_edit_update_task_fail)
                }.onSuccess {
                    _snackBarMessage.postNewMessage(messageId = R.string.add_edit_update_task_success)
                }
            }
        }
    }

    private fun onNavigationClick() {
        _navigateUpEvent.setNewEvent()
    }

    private fun onMenuClick(menuId: Int): Boolean {
        when (menuId) {
            R.id.save -> onSaveButtonClick()
            else -> TODO()
        }
        return true
    }
}