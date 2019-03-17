package com.sample.todo.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.NavDirections
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.sample.todo.core.Event
import com.sample.todo.core.MvRxViewModel
import com.sample.todo.core.ViewModelFactory
import com.sample.todo.domain.usecase.GetSearchResultStatisticsObservable
import com.sample.todo.domain.usecase.SearchTask
import com.sample.todo.util.extension.debounce
import com.sample.todo.util.extension.getFragment
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import timber.log.Timber
import java.util.concurrent.TimeUnit
import io.reactivex.subjects.BehaviorSubject

// TODO how to dispose observable/emitter
class SearchViewModel @AssistedInject constructor(
    @Assisted private val initialState: SearchState,
    private val searchTask: SearchTask,
    private val getSearchResultStatisticsObservable: GetSearchResultStatisticsObservable
) : MvRxViewModel<SearchState>(initialState) {

    @AssistedInject.Factory
    interface Factory : ViewModelFactory<SearchViewModel, SearchState>

    companion object : MvRxViewModelFactory<SearchViewModel, SearchState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: SearchState
        ): SearchViewModel? {
            return viewModelContext.getFragment<SearchFragment>().viewModelFactory.create(state)
        }

        override fun initialState(viewModelContext: ViewModelContext): SearchState? {
            return SearchState(
                searchResult = Uninitialized,
                searchResultStatistics = Uninitialized
            )
        }
    }

    var query = BehaviorSubject.create<String>()

    init {
        query.debounce(500L, TimeUnit.MILLISECONDS)
            .distinctUntilChanged().apply {
                switchMap { searchTask(it) }
                    .execute { copy(searchResult = it) }
                switchMap { getSearchResultStatisticsObservable(it) }
                    .execute { copy(searchResultStatistics = it) }
            }
    }

    private val _navigationEvent = MutableLiveData<Event<NavDirections>>()
    val navigationEvent: LiveData<Event<NavDirections>>
        get() = _navigationEvent

    fun onResultItemClick(taskId: String) {
        Timber.d("onResultItemClick(taskId=$taskId)")
        _navigationEvent.value =
            Event(SearchFragmentDirections.actionSearchFragmentToTaskDetailFragment(taskId))
    }

    fun onSearchTextChange(text: String?): Boolean {
        Timber.d("onSearchTextChange(text=$text)")
        query.onNext(text ?: "")
        return true
    }
}