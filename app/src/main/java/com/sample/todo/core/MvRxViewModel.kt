package com.sample.todo.core

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.sample.todo.BuildConfig

abstract class MvRxViewModel<S : MvRxState>(initialState: S) :
    BaseMvRxViewModel<S>(initialState = initialState, debugMode = BuildConfig.DEBUG)