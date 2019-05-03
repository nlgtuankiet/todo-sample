package com.sample.todo.dynamic.seeddatabase.domain.interactor

import com.sample.todo.domain.entity.Task
import com.sample.todo.dynamic.seeddatabase.library.di.SeedDatabaseWorkerScope
import com.sample.todo.dynamic.seeddatabase.lorem.Lorem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Random
import javax.inject.Inject

@SeedDatabaseWorkerScope
class SeedDatabase @Inject constructor(
    private val insertAllTasks: InsertAllTasks,
    private val lorem: Lorem
) {
    suspend operator fun invoke(): SeedDatabaseResult = withContext(Dispatchers.IO) {
        var totalTaskSeeded = 0
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
            val insertResult = kotlin.runCatching { insertAllTasks(tasks) }
            if (insertResult.isFailure) {
                return@withContext SeedDatabaseResult.Retry
            }
            val updatedValue = tasks.size
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
