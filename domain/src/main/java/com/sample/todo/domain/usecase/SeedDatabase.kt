package com.sample.todo.domain.usecase

import com.sample.todo.domain.model.Task
import com.sample.todo.domain.repository.PreferenceRepository
import com.thedeanda.lorem.Lorem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Random
import javax.inject.Inject

class SeedDatabase @Inject constructor(
    private val insertAllTasks: InsertAllTasks,
    private val lorem: Lorem,
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke(): SeedDatabaseResult = withContext(Dispatchers.IO) {
        var totalTaskSeeded = preferenceRepository.getTotalTaskSeeded()
        val random = Random()
        while (totalTaskSeeded < TOTAL_TASKS) {
            val numberOfTask = Math.min(ITEMS_PER_TRUNK, TOTAL_TASKS - totalTaskSeeded)
            val tasks = mutableListOf<Task>()
            repeat(numberOfTask) {
                tasks.add(
                    Task(
                        title = lorem.getTitle(1, 10),
                        description = lorem.getParagraphs(1, 3),
                        isCompleted = random.nextBoolean()
                    )
                )
            }
            val insertResult = insertAllTasks(tasks)
            if (insertResult.isFailure) {
                return@withContext SeedDatabaseResult.Retry
            }
            val updatedValue = preferenceRepository.increaseTotalTaskSeeded(insertResult.getOrNull()?.toInt()!!)
            totalTaskSeeded = updatedValue
        }
        return@withContext SeedDatabaseResult.Success
    }

    companion object {
        /**
         * Task(
         *     title = lorem.getTitle(1, 10),
         *     description = lorem.getParagraphs(1, 3),
         *     isCompleted = random.nextBoolean()
         * )
         * 75 mb per 100k entry (take 36s to insert)
         */
        const val TOTAL_TASKS = 10_000
        const val ITEMS_PER_TRUNK = 1_000
    }
}

sealed class SeedDatabaseResult {
    object Success : SeedDatabaseResult()
    object Retry : SeedDatabaseResult()
}