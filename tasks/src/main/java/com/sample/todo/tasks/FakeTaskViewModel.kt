package com.sample.todo.tasks

import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.sample.todo.base.MvRxViewModel

data class FakeState(val value: Int) : MvRxState

class FakeTaskViewModel : MvRxViewModel<FakeState>(FakeState(1)) {
    companion object : MvRxViewModelFactory<FakeTaskViewModel, FakeState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: FakeState
        ): FakeTaskViewModel? {
            return super.create(viewModelContext, state)
        }

        override fun initialState(viewModelContext: ViewModelContext): FakeState? {
            return super.initialState(viewModelContext)
        }
    }
}