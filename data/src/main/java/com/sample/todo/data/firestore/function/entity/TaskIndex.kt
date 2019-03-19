package com.sample.todo.data.firestore.function.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TaskIndex(
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String?,
    @Json(name = "objectID")
    val objectID: String,
    @Json(name = "_highlightResult")
    val highlightResult: TaskHighlightResult
)