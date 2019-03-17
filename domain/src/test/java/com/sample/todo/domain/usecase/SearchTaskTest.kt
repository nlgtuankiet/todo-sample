package com.sample.todo.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.verify
import com.sample.todo.domain.repository.TaskRepository
import org.junit.After
import org.junit.Before
import org.junit.Test

class SearchTaskTest {
    private val taskRepository: TaskRepository = mock()
    private lateinit var useCase: SearchTask

    @Before
    fun `set up`() {
        useCase = SearchTask(taskRepository)
    }

    @After
    fun `tear down`() {
        reset(taskRepository)
    }

    @Test
    fun `split query with white space`() {
        val query = "1 2  3 4   5"

        useCase.invoke(query, 1)

        val expected = """
            "1* 2* 3* 4* 5*"
        """.trimIndent()
        verify(taskRepository).getSearchResultObservablePaged(expected, 1)
    }

    @Test
    fun `query lowercase`() {
        val query = "aAa"

        useCase.invoke(query, 1)

        val expected = """
            "aaa*"
        """.trimIndent()
        verify(taskRepository).getSearchResultObservablePaged(expected, 1)
    }
}