package com.sample.todo.ui.bottomnavigation

import androidx.lifecycle.MutableLiveData
import com.sample.todo.core.BaseViewModel
import javax.inject.Inject

class BottomNavigationViewModel @Inject constructor() : BaseViewModel() {
    val text = MutableLiveData<String>().apply {
        value = "hello world"
    }
}