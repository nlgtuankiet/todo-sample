package com.sample.todo.ui.addedit

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sample.todo.R
import com.sample.todo.core.BaseViewModel
import com.sample.todo.core.Event
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskId
import com.sample.todo.domain.usecase.GetTask
import com.sample.todo.domain.usecase.InsertNewTask
import com.sample.todo.domain.usecase.UpdateTask
import com.sample.todo.ui.message.Message
import com.sample.todo.util.ToolbarData
import com.sample.todo.util.autoId
import com.sample.todo.util.extension.postValueIfNew
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class AddEditViewModel @Inject constructor(
    private val addEditFragmentArgs: AddEditFragmentArgs,
    private val getTask: GetTask,
    private val insertNewTask: InsertNewTask,
    private val updateTask: UpdateTask
) : BaseViewModel() {

    private val taskId: String? = addEditFragmentArgs.taskId

    @StringRes
    val toolbarTitle =
        if (taskId == null) R.string.add_edit_add_title else R.string.add_edit_edit_title

    val toolbarListenerData = ToolbarData(
        navigationIcon = R.drawable.toolbar_navigation_icon,
        navigationClickHandler = this::onNavigationClick
    )

    private val _navigateUpEvent = MutableLiveData<Event<Unit>>()
    val navigateUpEvent: LiveData<Event<Unit>>
        get() = _navigateUpEvent

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String?>()
    private var isCompleted = false

    private val _snackBarMessage = MutableLiveData<Event<Message>>()
    val snackBarMessage: LiveData<Event<Message>>
        get() = _snackBarMessage

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        loadTask()
    }

    private fun loadTask() {
        if (taskId != null) {
            launch(Dispatchers.IO) {
                _isLoading.postValueIfNew(true)
                getTask(TaskId(taskId))
                    .onSuccess { task ->
                        title.postValueIfNew(task.title)
                        description.postValueIfNew(task.description)
                        isCompleted = task.isCompleted
                    }
                    .onFailure {
                        Timber.e("cannot get task with id: $taskId, ex=$it")
                    }
                _isLoading.postValueIfNew(false)
            }
        }
    }

    fun onSaveButtonClick() {
        val title = title.value
            ?: throw IllegalArgumentException("when this could happen?")
        val taskEntity = Task(
            id = taskId ?: autoId(),
            title = title,
            description = description.value,
            isCompleted = isCompleted
        )
        launch(Dispatchers.IO) {
            if (taskId == null) {
                insertNewTask(taskEntity)
            } else {
                updateTask(taskEntity)
            }
        }
    }

    private fun onNavigationClick() {
        _navigateUpEvent.value = Event(Unit)
    }
}