package com.sample.todo.data.task.room.mapper

import com.sample.todo.data.Mapper
import com.sample.todo.data.task.room.entity.SearchResultEntity
import com.sample.todo.domain.model.SearchResult
import javax.inject.Inject

class SearchResultEntityMapper @Inject constructor() :
    Mapper<SearchResultEntity, SearchResult> {
    override fun map(from: SearchResultEntity): SearchResult {
        return SearchResult(
            id = from.id,
            snippets = from.snippets,
            title = from.title
        )
    }
}