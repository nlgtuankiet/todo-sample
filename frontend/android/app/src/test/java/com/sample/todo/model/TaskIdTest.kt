package com.sample.todo.model

import com.sample.todo.domain.model.TaskId
import org.junit.Assert
import org.junit.Test

class TaskIdTest {
    @Test
    fun `validate() return false when length less than 20`() {
        val lessThan20 = "AAA"
        val taskId = TaskId(lessThan20)
        val result = TaskId.validate(taskId)
        Assert.assertEquals(false, result)
    }

    @Test
    fun `validate() return false when length greater than 20`() {
        var moreThan20 = ""
        repeat(21) { moreThan20 += "A" }

        val taskId = TaskId(moreThan20)
        val result = TaskId.validate(taskId)
        Assert.assertEquals(false, result)
    }

    @Test
    fun `validate() return false when length is 20 but contain invalid char`() {
        var input = ""
        repeat(19) { input += "A" }
        input += "?" // invalid char

        val taskId = TaskId(input)
        val result = TaskId.validate(taskId)
        Assert.assertEquals(false, result)
    }

    @Test
    fun `validate() return true when all condition are meet`() {
        var input = ""
        repeat(20) { input += "A" }

        val taskId = TaskId(input)
        val result = TaskId.validate(taskId)
        Assert.assertEquals(true, result)
    }

    @Test
    fun `newInstance() return a valid TaskId`() {
        repeat(1_000_000) {
            val instance = TaskId.newInstance()
            val result = TaskId.validate(instance)
            Assert.assertEquals(true, result)
        }
    }
}
