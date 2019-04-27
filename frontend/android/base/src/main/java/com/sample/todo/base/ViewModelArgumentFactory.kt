package com.sample.todo.base

import androidx.lifecycle.ViewModel
import com.airbnb.mvrx.MvRxState

interface ViewModelArgumentFactory<VM : ViewModel, S : MvRxState, A> {
    fun create(initialState: S, args: A): VM
}
