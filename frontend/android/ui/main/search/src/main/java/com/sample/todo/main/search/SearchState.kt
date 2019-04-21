package com.sample.todo.main.search

import androidx.paging.PagedList
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.sample.todo.domain.model.SearchResult
import com.sample.todo.domain.model.SearchResultStatistics

data class SearchState(
    val searchResult: Async<PagedList<SearchResult>>,
    val searchResultStatistics: Async<SearchResultStatistics>
) : MvRxState
