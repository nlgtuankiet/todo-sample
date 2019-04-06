package com.sample.todo.base.usecase

import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.sample.todo.base.di.AppScope
import com.sample.todo.base.entity.DownloadModuleSessionId
import com.sample.todo.base.entity.DynamicFeatureModule
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@AppScope
class StartInstallModule @Inject constructor(
    private val splitInstallManager: SplitInstallManager
) {
    suspend operator fun invoke(dynamicFeatureModule: DynamicFeatureModule): DownloadModuleSessionId {
        return suspendCoroutine { continuation ->
            val request = SplitInstallRequest.newBuilder()
                .addModule(dynamicFeatureModule.codeName)
                .build()
            splitInstallManager.startInstall(request)
                .addOnSuccessListener { continuation.resume(DownloadModuleSessionId(it)) }
                .addOnFailureListener { continuation.resumeWithException(it) }
        }
    }
}