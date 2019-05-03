package com.sample.todo.main.addedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.todo.base.entity.Event
import com.sample.todo.base.extension.postNewEvent
import com.sample.todo.base.extension.postNewMessage
import com.sample.todo.base.extension.postValueIfNew
import com.sample.todo.base.extension.setNewEvent
import com.sample.todo.base.listener.ListenerKt.listenerOf
import com.sample.todo.base.listener.MenuListenerKt.menuListenerOf
import com.sample.todo.base.message.Message
import com.sample.todo.base.widget.ToolbarData
import com.sample.todo.domain.entity.Task
import com.sample.todo.domain.entity.TaskId
import com.sample.todo.domain.util.autoId
import com.sample.todo.main.about.library.interactor.GetTask
import com.sample.todo.main.about.library.interactor.InsertNewTask
import com.sample.todo.main.about.library.interactor.UpdateTask
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

// need a way to disable the save button
class AddEditViewModel @Inject constructor(
    private val args: AddEditFragmentArgs,
    private val getTask: GetTask,
    private val insertNewTask: InsertNewTask,
    private val updateTask: UpdateTask
) : ViewModel() {

    private val taskId: String? = args.taskId

    val toolbarTitle = if (args.taskId == null) R.string.add_edit_add_title else R.string.add_edit_edit_title

    private val onNavigationClick = listenerOf {
        _navigateUpEvent.setNewEvent()
    }

    private val onMenuClick = menuListenerOf { menuId ->
        when (menuId) {
            R.id.save -> onSaveButtonClick()
            else -> TODO()
        }
        true
    }

    val toolbarListenerData = ToolbarData(
        navigationIcon = R.drawable.toolbar_navigation_icon,
        navigationClickHandler = onNavigationClick,
        menu = R.menu.add_edit_menu,
        menuItemClickHandler = onMenuClick
    )

    private val _navigateUpEvent = MutableLiveData<Event<Unit>>()
    val navigateUpEvent: LiveData<Event<Unit>>
        get() = _navigateUpEvent

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean>
        get() = _loading

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String?>()
    private var isCompleted = false

    private val _snackBarMessage = MutableLiveData<Event<Message>>()
    val snackBarMessage: LiveData<Event<Message>>
        get() = _snackBarMessage

    init {
        loadTask()
    }

    private fun loadTask() {
        if (taskId != null) {
            viewModelScope.launch {
                _loading.postValueIfNew(true)
                runCatching { getTask(TaskId(taskId)) }
                    .onSuccess { task ->
                        title.postValueIfNew(task.title)
                        description.postValueIfNew(task.description)
                        isCompleted = task.isCompleted
                    }
                    .onFailure {
                        Timber.e("cannot get task with id: $taskId, ex=$it")
                    }
                _loading.postValueIfNew(false)
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
}
