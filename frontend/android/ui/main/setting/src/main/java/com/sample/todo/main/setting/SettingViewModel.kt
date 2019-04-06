package com.sample.todo.main.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.todo.base.entity.Event
import timber.log.Timber
import javax.inject.Inject

class SettingViewModel @Inject constructor() : ViewModel() {

    private val _showSeedInputDialogEvent: MutableLiveData<Event<Unit>> = MutableLiveData()
    val showSeedInputDialogEvent: LiveData<Event<Unit>>
        get() = _showSeedInputDialogEvent

    fun onSeedDatabaseClick() {
        Timber.d("onSeedDatabaseClick")
        _showSeedInputDialogEvent.value = Event(Unit)
    }
}