package com.sample.todo.main.search

import androidx.paging.PagedList
import com.airbnb.mvrx.MvRxState
import com.sample.todo.domain.model.SearchResult
import com.sample.todo.domain.model.SearchResultStatistics

data class SearchState(
    val searchResult: PagedList<SearchResult>?,
    val searchResultStatistics: SearchResultStatistics?
) : MvRxState
