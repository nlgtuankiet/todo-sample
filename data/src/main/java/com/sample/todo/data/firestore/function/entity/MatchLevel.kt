package com.sample.todo.data.firestore.function.entity

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
enum class MatchLevel {
    @Json(name = "none")
    NONE,
    @Json(name = "partial")
    PARTIAL,
    @Json(name = "full")
    FULL
}