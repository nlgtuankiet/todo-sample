package com.sample.todo.downloadmodule.domain.interactor

import com.sample.todo.downloadmodule.domain.entity.DownloadModuleSessionId
import com.sample.todo.downloadmodule.domain.entity.DynamicFeatureModule
import com.sample.todo.downloadmodule.domain.gateway.SplitInstallGateway
import javax.inject.Inject

class StartInstallModule @Inject constructor(
    private val splitInstallGateway: SplitInstallGateway
) {
    suspend operator fun invoke(dynamicFeatureModule: DynamicFeatureModule): DownloadModuleSessionId {
        return splitInstallGateway.startInstallModule(dynamicFeatureModule)
//        return suspendCoroutine { continuation ->
//            val request = SplitInstallRequest.newBuilder()
//                .addModule(dynamicFeatureModule.codeName)
//                .build()
//            splitInstallManager.startInstall(request)
//                .addOnSuccessListener { continuation.resume(DownloadModuleSessionId(it)) }
//                .addOnFailureListener { continuation.resumeWithException(it) }
//        }
    }
}
