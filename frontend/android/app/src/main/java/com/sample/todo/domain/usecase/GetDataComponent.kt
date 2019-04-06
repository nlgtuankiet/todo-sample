package com.sample.todo.domain.usecase

import android.content.Context
import com.sample.todo.base.di.AppScope
import com.sample.todo.base.di.ApplicationScope
import com.sample.todo.data.DataComponent
import com.sample.todo.data.task.firestore.FirestoreDataComponent
import com.sample.todo.data.task.room.RoomDataComponent
import com.sample.todo.data.task.sqldelight.SqlDelightDataComponent
import com.sample.todo.domain.repository.PreferenceRepository
import com.sample.todo.domain.entity.DataImplementation
import com.sample.todo.domain.repository.DataPreferenceRepository
import javax.inject.Inject

@ApplicationScope
class GetDataComponent @Inject constructor(
    private val dataPreferenceRepository: DataPreferenceRepository
) {
    operator fun invoke(context: Context): DataComponent {
        val ordinal = dataPreferenceRepository.getDataImplementationOrdinal()
        val implementation = DataImplementation.parse(ordinal)
        return when(implementation) {
            DataImplementation.FIRESTORE -> FirestoreDataComponent(context)
            DataImplementation.SQLDELIGHT -> SqlDelightDataComponent(context)
            else -> RoomDataComponent(context)
        }
    }
}