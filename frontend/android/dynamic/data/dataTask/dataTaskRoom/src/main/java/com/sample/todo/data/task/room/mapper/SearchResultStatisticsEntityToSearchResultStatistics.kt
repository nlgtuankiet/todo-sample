package com.sample.todo.data.task.room.mapper

import com.sample.todo.data.core.Mapper
import com.sample.todo.data.task.room.entity.SearchResultStatisticsEntity
import com.sample.todo.main.search.library.domain.entity.SearchResultStatistics
import javax.inject.Inject

class SearchResultStatisticsEntityToSearchResultStatistics @Inject constructor() : Mapper<SearchResultStatisticsEntity, SearchResultStatistics> {
    override fun invoke(from: SearchResultStatisticsEntity): SearchResultStatistics {
        return SearchResultStatistics(
            totalResultCount = from.totalResultCount.toLong(),
            totalTaskCount = from.totalTaskCount.toLong()
        )
    }
}
