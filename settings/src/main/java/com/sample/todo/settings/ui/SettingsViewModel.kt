package com.sample.todo.settings.ui

import androidx.lifecycle.MutableLiveData
import com.sample.todo.core.BaseViewModel
import com.sample.todo.settings.domain.usecase.GetNameObservable
import com.sample.todo.settings.domain.usecase.SetName
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val getNameObservable: GetNameObservable,
    private val setName: SetName
) : BaseViewModel() {
    val name: MutableLiveData<String> = MutableLiveData()
    private val getNameSubscription = getNameObservable().subscribe {
        name.value = it
    }

    fun onSetNameClick() {
        println("name is: ${name.value}")
       launch {
           val result = setName(name.value ?: TODO())
           println("result: $result")
       }
    }

    override fun onCleared() {
        super.onCleared()
        getNameSubscription.dispose()
    }
}