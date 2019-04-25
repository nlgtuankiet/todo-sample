package com.sample.todo.domain.usecase

import androidx.paging.PagedList
import com.sample.todo.domain.model.SearchResult
import com.sample.todo.domain.repository.TaskRepository
import io.reactivex.Observable
import javax.inject.Inject

class SearchTask @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(rawQuery: String, pageSize: Int = 20): Observable<PagedList<SearchResult>> {
        val query = rawQuery
            .splitToSequence(" ")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .joinToString(" ")
            .toLowerCase()
        return taskRepository.getSearchResultObservablePaged(query, pageSize)
    }
}