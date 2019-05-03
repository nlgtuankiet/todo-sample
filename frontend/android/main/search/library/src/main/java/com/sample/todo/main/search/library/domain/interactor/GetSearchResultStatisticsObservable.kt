package com.sample.todo.main.search.library.domain.interactor

import com.sample.todo.main.search.library.domain.entity.SearchResultStatistics
import com.sample.todo.main.search.library.domain.repository.SearchRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetSearchResultStatisticsObservable @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(rawQuery: String): Observable<SearchResultStatistics> {
        val query = rawQuery
            .splitToSequence(" ")
            .filter { it.isNotBlank() }
            .map { "$it*" }
            .joinToString(" ", "\"", "\"")
            .toLowerCase()
        return searchRepository.getSearchResultStatisticsObservable(query)
    }
}
