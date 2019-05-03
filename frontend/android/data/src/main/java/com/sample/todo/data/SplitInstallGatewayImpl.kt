package com.sample.todo.data

import android.content.Context
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.sample.todo.data.core.DataScope
import com.sample.todo.downloadmodule.domain.entity.DownloadModuleSessionId
import com.sample.todo.downloadmodule.domain.entity.DynamicFeatureModule
import com.sample.todo.downloadmodule.domain.gateway.SplitInstallGateway
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@DataScope
class SplitInstallGatewayImpl @Inject constructor(
    private val context: Context
) : SplitInstallGateway {
    private val splitInstallManager by lazy {
        SplitInstallManagerFactory.create(context)
    }

    override fun isModuleInstalled(dynamicFeatureModule: DynamicFeatureModule): Boolean {
        return splitInstallManager.installedModules.any {
            dynamicFeatureModule.codeName == it
        }
    }

    override suspend fun isModuleInstalling(dynamicFeatureModule: DynamicFeatureModule): Boolean {
        return suspendCoroutine { continuation ->
            splitInstallManager.sessionStates
                .addOnSuccessListener { stateList ->
                    continuation.resume(
                        stateList.any { state ->
                            state.moduleNames().contains(dynamicFeatureModule.codeName)
                        }
                    )
                }
                .addOnFailureListener { continuation.resumeWithException(it) }
        }
    }

    override suspend fun startInstallModule(dynamicFeatureModule: DynamicFeatureModule): DownloadModuleSessionId {
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