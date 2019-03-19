package com.sample.todo.ui

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.sample.todo.base.extension.getFragment
import com.sample.todo.domain.usecase.GetNameObservable
import com.sample.todo.domain.usecase.SetName
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class SettingsViewModel @AssistedInject constructor(
    private val getNameObservable: GetNameObservable,
    @Assisted private val initState: SettingsState,
    private val setName: SetName
) : com.sample.todo.base.MvRxViewModel<SettingsState>(initState) {
    companion object : MvRxViewModelFactory<SettingsViewModel, SettingsState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: SettingsState
        ): SettingsViewModel? {
            return viewModelContext.getFragment<SettingsFragment>().viewModelFactory.create(state)
        }

        override fun initialState(viewModelContext: ViewModelContext): SettingsState? {
            return SettingsState(
                name = Uninitialized
            )
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initState: SettingsState): SettingsViewModel
    }

    private var textTemp: String? = null

    init {
        getNameObservable().execute {
            copy(name = it)
        }
    }

    fun onNameChange(name: CharSequence?) {
        textTemp = name?.toString()
    }

    fun onSetNameClick() {
        viewModelScope.launch {
            setName(textTemp ?: TODO())
        }
    }
}