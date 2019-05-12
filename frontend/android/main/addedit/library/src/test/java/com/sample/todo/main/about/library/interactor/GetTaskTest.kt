package com.sample.todo.main.about.library.interactor

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.sample.todo.domain.TestData
import com.sample.todo.domain.entity.TaskId
import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.main.about.library.exception.TaskNotFoundException
import com.sample.todo.main.about.library.repository.AddEditRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetTaskTest {
    @Test
    fun `GetTask success`() = runBlocking {
        val task = TestData.tasks.first()
        val taskId = TaskId(task.id)
        val taskRepository: AddEditRepository = mock {
            onBlocking { getTask(taskId.value) } doReturn task
        }
        val getTask = GetTask(taskRepository)

        val result = runCatching { getTask(taskId) }
        assertEquals(task, result.getOrNull())
    }

    @Test
    fun `GetTask task not found`() = runBlocking {
        val task = TestData.tasks.first()
        val taskId = TaskId(task.id)
        val taskRepository: AddEditRepository = mock {
            onBlocking { getTask(taskId.value) } doReturn null
        }
        val getTask = GetTask(taskRepository)

        val result = runCatching { getTask(taskId) }
        assertTrue(result.exceptionOrNull() is TaskNotFoundException)
    }

    @Test
    fun `GetTask invalid TaskId`() = runBlocking {
        val taskId = TestData.invalidTaskId
        val taskRepository: AddEditRepository = mock()
        val getTask = GetTask(taskRepository)

        val result = runCatching { getTask(taskId) }

        verifyNoMoreInteractions(taskRepository)
        assertTrue(result.exceptionOrNull() is InvalidTaskIdException)
    }
}
