package com.sample.todo.dynamic.seeddatabase.service

import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import androidx.work.WorkManager
import com.sample.todo.dynamic.seeddatabase.worker.SeedDatabaseWorker
import timber.log.Timber
import javax.inject.Inject

class SeedDatabaseControllerService : IntentService("SeedDatabaseControllerService") {
    @Inject
    lateinit var workManager: WorkManager
    @Inject
    lateinit var notificationManager: NotificationManagerCompat

    override fun onCreate() {
        SeedDatabaseControllerComponent(this)
        super.onCreate()
    }

    override fun onHandleIntent(intent: Intent?) {
        Timber.d("onHandleIntent(123=${intent?.getStringExtra("123")})")
        val command = intent?.getStringExtra("command") ?: throw RuntimeException("???")
        when (command) {
            "stop" -> {
                stopSeedDatabaseWorker()
            }
            else -> throw RuntimeException("???")
        }
    }

    fun stopSeedDatabaseWorker() {
        val result = workManager.cancelUniqueWork(SeedDatabaseWorker.WORK_MANE)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
    }

    companion object {
        fun requestStopIntent(context: Context): PendingIntent {
            val intent: Intent = Intent(context, SeedDatabaseControllerService::class.java).apply {
                putExtra("command", "stop")
            }
            return PendingIntent.getService(context, 99, intent, PendingIntent.FLAG_ONE_SHOT)
        }
    }
}