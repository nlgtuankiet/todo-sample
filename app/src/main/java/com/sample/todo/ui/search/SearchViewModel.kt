package com.sample.todo.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.toLiveData
import androidx.navigation.NavDirections
import com.sample.todo.core.BaseViewModel
import com.sample.todo.core.Event
import com.sample.todo.domain.usecase.GetTasksCountFlowable
import com.sample.todo.domain.usecase.SearchTask
import com.sample.todo.util.extension.debounce
import com.sample.todo.util.extension.setValueIfNew
import timber.log.Timber
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchTask: SearchTask,
    private val tasksCountLive: GetTasksCountFlowable
) : BaseViewModel() {
    val query = MutableLiveData<String>()

    private val latestQuery = query.debounce().distinctUntilChanged()

    val queryChangeEvent = latestQuery.map {
        Event(Unit)
    }

    val source = latestQuery.switchMap {
        searchTask(it).toLiveData()
    }

    private val totalTaskCount = tasksCountLive().toLiveData()

    val resultStatistics = MediatorLiveData<SearchResultStatistics?>().apply {
        addSource(latestQuery) {
            Timber.d("_resultStatistics(latestQuery=$it)")
            if (it.isNullOrEmpty()) {
                value = null
            } else {
                value = SearchResultStatistics(
                    totalResultCount = source.value?.size ?: 0,
                    totalTaskCount = totalTaskCount.value ?: 0
                )
            }
        }
        addSource(source) {
            Timber.d("_resultStatistics(source(size=${it.size}, loadedCount=${it.loadedCount}))")
            value = value?.copy(totalResultCount = it.size)
        }
        addSource(totalTaskCount) {
            Timber.d("_resultStatistics(totalTaskCount=$it)")
            value = value?.copy(totalTaskCount = it)
        }
    }.distinctUntilChanged()

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
        if (text == null) return false
        query.setValueIfNew(text)
        return true
    }
}

data class SearchResultStatistics(
    val totalResultCount: Int,
    val totalTaskCount: Long
)
