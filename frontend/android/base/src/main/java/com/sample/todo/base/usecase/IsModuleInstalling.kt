package com.sample.todo.base.usecase

import com.google.android.play.core.splitinstall.SplitInstallManager
import com.sample.todo.base.di.AppScope
import com.sample.todo.base.entity.DynamicFeatureModule
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@AppScope
class IsModuleInstalling @Inject constructor(
    private val splitInstallManager: SplitInstallManager
) {
    suspend operator fun invoke(module: DynamicFeatureModule): Boolean {
        return suspendCoroutine { continuation ->
            splitInstallManager.sessionStates
                .addOnSuccessListener { stateList ->
                    continuation.resume(
                        stateList.any { state ->
                            state.moduleNames().contains(module.codeName)
                        }
                    )
                }
                .addOnFailureListener { continuation.resumeWithException(it) }
        }
    }
}
