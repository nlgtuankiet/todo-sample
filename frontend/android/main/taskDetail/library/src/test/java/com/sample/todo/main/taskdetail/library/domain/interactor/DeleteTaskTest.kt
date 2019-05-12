package com.sample.todo.main.taskdetail.library.domain.interactor

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.sample.todo.domain.TestData
import com.sample.todo.domain.entity.TaskId
import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.main.taskdetail.library.domain.repository.TaskDetailRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

class DeleteTaskTest {
    private val taskRepository: TaskDetailRepository = mock()
    private lateinit var useCase: DeleteTask

    @Before
    fun `set up`() {
        useCase = DeleteTask(taskRepository)
    }

    @After
    fun `tear down`() {
        reset(taskRepository)
    }

    @Test
    fun `return true when success`() = runBlocking {
        val taskId = TestData.tasks.first().id.run { TaskId(this) }
        `when`(taskRepository.deleteTask(taskId.value)) doReturn 1

        val actual = useCase.invoke(taskId)

        assertEquals(true, actual)
    }

    @Test
    fun `return false when there is no task to delete`() = runBlocking {
        val taskId = TestData.tasks.first().id.run { TaskId(this) }
        `when`(taskRepository.deleteTask(taskId.value)) doReturn 0

        val actual = useCase.invoke(taskId)

        assertEquals(false, actual)
    }

    @Test
    fun `fail when pass invalid TaskId`() = runBlocking {
        val taskId: TaskId = TestData.invalidTaskId

        val result = kotlin.runCatching { useCase.invoke(taskId) }

        assertTrue(result.exceptionOrNull() is InvalidTaskIdException)
        verifyNoMoreInteractions(taskRepository)
    }

    @Test
    fun `fail when repository throw exception`() = runBlocking {
        val taskId: TaskId = TestData.validTaskId
        val exception = RuntimeException("test")
        `when`(taskRepository.deleteTask(taskId.value)) doThrow exception

        val result = kotlin.runCatching { useCase.invoke(taskId) }

        assertEquals(exception, result.exceptionOrNull())
    }
}
