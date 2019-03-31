package com.sample.todo.work.downloadmodule

import android.content.Context
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.sample.todo.base.notification.AppNotification
import com.sample.todo.base.notification.NotificationChannelInformation
import com.sample.todo.core.checkAllMatched
import com.sample.todo.work.BaseWorker
import com.sample.todo.work.ListenableWorkerFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import timber.log.Timber
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DownloadModuleWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val notificationManager: NotificationManagerCompat,
    private val splitInstallManager: SplitInstallManager
) : BaseWorker(appContext, workerParams) {
    @AssistedInject.Factory
    interface Factory : ListenableWorkerFactory {
        override fun create(appContext: Context, workerParams: WorkerParameters): ListenableWorker
    }

    private lateinit var stateListener: SplitInstallStateUpdatedListener

    private val notificationId = AppNotification.DownloadModule.ordinal
    private val channelId = NotificationChannelInformation.DownloadModule.ordinal.toString()
    private lateinit var parameters: Parameter
    private val moduleNames: String by lazy { parameters.modules.joinToString(", ") { it.name } }
    private val notificationTitle: String
        get() = appContext.getString(R.string.download_module_notification_title, moduleNames)

    override suspend fun doSuspendWork() {
        parameters = Parameter.fromData(workerParams.inputData)

        // TODO validate parameter if invalid stuff
        val requestInstall = SplitInstallRequest
            .newBuilder()
            .apply {
                parameters.modules.forEach { module ->
                    addModule(module.name)
                }
            }
            .build()
        displayOnGoingNotification(R.string.download_module_notification_sending_request)
        runCatching {
            suspendCoroutine<Int> { continuation ->
                splitInstallManager.startInstall(requestInstall)
                    .addOnSuccessListener {
                        Timber.d("sessionId: $it")
                        continuation.resume(it)
                    }
                    .addOnFailureListener {
                        Timber.d("error obtain sessionId: ${it.message}")
                        continuation.resumeWithException(it)
                    }
            }
        }.onSuccess { sessionId ->
            suspendCoroutine<Unit> { continuation ->
                initListener(sessionId, continuation)
            }
        }.onFailure { cause ->
            displayFinishNotification {
                cause.message ?: TODO()
            }
        }
        clearResource()
    }

    private fun initListener(sessionId: Int, continuation: Continuation<Unit>) {
        stateListener = SplitInstallStateUpdatedListener { state ->
            if (state.sessionId() != sessionId) TODO()
            val multiInstall = state.moduleNames().size > 1
            when (state.status()) {
                SplitInstallSessionStatus.UNKNOWN -> {
                    displayFinishNotification(R.string.download_module_notification_unknown_error)
                    continuation.resume(Unit)
                }
                SplitInstallSessionStatus.PENDING -> {
                    displayOnGoingNotification(R.string.download_module_notification_pending)
                }
                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                    val intentSender = state.resolutionIntent()?.intentSender ?: TODO()
                    appContext.startIntentSender(intentSender, null, 0, 0, 0)
                }
                SplitInstallSessionStatus.DOWNLOADING -> {
                    displayOnGoingNotification(R.string.download_module_notification_downloading)
                }
                SplitInstallSessionStatus.DOWNLOADED -> {
                    displayOnGoingNotification(R.string.download_module_notification_percent_downloaded)
                }
                SplitInstallSessionStatus.INSTALLING -> {
                    displayOnGoingNotification(R.string.download_module_notification_installing)
                }
                SplitInstallSessionStatus.INSTALLED -> {
                    displayFinishNotification(R.string.download_module_notification_installed)
                    continuation.resume(Unit)
                }
                SplitInstallSessionStatus.FAILED -> {
                    displayFinishNotification(R.string.download_module_notification_install_failed)
                    continuation.resume(Unit)
                }
                SplitInstallSessionStatus.CANCELING -> {
                    displayOnGoingNotification(R.string.download_module_notification_canceling)
                }
                SplitInstallSessionStatus.CANCELED -> {
                    displayFinishNotification(R.string.download_module_notification_canceled)
                    continuation.resume(Unit)
                }
                else -> TODO()
            }.checkAllMatched
        }
        splitInstallManager.registerListener(stateListener)
    }

    private val baseNotificationBuilder: NotificationCompat.Builder
        get() = NotificationCompat.Builder(appContext, channelId)
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setContentTitle(notificationTitle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOnlyAlertOnce(true)

    private inline fun displayOnGoingNotification(getContentText: () -> String) {
        val notification = baseNotificationBuilder
            .setContentText(getContentText())
            .setOngoing(true)
            .setAutoCancel(false)
            .build()
        notificationManager.notify(notificationId, notification)
    }

    private inline fun displayFinishNotification(getContentText: () -> String) {
        val notification = baseNotificationBuilder
            .setContentText(getContentText())
            .setOngoing(false)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(notificationId, notification)
    }

    private fun displayOnGoingNotification(@StringRes content: Int) {
        displayOnGoingNotification { appContext.getString(content) }
    }

    private fun displayFinishNotification(@StringRes content: Int) {
        displayFinishNotification { appContext.getString(content) }
    }

    private fun clearResource() {
        if (::stateListener.isInitialized) {
            splitInstallManager.unregisterListener(stateListener)
        }
    }

    override fun onStopped() {
        super.onStopped()
        clearResource()
    }
}