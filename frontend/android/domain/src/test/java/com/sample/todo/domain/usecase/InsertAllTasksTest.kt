package com.sample.todo.domain.usecase

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.sample.todo.domain.TestData
import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.repository.TaskRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import java.lang.RuntimeException

class InsertAllTasksTest {
    private val taskRepository: TaskRepository = mock()
    private lateinit var useCase: InsertAllTasks

    @Before
    fun `set up`() {
        useCase = InsertAllTasks(taskRepository)
    }

    @After
    fun `tear down`() {
        reset(taskRepository)
    }

    @Test
    fun `fail because invalid task id`() = runBlocking {
        val tasks: List<Task> = listOf(Task(id = "123", title = ""))

        val result = runCatching { useCase.invoke(tasks) }

        assertTrue(result.exceptionOrNull() is InvalidTaskIdException)
        verifyNoMoreInteractions(taskRepository)
    }

    @Test
    fun `fail because TaskRepository throw exception`() = runBlocking {
        val tasks: List<Task> = TestData.tasks
        `when`(taskRepository.insertAll(tasks)) doThrow RuntimeException()
        val result = runCatching { useCase.invoke(tasks) }

        assertTrue(result.exceptionOrNull() is RuntimeException)
    }

    @Test
    fun `success insert empty list`() = runBlocking {
        val tasks: List<Task> = emptyList()

        val result = runCatching { useCase.invoke(tasks) }

        assertEquals(true, result.getOrNull())
        verifyNoMoreInteractions(taskRepository)
    }

    @Test
    fun `success`() = runBlocking {
        val tasks: List<Task> = TestData.tasks
        `when`(taskRepository.insertAll(tasks)) doReturn tasks.size.toLong()
        val result = runCatching { useCase.invoke(tasks) }

        assertEquals(true, result.getOrNull())
    }
}
