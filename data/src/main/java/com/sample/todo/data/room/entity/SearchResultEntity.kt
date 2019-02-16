package com.sample.todo.data.room.entity

import androidx.room.ColumnInfo

data class SearchResultEntity(
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "snippet")
    val snippets: String,
    @ColumnInfo(name = "title")
    val title: String
)