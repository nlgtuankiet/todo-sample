package com.sample.todo.domain.usecase

import com.sample.todo.domain.repository.SettingsPreferenceRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetNameObservable @Inject constructor(
    private val preferenceRepository: SettingsPreferenceRepository
) {
    operator fun invoke(): Observable<String> {
        return preferenceRepository.getNameObservable().distinct()
    }
}