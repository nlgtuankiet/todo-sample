package com.sample.todo.data.task.room.mapper

import com.sample.todo.data.core.Mapper
import com.sample.todo.data.task.room.entity.SearchResultStatisticsEntity
import com.sample.todo.domain.model.SearchResultStatistics
import javax.inject.Inject

class SearchResultStatisticsEntityToSearchResultStatistics @Inject constructor(
) : Mapper<SearchResultStatisticsEntity, SearchResultStatistics> {
    override fun invoke(from: SearchResultStatisticsEntity): SearchResultStatistics {
        return SearchResultStatistics(
            totalResultCount = from.totalResultCount.toLong(),
            totalTaskCount = from.totalTaskCount.toLong()
        )
    }
}