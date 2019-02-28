package com.sample.todo.settings.domain.usecase

import com.sample.todo.settings.domain.repository.PreferenceRepository
import javax.inject.Inject

class SetName @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke(name: String): Result<Unit> {
        return kotlin.runCatching {
            val realName = name.trim()
            if (realName.isEmpty()) throw IllegalArgumentException("Name can not empty")
            preferenceRepository.setName(realName)
        }
    }
}