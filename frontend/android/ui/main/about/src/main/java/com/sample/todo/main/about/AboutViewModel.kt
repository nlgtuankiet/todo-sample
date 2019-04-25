package com.sample.todo.main.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.sample.todo.base.entity.Event
import com.sample.todo.base.extension.setNewEvent
import com.sample.todo.base.usecase.IsModuleInstalled
import com.sample.todo.base.usecase.IsModuleInstalling
import javax.inject.Inject

class AboutViewModel @Inject constructor(
    private val isModuleInstalled: IsModuleInstalled,
    private val isModuleInstalling: IsModuleInstalling
) : ViewModel() {
    private val _navigationEvent = MutableLiveData<Event<NavDirections>>()
    val navigationEvent: LiveData<Event<NavDirections>>
        get() = _navigationEvent

    private val _navigateToSettingsEvent = MutableLiveData<Event<Unit>>()
    val navigateToSettingsEvent: LiveData<Event<Unit>>
        get() = _navigateToSettingsEvent

    private val _displayModuleDetailDialogEvent = MutableLiveData<Event<Unit>>()
    val displayModuleDetailDialogEvent: LiveData<Event<Unit>>
        get() = _displayModuleDetailDialogEvent

    fun onStatisticsLabelClick() {
        _navigationEvent.value = Event(AboutFragmentDirections.toStatisticsFragment())
    }

    fun onSettingsLabelClick() {
        _navigateToSettingsEvent.setNewEvent()
    }
}
