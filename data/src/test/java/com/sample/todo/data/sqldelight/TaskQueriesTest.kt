package com.sample.todo.data.sqldelight

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TaskQueriesTest {
    private val driver = JdbcSqliteDriver()
    private lateinit var database: TodoSqlDelightDatabase
    private lateinit var taskQueries: TaskQueries

    @Before
    fun init() {
        TodoSqlDelightDatabase.Schema.create(driver)
        database = TodoSqlDelightDatabase(driver) // createDatabase(driver)
        taskQueries = database.taskQueries
    }

    @Test
    fun `insert working`() {
        val task: Task = Task.Impl(
            id = "123",
            title = "ti",
            description = null,
            completed = 1
        )
        taskQueries.insert(task = task)
        val actual = taskQueries.selectAll().executeAsOne()
        println(actual)
        Assert.assertEquals(task, actual)
    }

    @Test
    fun `insert 2 working`() {
        val task = Task.Impl(
            id = "123",
            title = "ti",
            description = null,
            completed = 1
        )
        val task2 = task.copy(id = "321", completed = 0)
        taskQueries.insert(task = task)
        taskQueries.insert(task = task2)
        val actual = taskQueries.selectAll().executeAsList()
        Assert.assertEquals(listOf(task, task2), actual)
    }

    @Test
    fun `update same content but changes output is 1 (expected 0)`() {
        val task = Task.Impl(
            id = "123",
            title = "ti",
            description = null,
            completed = 1
        )
        taskQueries.insert(task = task)
        val changes = database.inTransaction {
            taskQueries.updateComplete(task.id, task.completed)
            taskQueries.changes().executeAsOne()
        }
        Assert.assertEquals(1, changes)
    }
}