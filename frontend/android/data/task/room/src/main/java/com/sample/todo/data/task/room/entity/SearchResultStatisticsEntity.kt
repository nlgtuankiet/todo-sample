package com.sample.todo.data.task.room.entity

import androidx.room.ColumnInfo

data class SearchResultStatisticsEntity(
    @ColumnInfo(name = "total_result_count")
    val totalResultCount: Int,
    @ColumnInfo(name = "total_task_count")
    val totalTaskCount: Int
)