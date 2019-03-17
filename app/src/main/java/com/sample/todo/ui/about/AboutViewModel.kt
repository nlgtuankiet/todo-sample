package com.sample.todo.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.todo.core.Event
import javax.inject.Inject

class AboutViewModel @Inject constructor() : ViewModel() {

    private val _navigateToStatisticsEvent = MutableLiveData<Event<Unit>>()
    val navigateToStatisticsEvent: LiveData<Event<Unit>>
        get() = _navigateToStatisticsEvent

    private val _navigateToSettingsEvent = MutableLiveData<Event<Unit>>()
    val navigateToSettingsEvent: LiveData<Event<Unit>>
        get() = _navigateToSettingsEvent

    fun onStatisticsLabelClick() {
        _navigateToStatisticsEvent.value = Event(Unit)
    }

    fun onSettingsLabelClick() {
        _navigateToSettingsEvent.value = Event(Unit)
    }
}