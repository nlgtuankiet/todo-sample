package com.sample.todo.data.task.room.mapper

import com.sample.todo.data.core.Mapper
import com.sample.todo.data.task.room.entity.SearchResultEntity
import com.sample.todo.main.search.library.domain.entity.SearchResult
import javax.inject.Inject

class SearchResultEntityToSearchResult @Inject constructor() : Mapper<SearchResultEntity, SearchResult> {
    override fun invoke(from: SearchResultEntity): SearchResult {
        return SearchResult(
            id = from.id,
            snippets = from.snippets,
            title = from.title
        )
    }
}
