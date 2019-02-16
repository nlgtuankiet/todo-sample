package com.sample.todo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sample.todo.R
import com.sample.todo.core.BaseViewModel
import com.sample.todo.util.extension.setValueIfNew
import javax.inject.Inject

class HostViewModel @Inject constructor() : BaseViewModel() {

    fun onTopNavigationSelected(navControllerId: Int) {
        _currentNavControllerId.setValueIfNew(navControllerId)
    }

    private val _currentNavControllerId = MutableLiveData<Int>().apply {
        value = R.id.tasks_nav_controller
    }
    val currentNavControllerId: LiveData<Int>
        get() = _currentNavControllerId
}