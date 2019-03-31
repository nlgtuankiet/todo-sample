package com.sample.todo.main.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AboutViewModel @Inject constructor() : ViewModel() {

    private val _navigateToStatisticsEvent = MutableLiveData<com.sample.todo.base.Event<Unit>>()
    val navigateToStatisticsEvent: LiveData<com.sample.todo.base.Event<Unit>>
        get() = _navigateToStatisticsEvent

    private val _navigateToSettingsEvent = MutableLiveData<com.sample.todo.base.Event<Unit>>()
    val navigateToSettingsEvent: LiveData<com.sample.todo.base.Event<Unit>>
        get() = _navigateToSettingsEvent

    fun onStatisticsLabelClick() {
        _navigateToStatisticsEvent.value = com.sample.todo.base.Event(Unit)
    }

    fun onSettingsLabelClick() {
        _navigateToSettingsEvent.value = com.sample.todo.base.Event(Unit)
    }
}