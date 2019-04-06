package com.sample.todo.main.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.todo.base.entity.DynamicFeatureModule
import com.sample.todo.base.entity.Event
import com.sample.todo.base.extension.setNewEvent
import com.sample.todo.base.usecase.IsModuleInstalled
import com.sample.todo.base.usecase.IsModuleInstalling
import javax.inject.Inject

class AboutViewModel @Inject constructor(
    private val isModuleInstalled: IsModuleInstalled,
    private val isModuleInstalling: IsModuleInstalling
) : ViewModel() {



    private val _navigateToStatisticsEvent = MutableLiveData<Event<Unit>>()
    val navigateToStatisticsEvent: LiveData<Event<Unit>>
        get() = _navigateToStatisticsEvent

    private val _navigateToSettingsEvent = MutableLiveData<Event<Unit>>()
    val navigateToSettingsEvent: LiveData<Event<Unit>>
        get() = _navigateToSettingsEvent

    private val _displayModuleDetailDialogEvent = MutableLiveData<Event<Unit>>()
    val displayModuleDetailDialogEvent: LiveData<Event<Unit>>
        get() = _displayModuleDetailDialogEvent

    fun onStatisticsLabelClick() {
        _navigateToStatisticsEvent.value = Event(Unit)
    }

    fun onSettingsLabelClick() {
            _navigateToSettingsEvent.setNewEvent()

    }
}