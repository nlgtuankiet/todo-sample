package com.sample.todo.domain.usecase

import com.sample.todo.TodoApplication
import com.sample.todo.di.application.ApplicationScope
import com.sample.todo.di.app.AppComponent
import com.sample.todo.domain.di.DomainComponent
import com.sample.todo.domain.entity.DataImplementation
import com.sample.todo.domain.repository.DataPreferenceRepository
import timber.log.Timber
import javax.inject.Inject

@ApplicationScope
class SetDataImplementation @Inject constructor(
    private val dataPreferenceRepository: DataPreferenceRepository,
    private val todoApplication: TodoApplication,
    private val getDataComponent: GetDataComponent
) {
    operator fun invoke(dataImplementation: DataImplementation) = with(todoApplication) {
        // current impl
        Timber.d("current impl: ${todoApplication.dataComponent::class.java.name}")
        dataPreferenceRepository.setDataImplementationOrdinal(dataImplementation.ordinal)
        dataComponent = getDataComponent()
        domainComponent = DomainComponent(
            taskRepository = dataComponent.provideTaskRepository(),
            preferenceRepository = dataComponent.providePreferenceRepository()
        )
        appComponent = AppComponent(
            androidComponent = androidComponent,
            domainComponent = domainComponent,
            dataComponent = dataComponent
        )
        Timber.d("current impl: ${todoApplication.dataComponent::class.java.name}")
    }
}
