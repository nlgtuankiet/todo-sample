package com.sample.todo.worker.seeddatabase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sample.todo.R
import com.sample.todo.core.AppNotificationChannelIds
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.usecase.InsertAllTasks
import com.sample.todo.service.SeedControllerService
import com.sample.todo.worker.ChildWorkerFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.thedeanda.lorem.Lorem
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import kotlin.random.Random
import androidx.work.ListenableWorker.Result as WorkResult

// TODO implement DB
class SeedDatabase2Worker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val notificationManager: NotificationManagerCompat,
    private val lorem: Lorem,
    private val insertAllTasks: InsertAllTasks
) : CoroutineWorker(appContext, workerParams) {

    private lateinit var params: Parameter

    private val cancelAction: NotificationCompat.Action

    init {
        cancelAction = NotificationCompat.Action.Builder(
            R.drawable.ic_format_list_numbered_black_24dp,
            "Cancel",
            SeedControllerService.requestStopIntent(appContext)
        )
            .setShowsUserInterface(false)
            .build()
    }

    private var _totalTasksSeeded = 0

    /**
     * TODO implement using database
     */
    private fun getTotalTaskSeeded(): Int {
        return _totalTasksSeeded
    }

    /**
     * TODO implement using database
     */
    private fun onTasksSeeded(numberOfTasks: Int) {
        _totalTasksSeeded += numberOfTasks
    }

    override suspend fun doWork(): WorkResult {
        val validateResult = initAndValidateParams()
        if (!validateResult) {
            // TODO show notification
            return WorkResult.failure()
        }
        val totalTasks = params.totalTasks!!
        val itemPerTrunk = ITEMS_PER_TRUNK
        addNotificationChannel()
        (3 downTo 1).forEach {
            showStartNotification(it)
            delay(1000)
        }
        while (true) {
            val totalTaskSeeded = getTotalTaskSeeded()
            showUpdateNotification(totalTaskSeeded, totalTasks)
            val nextTrunk = Math.min(itemPerTrunk, totalTasks - totalTaskSeeded)
            if (nextTrunk == 0) break
            val tasks = createRandomTasks(nextTrunk)
            val result = insertAllTasks(tasks)
            result.onFailure { return WorkResult.failure() }
                .onSuccess { onTasksSeeded(it.toInt()) }

            delay(1000)
        }
        showDoneNotification()
        return WorkResult.success()
    }

    /**
     * return true if validate success
     */
    private fun initAndValidateParams(): Boolean {
        params = runCatching {
            Parameter.fromData(workerParams.inputData).validate()
        }.getOrElse {
            return false
        }
        return true
    }

    private fun createRandomTasks(numberOfTasks: Int): List<Task> {
        val tasks = mutableListOf<Task>()
        repeat(numberOfTasks) {
            tasks.add(createRandomTask())
        }
        return tasks
    }

    private fun createRandomTask(): Task {
        return Task(
            title = lorem.getTitle(1, 10),
            description = lorem.getParagraphs(1, 3),
            isCompleted = Random.nextBoolean()
        )
    }

    private fun showStartNotification(secondsRemain: Int) {
        val notiBuilder = NotificationCompat.Builder(appContext,
            notificationChannelId
        )
            .setSmallIcon(R.drawable.taskdetail_notification)
            .setContentTitle("Seed database")
            .setContentText("Start in $secondsRemain")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOngoing(true)
            .setAutoCancel(false)
            .addAction(cancelAction)
        notificationManager.notify(notificationId, notiBuilder.build())
    }

    private fun showDoneNotification() {
        val notification = NotificationCompat.Builder(appContext,
            notificationChannelId
        )
            .setSmallIcon(R.drawable.taskdetail_notification)
            .setContentTitle("Seed database completed")
            .setContentText("XX tasks in XX seconds")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOngoing(false)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(notificationId, notification)
    }

    private fun showUpdateNotification(seededTasks: Int, totalTasks: Int) {
        val percent = (seededTasks * 100.0 / totalTasks).roundToInt()
        val notification = NotificationCompat.Builder(appContext,
            notificationChannelId
        )
            .setSmallIcon(R.drawable.taskdetail_notification)
            .setContentTitle("Seed database")
            .setContentText("$percent% $seededTasks/$totalTasks")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOngoing(true)
            .setAutoCancel(false)
            .addAction(cancelAction)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    private fun addNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Seed Database Info"
            val descriptionText = "Info about seed database process"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("seed_database_info", name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory

    companion object {
        private const val ITEMS_PER_TRUNK = 1000
        const val notificationId = 1367
        private const val notificationChannelId = AppNotificationChannelIds.SEED_DATABASE_INFO
        const val WORK_MANE = "seed_db"

        fun notifyWorkCanceled(context: Context, notificationManager: NotificationManagerCompat) {
            val notification = NotificationCompat.Builder(context,
                notificationChannelId
            )
                .setSmallIcon(R.drawable.taskdetail_notification)
                .setContentTitle("Seed database")
                .setContentText("Stoped")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(false)
                .setAutoCancel(true)
                .build()
            notificationManager.notify(notificationId, notification)
        }
    }
}