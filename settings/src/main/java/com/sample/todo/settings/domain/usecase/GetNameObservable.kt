package com.sample.todo.settings.domain.usecase

import com.sample.todo.settings.domain.repository.PreferenceRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetNameObservable @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke(): Observable<String> {
        return preferenceRepository.getNameObservable().distinct()
    }
}