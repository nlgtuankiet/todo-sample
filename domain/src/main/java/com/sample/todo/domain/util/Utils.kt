package com.sample.todo.domain.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend inline fun <T> runCatchingIO(
    crossinline block: suspend () -> T
): Result<T> {
    return runCatching {
        withContext(Dispatchers.IO) {
            block()
        }
    }
}