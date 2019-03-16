package com.sample.todo.data.preference

import com.sample.todo.data.core.DataScope
import com.sample.todo.domain.repository.PreferenceRepository
import io.reactivex.Flowable
import javax.inject.Inject

@DataScope
class PreferenceRepositoryImpl @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : PreferenceRepository {
    override suspend fun getTotalTaskSeeded(): Int {
        return preferenceStorage.getTotalTaskSeeded()
    }

    override suspend fun increaseTotalTaskSeeded(amount: Int): Int {
        return preferenceStorage.increaseTotalTaskSeeded(amount)
    }

    override suspend fun getTaskFilterTypeOrdinal(): Int {
        return preferenceStorage.getTaskFilterTypeOrdinal()
    }

    override suspend fun setTaskFilterTypeOrdinal(value: Int) {
        return preferenceStorage.setTaskFilterTypeOrdinal(value)
    }

    override fun getTaskFilterTypeOrdinalFlowable(): Flowable<Int> {
        return preferenceStorage.getTaskFilterTypeOrdinalFlowable()
    }
}