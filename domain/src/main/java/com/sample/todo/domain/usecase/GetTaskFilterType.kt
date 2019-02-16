package com.sample.todo.domain.usecase

import com.sample.todo.domain.model.TaskFilterType
import com.sample.todo.domain.repository.PreferenceRepository
import io.reactivex.Observable
import javax.inject.Inject

// TODO write test for it
class GetTaskFilterType @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke(): Observable<TaskFilterType> {
        return preferenceRepository
            .getTaskFilterTypeOrdinalFlowable()
            .distinctUntilChanged()
            .map {
                TaskFilterType.parse(it) ?: TaskFilterType.ALL
            }
    }
}