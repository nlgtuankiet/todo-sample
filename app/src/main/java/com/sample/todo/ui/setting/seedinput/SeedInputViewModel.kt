package com.sample.todo.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sample.todo.core.BaseViewModel
import com.sample.todo.core.Event
import com.sample.todo.worker.seeddatabase.Parameter
import javax.inject.Inject

class SeedInputViewModel @Inject constructor() : BaseViewModel() {

    private val _requestSeedDatabaseEvent: MutableLiveData<Event<Parameter>> = MutableLiveData()
    val requestSeedDatabaseEvent: LiveData<Event<Parameter>>
        get() = _requestSeedDatabaseEvent

    fun onSeedDatabaseClick() {
        _requestSeedDatabaseEvent.value = Event(
            Parameter(
                isBrandNew = true,
                totalTasks = 10000,
                maxTitleLength = 5,
                maxDescriptionParagraph = 4
            )
        )
    }
}