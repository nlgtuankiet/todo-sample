package com.sample.todo.domain.usecase

import com.sample.todo.domain.repository.SettingsPreferenceRepository
import javax.inject.Inject

class SetName @Inject constructor(
    private val preferenceRepository: SettingsPreferenceRepository
) {
    suspend operator fun invoke(name: String): Result<Unit> {
        return kotlin.runCatching {
            val realName = name.trim()
            if (realName.isEmpty()) throw IllegalArgumentException("Name can not empty")
            preferenceRepository.setName(realName)
        }
    }
}