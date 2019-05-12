package com.sample.todo.main.tasks.library.domain.interactor

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.sample.todo.main.tasks.library.domain.entity.TaskFilterType
import com.sample.todo.main.tasks.library.domain.repository.TasksRepository
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Test

class GetTaskFilterTypeObservableTest {

    @Test
    fun `data flow as usual`() {
        val expected = TaskFilterType.values().toList()
        val filterTypeOrdinals = Observable.fromIterable(expected.map { it.ordinal })
        val preferenceRepository: TasksRepository = mock {
            on { getTaskFilterTypeOrdinalObservable() } doReturn filterTypeOrdinals
        }
        val result = GetTaskFilterTypeObservable(preferenceRepository).invoke()

        val actual = result.test().values()
        assertEquals(expected, actual)
    }

    @Test
    fun `data flow correct when ordinal is invalid`() {
        val preferenceRepository: TasksRepository = mock {
            on { getTaskFilterTypeOrdinalObservable() } doReturn Observable.just(-2)
        }
        val result = GetTaskFilterTypeObservable(preferenceRepository).invoke()

        result.test()
            .assertValue(TaskFilterType.ALL)
    }

    @Test
    fun `data flow dictint until changed`() {
        val ordinals = listOf(0, 1, 1, 2)
        val preferenceRepository: TasksRepository = mock {
            on { getTaskFilterTypeOrdinalObservable() } doReturn Observable.fromIterable(ordinals)
        }
        val result = GetTaskFilterTypeObservable(preferenceRepository).invoke()

        val expected = listOf(
            TaskFilterType.ALL,
            TaskFilterType.COMPLETED,
            TaskFilterType.ACTIVE

        )
        val actual = result.test().values()
        assertEquals(expected, actual)
    }
}
