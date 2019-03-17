package com.sample.todo.data.room.mapper

import com.sample.todo.data.Mapper
import com.sample.todo.data.room.entity.SearchResultStatisticsEntity
import com.sample.todo.domain.model.SearchResultStatistics
import javax.inject.Inject

class SearchResultStatisticsEntityMapper @Inject constructor() : Mapper<SearchResultStatisticsEntity, SearchResultStatistics> {
    override fun map(from: SearchResultStatisticsEntity): SearchResultStatistics {
        return SearchResultStatistics(
            totalResultCount = from.totalResultCount.toLong(),
            totalTaskCount = from.totalTaskCount.toLong()
        )
    }
}