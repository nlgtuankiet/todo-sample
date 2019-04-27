package com.sample.todo.dynamic.dataimplementation.domain.interactor

import com.sample.todo.TodoApplication
import com.sample.todo.base.di.ActivityScoped
import com.sample.todo.domain.entity.DataImplementation
import javax.inject.Inject

@ActivityScoped
class GetCurrentRunTimeDataImplementation @Inject constructor(
    private val todoApplication: TodoApplication
) {
    operator fun invoke(): DataImplementation? {
        val currentComponentClassName = todoApplication.dataComponent::class.java.name
        return DataImplementation.values().find {
            it.componentClassName == currentComponentClassName
        }
    }
}
