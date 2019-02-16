package com.sample.todo.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sample.todo.core.BaseViewModel
import com.sample.todo.core.Event
import timber.log.Timber
import javax.inject.Inject

class SettingViewModel @Inject constructor() : BaseViewModel() {

    private val _showSeedInputDialogEvent: MutableLiveData<Event<Unit>> = MutableLiveData()
    val showSeedInputDialogEvent: LiveData<Event<Unit>>
        get() = _showSeedInputDialogEvent

    fun onSeedDatabaseClick() {
        Timber.d("onSeedDatabaseClick")
        _showSeedInputDialogEvent.value = Event(Unit)
    }
}