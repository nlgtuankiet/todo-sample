package com.sample.todo.domain.usecase

import com.sample.todo.domain.model.SearchResultStatistics
import com.sample.todo.domain.repository.TaskRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetSearchResultStatisticsObservable @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(rawQuery: String): Observable<SearchResultStatistics> {
        val query = rawQuery
            .splitToSequence(" ")
            .filter { it.isNotBlank() }
            .map { "$it*" }
            .joinToString(" ", "\"", "\"")
            .toLowerCase()
        return taskRepository.getSearchResultStatisticsObservable(query)
    }
}