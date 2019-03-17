package com.sample.todo.ui.statistics

import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.sample.todo.core.MvRxViewModel
import com.sample.todo.core.ViewModelFactory
import com.sample.todo.domain.usecase.GetTaskStatObservable
import com.sample.todo.util.extension.getFragment
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class StatisticsViewModel @AssistedInject constructor(
    @Assisted private val initialState: StatisticsState,
    getTaskStatLive: GetTaskStatObservable
) : MvRxViewModel<StatisticsState>(initialState) {

    @AssistedInject.Factory
    interface Factory : ViewModelFactory<StatisticsViewModel, StatisticsState>

    companion object : MvRxViewModelFactory<StatisticsViewModel, StatisticsState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: StatisticsState
        ): StatisticsViewModel? {
            return viewModelContext.getFragment<StatisticsFragment>().viewModelFactory.create(state)
        }

        override fun initialState(viewModelContext: ViewModelContext): StatisticsState? {
            return StatisticsState(
                tasksCount = "",
                completedTasksCount = "",
                activeTasksCount = ""
            )
        }
    }

    init {
        getTaskStatLive().execute {
            it()?.let {
                copy(
                    tasksCount = it.taskCount.toString(),
                    completedTasksCount = it.completedTaskCount.toString(),
                    activeTasksCount = it.activeTaskCount.toString()
                )
            } ?: this
        }
    }
}