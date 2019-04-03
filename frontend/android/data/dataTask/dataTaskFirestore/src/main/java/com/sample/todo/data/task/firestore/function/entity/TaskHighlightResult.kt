package com.sample.todo.data.task.firestore.function.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TaskHighlightResult(
    @Json(name = "title")
    val title: Highlight,
    @Json(name = "description")
    val description: Highlight?
)