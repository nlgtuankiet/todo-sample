package com.sample.todo.downloadmodule.domain.interactor

import com.sample.todo.downloadmodule.domain.entity.DynamicFeatureModule
import com.sample.todo.downloadmodule.domain.gateway.SplitInstallGateway
import javax.inject.Inject

class IsModuleInstalled @Inject constructor(
    private val splitInstallGateway: SplitInstallGateway
) {
    operator fun invoke(module: DynamicFeatureModule): Boolean {
        return splitInstallGateway.isModuleInstalled(module)
    }
}
