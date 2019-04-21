package com.sample.todo.data.task.firestore.function.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponce(
    @Json(name = "hits")
    val hits: List<TaskIndex>,
    @Json(name = "nbHits")
    val nbHits: Long,
    @Json(name = "page")
    val page: Long,
    @Json(name = "nbPages")
    val nbPages: Long,
    @Json(name = "hitsPerPage")
    val hitsPerPage: Long,
    @Json(name = "processingTimeMS")
    val processingTimeMS: Long,
    @Json(name = "exhaustiveNbHits")
    val exhaustiveNbHits: Boolean,
    @Json(name = "query")
    val query: String,
    @Json(name = "params")
    val params: String
)
