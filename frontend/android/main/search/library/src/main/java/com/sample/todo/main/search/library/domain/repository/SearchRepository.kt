package com.sample.todo.main.search.library.domain.repository

import androidx.paging.PagedList
import com.sample.todo.main.search.library.domain.entity.SearchResult
import com.sample.todo.main.search.library.domain.entity.SearchResultStatistics
import io.reactivex.Observable

interface SearchRepository {
    fun getSearchResultStatisticsObservable(query: String): Observable<SearchResultStatistics>
    fun getSearchResultObservablePaged(query: String, pageSize: Int): Observable<PagedList<SearchResult>>
}