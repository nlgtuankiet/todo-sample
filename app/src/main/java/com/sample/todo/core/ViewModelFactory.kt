package com.sample.todo.core

import androidx.lifecycle.ViewModel
import com.airbnb.mvrx.MvRxState

interface ViewModelFactory<VM : ViewModel, S : MvRxState> {
    fun create(initialState: S): VM
}