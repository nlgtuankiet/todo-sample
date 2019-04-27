package com.sample.todo.dynamic.data.task.firestore.function

import com.sample.todo.data.core.DataScope
import com.sample.todo.data.core.Mapper
import com.sample.todo.domain.model.SearchResult
import com.sample.todo.dynamic.data.task.firestore.function.entity.SearchResponce
import javax.inject.Inject

@DataScope
class SearchResultMapper @Inject constructor() : Mapper<SearchResponce, List<SearchResult>> {
    override fun invoke(from: SearchResponce): List<SearchResult> {
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
