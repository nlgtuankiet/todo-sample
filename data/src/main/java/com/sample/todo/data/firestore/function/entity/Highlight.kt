package com.sample.todo.data.firestore.function.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Highlight(
    @Json(name = "value")
    val value: String,
    @Json(name = "matchLevel")
    val matchLevel: MatchLevel,
    @Json(name = "fullyHighlighted")
    val fullyHighlighted: Boolean?,
    @Json(name = "matchedWords")
    val matchedWords: List<String>
)