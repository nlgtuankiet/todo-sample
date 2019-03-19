package com.sample.todo.data.firestore.function

import com.sample.todo.data.Mapper
import com.sample.todo.data.core.DataScope
import com.sample.todo.data.firestore.function.entity.SearchResponce
import com.sample.todo.domain.model.SearchResult
import javax.inject.Inject

@DataScope
class SearchResultMapper @Inject constructor() : Mapper<SearchResponce, List<SearchResult>> {
    override fun map(from: SearchResponce): List<SearchResult> {
        return from.hits.map { taskIndex ->
            val snippets = taskIndex
                .highlightResult
                .let { listOf(it.title, it.description) }
                .sortedByDescending { it?.matchLevel?.ordinal }
                .firstOrNull()
                ?.value ?: TODO()
            SearchResult(
                id = taskIndex.objectID,
                title = taskIndex.title,
                snippets = snippets
            )
        }
    }
}