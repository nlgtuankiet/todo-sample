package com.sample.todo.setting.seedinput

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.todo.base.Event
import com.sample.todo.work.seeddatabase.Parameter
import javax.inject.Inject

class SeedInputViewModel @Inject constructor() : ViewModel() {

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