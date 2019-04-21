package com.sample.todo.domain.usecase

import com.sample.todo.base.di.ApplicationScope
import com.sample.todo.domain.entity.DataImplementation
import com.sample.todo.domain.repository.DataPreferenceRepository
import javax.inject.Inject

@ApplicationScope
class SetDataImplementation @Inject constructor(
    private val dataPreferenceRepository: DataPreferenceRepository
) {
    operator fun invoke(dataImplementation: DataImplementation) {
        dataPreferenceRepository.setDataImplementationOrdinal(dataImplementation.ordinal)
    }
}
