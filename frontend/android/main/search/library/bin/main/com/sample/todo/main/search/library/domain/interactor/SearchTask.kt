package com.sample.todo.main.search.library.domain.interactor

import androidx.paging.PagedList
import com.sample.todo.main.search.library.domain.entity.SearchResult
import com.sample.todo.main.search.library.domain.repository.SearchRepository
import io.reactivex.Observable
import javax.inject.Inject

class SearchTask @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(rawQuery: String, pageSize: Int = 20): Observable<PagedList<SearchResult>> {
        val query = rawQuery
            .splitToSequence(" ")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .joinToString(" ")
            .toLowerCase()
        return searchRepository.getSearchResultObservablePaged(query, pageSize)
    }
}
