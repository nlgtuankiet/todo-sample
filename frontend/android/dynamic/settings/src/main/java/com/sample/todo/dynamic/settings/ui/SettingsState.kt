package com.sample.todo.dynamic.settings.ui

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState

data class SettingsState(
    val name: Async<String>
) : MvRxState
