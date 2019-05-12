package com.sample.todo.main.taskdetail.library.domain.interactor

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.sample.todo.domain.TestData
import com.sample.todo.domain.entity.Task
import com.sample.todo.domain.entity.TaskId
import com.sample.todo.domain.exception.InvalidTaskIdException
import com.sample.todo.main.taskdetail.library.domain.repository.TaskDetailRepository
import io.reactivex.Observable
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

class GetTaskObservableTest {

    private val taskRepository: TaskDetailRepository = mock()
    private lateinit var useCase: GetTaskObservable

    @Before
    fun `set up`() {
        useCase = GetTaskObservable(taskRepository)
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
        val mockResult: Observable<List<Task>> = Observable.just(emptyList())
        `when`(taskRepository.getTaskWithIdObservable(taskId.value)) doReturn mockResult

        val actual = useCase.invoke(taskId)
        actual.test().assertValue(GetTaskObservable.Result.TaskNotFound)
    }

    @Test
    fun `success update value`() {
        val task: Task = TestData.tasks.first()
        val task2: Task = task.copy(title = "new title")
        val taskId = TaskId(task.id)
        val mockResult: Observable<List<Task>> = Observable.just(
            listOf(task),
            listOf(task2)
        )
        `when`(taskRepository.getTaskWithIdObservable(taskId.value)) doReturn mockResult

        val useCaseResult = useCase.invoke(taskId)

        assertEquals(
            listOf(
                GetTaskObservable.Result.Found(task),
                GetTaskObservable.Result.Found(task2)
            ),
            useCaseResult.test().values()
        )
    }

    @Test
    fun `success update value distinct until changed`() {
        val task: Task = TestData.tasks.first()
        val task2: Task = task.copy(title = "new title")
        val taskId = TaskId(task.id)
        val mockResult: Observable<List<Task>> = Observable.just(
            listOf(task),
            listOf(task2),
            listOf(task2),
            listOf(task)
        )

        `when`(taskRepository.getTaskWithIdObservable(taskId.value)) doReturn mockResult

        val useCaseResult = useCase.invoke(taskId)
        assertEquals(
            listOf(
                GetTaskObservable.Result.Found(task),
                GetTaskObservable.Result.Found(task2),
                GetTaskObservable.Result.Found(task)
            ),
            useCaseResult.test().values()
        )
    }
}
