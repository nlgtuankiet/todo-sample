package com.sample.todo.domain.usecase

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.sample.todo.domain.model.TaskFilterType
import com.sample.todo.domain.repository.PreferenceRepository
import io.reactivex.Flowable
import org.junit.Assert.assertEquals
import org.junit.Test

class GetTaskFilterTypeTest {

    @Test
    fun `data flow as usual`() {
        val expected = TaskFilterType.values().toList()
        val filterTypeOrdinals = Flowable.fromIterable(expected.map { it.ordinal })
        val preferenceRepository: PreferenceRepository = mock {
            on { getTaskFilterTypeOrdinalFlowable() } doReturn filterTypeOrdinals
        }
        val result = GetTaskFilterTypeFlowable(preferenceRepository).invoke()

        val actual = result.test().values()
        assertEquals(expected, actual)
    }

    @Test
    fun `data flow correct when ordinal is invalid`() {
        val preferenceRepository: PreferenceRepository = mock {
            on { getTaskFilterTypeOrdinalFlowable() } doReturn Flowable.just(-2)
        }
        val result = GetTaskFilterTypeFlowable(preferenceRepository).invoke()

        result.test()
            .assertValue(TaskFilterType.ALL)
    }

    @Test
    fun `data flow dictint until changed`() {
        val ordinals = listOf(0, 1, 1, 2)
        val preferenceRepository: PreferenceRepository = mock {
            on { getTaskFilterTypeOrdinalFlowable() } doReturn Flowable.fromIterable(ordinals)
        }
        val result = GetTaskFilterTypeFlowable(preferenceRepository).invoke()

        val expected = listOf(
            TaskFilterType.ALL,
            TaskFilterType.COMPLETED,
            TaskFilterType.ACTIVE

        )
        val actual = result.test().values()
        assertEquals(expected, actual)
    }
}