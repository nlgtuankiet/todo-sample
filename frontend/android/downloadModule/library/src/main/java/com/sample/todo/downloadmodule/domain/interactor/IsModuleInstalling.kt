package com.sample.todo.downloadmodule.domain.interactor

import com.sample.todo.downloadmodule.domain.entity.DynamicFeatureModule
import com.sample.todo.downloadmodule.domain.gateway.SplitInstallGateway
import javax.inject.Inject

class IsModuleInstalling @Inject constructor(
    private val splitInstallGateway: SplitInstallGateway
) {
    suspend operator fun invoke(module: DynamicFeatureModule): Boolean {
        return splitInstallGateway.isModuleInstalled(module)
//        return suspendCoroutine { continuation ->
//            splitInstallManager.sessionStates
//                .addOnSuccessListener { stateList ->
//                    continuation.resume(
//                        stateList.any { state ->
//                            state.moduleNames().contains(module.codeName)
//                        }
//                    )
//                }
//                .addOnFailureListener { continuation.resumeWithException(it) }
//        }
    }
}
