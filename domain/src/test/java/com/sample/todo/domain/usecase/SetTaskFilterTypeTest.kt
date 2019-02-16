package com.sample.todo.domain.usecase

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.sample.todo.domain.model.TaskFilterType
import com.sample.todo.domain.repository.PreferenceRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

class SetTaskFilterTypeTest {
    private val preferenceRepository: PreferenceRepository = mock()
    private lateinit var useCase: SetTaskFilterType

    @Before
    fun `set up`() {
        useCase = SetTaskFilterType(preferenceRepository)
    }

    @After
    fun `tear down`() {
        reset(preferenceRepository)
    }

    @Test
    fun `success when there is no value in preference`() = runBlocking {
        `when`(preferenceRepository.getTaskFilterTypeOrdinal()) doReturn -1

        val result = useCase.invoke(TaskFilterType.ACTIVE)

        assertTrue(result.isSuccess)
        inOrder(preferenceRepository) {
            verify(preferenceRepository).getTaskFilterTypeOrdinal()
            verify(preferenceRepository).setTaskFilterTypeOrdinal(TaskFilterType.ACTIVE.ordinal)
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `success when set same value`() = runBlocking {
        `when`(preferenceRepository.getTaskFilterTypeOrdinal()) doReturn TaskFilterType.ACTIVE.ordinal

        val result = useCase.invoke(TaskFilterType.ACTIVE)

        assertTrue(result.isSuccess)
        inOrder(preferenceRepository) {
            verify(preferenceRepository).getTaskFilterTypeOrdinal()
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `success when set different value`() = runBlocking {
        `when`(preferenceRepository.getTaskFilterTypeOrdinal()) doReturn TaskFilterType.ACTIVE.ordinal

        val result = useCase.invoke(TaskFilterType.COMPLETED)

        assertTrue(result.isSuccess)
        inOrder(preferenceRepository) {
            verify(preferenceRepository).getTaskFilterTypeOrdinal()
            verify(preferenceRepository).setTaskFilterTypeOrdinal(TaskFilterType.COMPLETED.ordinal)
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `fail when repository throw exception when get filter`() = runBlocking {
        `when`(preferenceRepository.getTaskFilterTypeOrdinal()) doThrow RuntimeException()

        val result = useCase.invoke(TaskFilterType.COMPLETED)

        assertTrue(result.exceptionOrNull() is RuntimeException)
    }

    @Test
    fun `fail when repository throw exception when set filter`() = runBlocking {
        val oldFilter = TaskFilterType.ALL
        val newFilter = TaskFilterType.COMPLETED
        `when`(preferenceRepository.getTaskFilterTypeOrdinal()) doReturn oldFilter.ordinal
        `when`(preferenceRepository.setTaskFilterTypeOrdinal(newFilter.ordinal)) doThrow RuntimeException()

        val result = useCase.invoke(newFilter)

        assertTrue(result.exceptionOrNull() is RuntimeException)
    }
}