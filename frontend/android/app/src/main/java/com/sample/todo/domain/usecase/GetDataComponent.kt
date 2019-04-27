package com.sample.todo.domain.usecase

import android.content.Context
import com.sample.todo.di.application.ApplicationScope
import com.sample.todo.data.DataComponent
import com.sample.todo.domain.entity.DataImplementation
import com.sample.todo.domain.repository.DataPreferenceRepository
import javax.inject.Inject
import kotlin.reflect.full.companionObjectInstance

@ApplicationScope
class GetDataComponent @Inject constructor(
    private val dataPreferenceRepository: DataPreferenceRepository,
    private val context: Context
) {
    operator fun invoke(): DataComponent {
        val ordinal = dataPreferenceRepository.getDataImplementationOrdinal()
        val dataImplementation = DataImplementation.fromOdinal(ordinal) ?: DataImplementation.ROOM
        return dataImplementation.componentClassName.let { className ->
            val companion = Class.forName(className).kotlin.companionObjectInstance as DataComponent.Companion
            companion.invoke(context)
        }
    }
}
