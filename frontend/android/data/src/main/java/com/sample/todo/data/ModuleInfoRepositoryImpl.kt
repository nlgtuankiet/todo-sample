package com.sample.todo.data

import android.content.Context
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.sample.todo.data.core.DataScope
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@DataScope
class ModuleInfoRepositoryImpl @Inject constructor(
    private val context: Context
) {
    private val splitInstallManager by lazy {
        SplitInstallManagerFactory.create(context)
    }

    fun isModuleInstalled(name: String): Boolean {
        return splitInstallManager.installedModules.contains(name)
    }

    suspend fun installModules(vararg names: String): Unit = suspendCoroutine { continuation ->
        val builder = SplitInstallRequest.newBuilder()
        names.forEach { builder.addModule(it) }
        val request = builder.build()
//        SplitInstallStateUpdatedListener
        splitInstallManager.startInstall(request)
            .addOnSuccessListener { continuation.resume(Unit) }
            .addOnFailureListener { continuation.resumeWithException(it) }
    }
}