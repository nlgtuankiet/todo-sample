package com.sample.todo.base

import androidx.annotation.Keep
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState

@Keep
abstract class MvRxViewModel<S : MvRxState>(initialState: S) :
    BaseMvRxViewModel<S>(initialState = initialState, debugMode = false)
