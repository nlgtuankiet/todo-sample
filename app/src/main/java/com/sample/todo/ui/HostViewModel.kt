package com.sample.todo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sample.todo.core.BaseViewModel
import com.sample.todo.util.extension.setValueIfNew
import timber.log.Timber
import javax.inject.Inject

class HostViewModel @Inject constructor() : BaseViewModel() {

    fun onTopNavigationSelected(navControllerId: Int) {
        Timber.d("onTopNavigationSelected(navControllerId=$navControllerId)")
        _currentNavControllerId.setValueIfNew(navControllerId)
    }

    private val _currentNavControllerId = MutableLiveData<Int>()
    val currentNavControllerId: LiveData<Int>
        get() = _currentNavControllerId
}