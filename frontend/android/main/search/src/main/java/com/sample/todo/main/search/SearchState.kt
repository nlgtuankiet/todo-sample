package com.sample.todo.main.search

import androidx.paging.PagedList
import com.airbnb.mvrx.MvRxState
import com.sample.todo.main.search.library.domain.entity.SearchResult
import com.sample.todo.main.search.library.domain.entity.SearchResultStatistics

data class SearchState(
    val searchResult: PagedList<SearchResult>?,
    val searchResultStatistics: SearchResultStatistics?
) : MvRxState
