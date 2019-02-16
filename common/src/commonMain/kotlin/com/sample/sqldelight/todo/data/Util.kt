package com.sample.sqldelight.todo.data

import com.sample.todo.data.sqldelight.TodoSqlDelightDatabase
import com.squareup.sqldelight.db.SqlDriver

fun createDatabase(driver: SqlDriver): TodoSqlDelightDatabase {
    return TodoSqlDelightDatabase(
        driver = driver)
}