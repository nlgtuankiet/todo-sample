package com.sample.todo.domain.usecase

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.sample.todo.domain.TestData
import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskId
import com.sample.todo.domain.repository.TaskRepository
import io.reactivex.Flowable
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

// TODO bug
// //        flowable.test()
// //            .assertValue { it.exceptionOrNull() is InvalidTaskIdException } // null
// //            .assertComplete()

class GetTaskFlowableTest {

    private val taskRepository: TaskRepository = mock()
    private lateinit var useCase: GetTaskFlowable

    @Before
    fun `set up`() {
        useCase = GetTaskFlowable(taskRepository)
    }

    @After
    fun `tear down`() {
        reset(taskRepository)
    }

    @Test
    fun `fail when invalid task id`() {
        val taskId: TaskId = TestData.invalidTaskId

        val result = kotlin.runCatching { useCase.invoke(taskId) }

        assertTrue(result.exceptionOrNull() is InvalidTaskIdException)
        verifyNoMoreInteractions(taskRepository)
    }

    @Test
    fun `fail because of task not found`() {
        val task: Task = TestData.tasks.first()
        val taskId = TaskId(task.id)
        val mockResult: Flowable<List<Task>> = Flowable.just(emptyList())
        `when`(taskRepository.getTaskWithIdFlowable(taskId.value)) doReturn mockResult

        val actual = useCase.invoke(taskId)
        actual.test().assertValue(GetTaskFlowable.Result.TaskNotFound)
    }

    @Test
    fun `success update value`() {
        val task: Task = TestData.tasks.first()
        val task2: Task = task.copy(title = "new title")
        val taskId = TaskId(task.id)
        val mockResult: Flowable<List<Task>> = Flowable.just(
            listOf(task),
            listOf(task2)
        )
        `when`(taskRepository.getTaskWithIdFlowable(taskId.value)) doReturn mockResult

        val useCaseResult = useCase.invoke(taskId)

        assertEquals(
            listOf(
                GetTaskFlowable.Result.Found(task),
                GetTaskFlowable.Result.Found(task2)
            ),
            useCaseResult.test().values()
        )
    }

    @Test
    fun `success update value distinct until changed`() {
        val task: Task = TestData.tasks.first()
        val task2: Task = task.copy(title = "new title")
        val taskId = TaskId(task.id)
        val mockResult: Flowable<List<Task>> = Flowable.just(
            listOf(task),
            listOf(task2),
            listOf(task2),
            listOf(task)
        )

        `when`(taskRepository.getTaskWithIdFlowable(taskId.value)) doReturn mockResult

        val useCaseResult = useCase.invoke(taskId)
        assertEquals(
            listOf(
                GetTaskFlowable.Result.Found(task),
                GetTaskFlowable.Result.Found(task2),
                GetTaskFlowable.Result.Found(task)
            ),
            useCaseResult.test().values()
        )
    }
}