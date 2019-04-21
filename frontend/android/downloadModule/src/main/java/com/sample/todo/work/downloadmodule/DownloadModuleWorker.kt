package com.sample.todo.work.downloadmodule

import android.content.Context
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.sample.todo.base.entity.DownloadModuleSessionId
import com.sample.todo.base.entity.DynamicFeatureModule
import com.sample.todo.base.notification.AppNotification
import com.sample.todo.base.notification.NotificationChannelInformation
import com.sample.todo.base.usecase.IsModuleInstalled
import com.sample.todo.base.usecase.IsModuleInstalling
import com.sample.todo.base.usecase.StartInstallModule
import com.sample.todo.domain.util.checkAllMatched
import com.sample.todo.work.BaseWorker
import com.sample.todo.work.ListenableWorkerFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DownloadModuleWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val notificationManager: NotificationManagerCompat,
    private val splitInstallManager: SplitInstallManager,
    private val isModuleInstalled: IsModuleInstalled,
    private val isModuleInstalling: IsModuleInstalling,
    private val startInstallModule: StartInstallModule
) : BaseWorker(appContext, workerParams) {
    @AssistedInject.Factory
    interface Factory : ListenableWorkerFactory {
        override fun create(appContext: Context, workerParams: WorkerParameters): ListenableWorker
    }

    internal data class Parameter(
        val module: DynamicFeatureModule
    ) {
        fun asData(): Data {
            return Data.Builder()
                .putString(KEY_MODULE_NAME, module.name)
                .build()
        }

        companion object {
            const val KEY_MODULE_NAME = "KEY_MODULE_NAME"
            fun fromData(data: Data): Parameter {
                val moduleName = data.getString(KEY_MODULE_NAME)
                    ?: throw IllegalArgumentException("$KEY_MODULE_NAME is not present in data")
                val module = DynamicFeatureModule.fromString(moduleName)
                    ?: throw IllegalArgumentException("""Module name: "$moduleName" is not valid""")
                return Parameter(module)
            }
        }
    }

    companion object {
        private const val WORKER_NAME_PREFIX = "DOWNLOAD_MODULE_"

        fun enqueNewWorker(module: DynamicFeatureModule) {
            val param = DownloadModuleWorker.Parameter(module)
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val request = OneTimeWorkRequestBuilder<DownloadModuleWorker>()
                .setInputData(param.asData())
                .setConstraints(constraints)
                .build()
            WorkManager.getInstance()
                .enqueueUniqueWork(
                    WORKER_NAME_PREFIX + module.codeName.toUpperCase(),
                    ExistingWorkPolicy.REPLACE,
                    request
                )
        }
    }

    private lateinit var stateListener: SplitInstallStateUpdatedListener

    private val notificationId = AppNotification.DownloadModule.ordinal
    private val channelId = NotificationChannelInformation.DownloadModule.ordinal.toString()
    private val parameters: Parameter by lazy { Parameter.fromData(workerParams.inputData) }
    private val module: DynamicFeatureModule by lazy { parameters.module }
    private val moduleDisplayName: String
        get() = appContext.getString(module.displayName)
    private val notificationTitle: String
        get() = appContext.getString(R.string.download_module_notification_title, moduleDisplayName)

    override suspend fun doSuspendWork() {
        displayOnGoingNotification(R.string.download_module_notification_sending_request)

        if (isModuleInstalled(module)) {
            displayFinishNotification {
                appContext.getString(
                    R.string.download_module_notification_module_is_already_installed,
                    moduleDisplayName
                )
            }
        }
        if (isModuleInstalling(module)) {
            displayFinishNotification {
                appContext.getString(
                    R.string.download_module_notification_module_is_already_downloading,
                    moduleDisplayName
                )
            }
        }

        runCatching {
            startInstallModule(module)
        }.onSuccess { sessionId ->
            suspendCoroutine<Unit> { continuation ->
                initListener(sessionId, continuation)
            }
        }.onFailure { cause ->
            displayFinishNotification {
                cause.message ?: appContext.getString(R.string.download_module_notification_unknown_error)
            }
        }
        clearResource()
    }

    private fun initListener(sessionId: DownloadModuleSessionId, continuation: Continuation<Unit>) {
        stateListener = SplitInstallStateUpdatedListener { state ->
            if (state.sessionId() != sessionId.value)
                throw IllegalArgumentException("Session id is not match: expected: $sessionId, actual: ${state.sessionId()}")
            when (state.status()) {
                SplitInstallSessionStatus.UNKNOWN -> {
                    displayFinishNotification(R.string.download_module_notification_unknown_error)
                    continuation.resume(Unit)
                }
                SplitInstallSessionStatus.PENDING -> {
                    displayOnGoingNotification(R.string.download_module_notification_pending)
                }
                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                    val intentSender = state.resolutionIntent()?.intentSender
                        ?: throw RuntimeException("Cannot resolve intent from state")
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
                else -> throw IllegalArgumentException("Invalid SplitInstallSessionStatus: ${state.status()}")
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
            .setSmallIcon(android.R.drawable.stat_sys_download_done)
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
