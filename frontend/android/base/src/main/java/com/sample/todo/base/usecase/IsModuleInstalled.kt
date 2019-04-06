package com.sample.todo.base.usecase

import com.google.android.play.core.splitinstall.SplitInstallManager
import com.sample.todo.base.di.AppScope
import com.sample.todo.base.entity.DynamicFeatureModule
import javax.inject.Inject

@AppScope
class IsModuleInstalled @Inject constructor(
    private val splitInstallManager: SplitInstallManager
) {
    operator fun invoke(module: DynamicFeatureModule): Boolean {
        return splitInstallManager.installedModules.any {
            it == module.name
        }
    }
}