package com.sample.todo.data.preference

import com.sample.todo.data.core.DataScope
import com.sample.todo.domain.repository.PreferenceRepository
import io.reactivex.Observable
import javax.inject.Inject

@DataScope
class PreferenceRepositoryImpl @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : PreferenceRepository {
    override fun getTotalTaskSeeded(): Int {
        return preferenceStorage.getTotalTaskSeeded()
    }

    override fun increaseTotalTaskSeeded(amount: Int): Int {
        return preferenceStorage.increaseTotalTaskSeeded(amount)
    }

    override fun getTaskFilterTypeOrdinal(): Int {
        return preferenceStorage.getTaskFilterTypeOrdinal()
    }

    override fun setTaskFilterTypeOrdinal(value: Int) {
        return preferenceStorage.setTaskFilterTypeOrdinal(value)
    }

    override fun getTaskFilterTypeOrdinalFlowable(): Observable<Int> {
        return preferenceStorage.getTaskFilterTypeOrdinalLiveData()
    }
}