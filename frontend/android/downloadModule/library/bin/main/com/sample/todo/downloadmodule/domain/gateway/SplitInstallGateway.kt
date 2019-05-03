package com.sample.todo.downloadmodule.domain.gateway

import com.sample.todo.downloadmodule.domain.entity.DownloadModuleSessionId
import com.sample.todo.downloadmodule.domain.entity.DynamicFeatureModule

interface SplitInstallGateway {
    fun isModuleInstalled(dynamicFeatureModule: DynamicFeatureModule): Boolean
    suspend fun isModuleInstalling(dynamicFeatureModule: DynamicFeatureModule): Boolean
    suspend fun startInstallModule(dynamicFeatureModule: DynamicFeatureModule): DownloadModuleSessionId
}