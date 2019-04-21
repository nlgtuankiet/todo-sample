package com.sample.todo.dynamic.settings.domain.usecase

import com.sample.todo.dynamic.settings.domain.repository.SettingsPreferenceRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetNameObservable @Inject constructor(
    private val preferenceRepository: SettingsPreferenceRepository
) {
    operator fun invoke(): Observable<String> {
        return preferenceRepository.getNameObservable().distinct()
    }
}
