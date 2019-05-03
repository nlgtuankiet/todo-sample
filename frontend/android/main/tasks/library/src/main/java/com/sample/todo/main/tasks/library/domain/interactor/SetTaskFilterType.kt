package com.sample.todo.main.tasks.library.domain.interactor

import com.sample.todo.main.tasks.library.domain.entity.TaskFilterType
import com.sample.todo.main.tasks.library.domain.repository.TasksRepository
import javax.inject.Inject

// TODO suspect race condition, try to do in transaction?
class SetTaskFilterType @Inject constructor(
    private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(newFilter: TaskFilterType) {
        val oldOrdinal = tasksRepository.getTaskFilterTypeOrdinal()
        val oldFilter: TaskFilterType? = TaskFilterType.parse(oldOrdinal)
        if (oldFilter == newFilter)
            return
        tasksRepository.setTaskFilterTypeOrdinal(newFilter.ordinal)
    }
}
