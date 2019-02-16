package com.sample.todo.domain.usecase

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.sample.todo.domain.repository.TaskRepository
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

class GetTasksCountFlowableTest {
    private val taskRepository: TaskRepository = mock()
    private lateinit var useCase: GetTasksCountFlowable

    @Before
    fun `set up`() {
        useCase = GetTasksCountFlowable(taskRepository)
    }

    @After
    fun `tear down`() {
        reset(taskRepository)
    }

    @Test
    fun `success as usual`() {
        val mockFlow = Observable.just(1L, 2L, 3L)
        `when`(taskRepository.getTaskCount()) doReturn mockFlow

        val result = useCase.invoke()

        result.test().assertValues(1, 2, 3)
        verify(taskRepository).getTaskCount()
        verifyNoMoreInteractions(taskRepository)
    }

    @Test
    fun `success distinct until changed`() {
        val mockFlow = Observable.just(1L, 2L, 2L, 1L)
        `when`(taskRepository.getTaskCount()) doReturn mockFlow

        val result = useCase.invoke()

        result.test().assertValues(1, 2, 1)
        verify(taskRepository).getTaskCount()
        verifyNoMoreInteractions(taskRepository)
    }
}