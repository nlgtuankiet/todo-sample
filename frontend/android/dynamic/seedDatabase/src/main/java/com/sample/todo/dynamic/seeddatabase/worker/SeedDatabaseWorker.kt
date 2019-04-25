package com.sample.todo.dynamic.seeddatabase.worker

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.WorkerParameters
import com.sample.todo.base.notification.AppNotification
import com.sample.todo.base.notification.NotificationChannelInformation
import com.sample.todo.domain.model.Task
import com.sample.todo.dynamic.seeddatabase.R
import com.sample.todo.dynamic.seeddatabase.lorem.Lorem
import com.sample.todo.dynamic.seeddatabase.service.SeedDatabaseControllerService
import com.sample.todo.dynamic.seeddatabase.usecase.InsertAllTasks
import com.sample.todo.work.BaseWorker
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlin.random.Random

// TODO implement DB
// TODO implement: Seed XX tasks in XX seconds
class SeedDatabaseWorker constructor(
    private val appContext: Context,
    private val workerParams: WorkerParameters
) : BaseWorker(appContext, workerParams) {

    @Inject
    lateinit var notificationManager: NotificationManagerCompat
    @Inject
    lateinit var lorem: Lorem
    @Inject
    lateinit var insertAllTasks: InsertAllTasks

    init {
        SeedDatabaseWorkerComponent(this)
    }

    private val channelId = NotificationChannelInformation.SeedDatabase.ordinal.toString()
    private val notificationId = AppNotification.SeedDatabase.ordinal
    private val params: Parameter by lazy {
        Parameter.fromData(
            workerParams.inputData
        )
    }
    private val baseNotificationBuilder: NotificationCompat.Builder
        get() = NotificationCompat.Builder(appContext, channelId)
            .setContentTitle(appContext.getString(R.string.seed_database_module_title))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setAutoCancel(false)
    private var _totalTasksSeeded = 0L

    override suspend fun doSuspendWork() {
        // TODO validate param
        val totalTasks = params.totalTasks
        val itemPerTrunk = params.itemPerTrunk
        (3 downTo 1).forEach {
            showStartNotification(it)
            delay(1000)
        }
        while (true) {
            val totalTaskSeeded = getTotalTaskSeeded()
            showUpdateNotification(totalTaskSeeded)
            val nextTrunk = Math.min(itemPerTrunk, params.totalTasks - totalTaskSeeded)
            if (nextTrunk < 0L) TODO()
            if (nextTrunk == 0L) break
            val tasks = createRandomTasks(nextTrunk)
            val result = kotlin.runCatching {
                insertAllTasks(tasks)
            }
            result.onFailure {
                TODO()
            }
                .onSuccess { onTasksSeeded(nextTrunk) }

            delay(1000)
        }
        showSeedDoneNotification()
    }

    private val cancelAction: NotificationCompat.Action by lazy {
        NotificationCompat.Action.Builder(
            R.drawable.ic_format_list_numbered_black_24dp,
            appContext.getString(R.string.seed_database_notification_cancel_action_title),
            SeedDatabaseControllerService.requestStopIntent(appContext)
        )
            .setShowsUserInterface(false)
            .build()
    }

    /**
     * TODO implement using database
     */
    private fun getTotalTaskSeeded(): Long {
        return _totalTasksSeeded
    }

    /**
     * TODO implement using database
     */
    private fun onTasksSeeded(numberOfTasks: Long) {
        _totalTasksSeeded += numberOfTasks
    }

    private suspend fun createRandomTasks(numberOfTasks: Long): List<Task> {
        val tasks = mutableListOf<Task>()
        (0 until numberOfTasks).forEach {
            tasks.add(createRandomTask())
        }
        return tasks
    }

    private fun createRandomTask(): Task {
        return Task(
            title = lorem.getTitle(params.minTitleLength, params.maxTitleLength),
            description = lorem.getParagraphs(params.minDescriptionParagraph, params.maxDescriptionParagraph),
            isCompleted = Random.nextBoolean()
        )
    }

    private fun showStartNotification(secondsRemain: Int) {
        showNotification(
            onGoing = true,
            contentText = appContext.getString(
                R.string.seed_database_notification_prepare,
                params.totalTasks,
                secondsRemain
            )

        )
    }

    private fun showSeedDoneNotification() {
        showNotification(
            onGoing = false,
            contentText = appContext.getString(
                R.string.seed_database_notification_seed_done,
                params.totalTasks
            )

        )
    }

    private fun showUpdateNotification(seededTasks: Long) {
        val percent = (seededTasks * 100.0 / params.totalTasks).roundToInt()
        showNotification(
            onGoing = true,
            actions = listOf(cancelAction),
            contentText = appContext.getString(
                R.string.seed_database_notification_seed_process,
                percent
            ),
            builderOptions = {
                setProgress(100, percent, false)
            }
        )
    }

    private fun showStoppedNotification() {
        showNotification(
            onGoing = false,
            contentText = appContext.getString(R.string.seed_database_notification_stopped)
        )
    }

    private inline fun showNotification(
        onGoing: Boolean = false,
        actions: List<NotificationCompat.Action> = emptyList(),
        builderOptions: (NotificationCompat.Builder.() -> Unit) = {},
        contentText: String? = null
    ) {
        val builder = baseNotificationBuilder.apply {
            setOngoing(onGoing)
            setAutoCancel(!onGoing)
            contentText?.let { setContentText(it) }
            actions.forEach { addAction(it) }
            builderOptions(this)
        }
        notificationManager.notify(notificationId, builder.build())
    }

    // invoke only when interrupted
    override fun onStopped() {
        super.onStopped()
        showStoppedNotification()
    }

    companion object {
        const val WORK_MANE = "seed_db"
    }
}
