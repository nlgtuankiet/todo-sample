package com.sample.todo.domain.usecase

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.sample.todo.domain.TestData
import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.repository.TaskRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import java.lang.RuntimeException

class InsertNewTaskTest {
    private val taskRepository: TaskRepository = mock()
    private lateinit var useCase: InsertNewTask

    @Before
    fun `set up`() {
        useCase = InsertNewTask(taskRepository)
    }

    @After
    fun `tear down`() {
        reset(taskRepository)
    }

    @Test
    fun `fail because invalid task id`() = runBlocking {
        val task = Task(id = "123", title = "")

        val result = runCatching { useCase.invoke(task) }

        Assert.assertTrue(result.exceptionOrNull() is InvalidTaskIdException)
        verifyNoMoreInteractions(taskRepository)
    }

    @Test
    fun `fail because TaskRepository throw exception`() = runBlocking {
        val task: Task = TestData.tasks.first()
        `when`(taskRepository.insert(task)) doThrow RuntimeException()

        val result = runCatching { useCase.invoke(task) }

        assertTrue(result.exceptionOrNull() is RuntimeException)
    }

    @Test
    fun `success`(): Unit = runBlocking {
        val task: Task = TestData.tasks.first()
        `when`(taskRepository.insert(task)) doReturn 1L

        val result = runCatching { useCase.invoke(task) }

        assertEquals(true, result.getOrNull())
        verify(taskRepository).insert(task)
        Unit
    }
}
