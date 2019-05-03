package com.sample.todo.downloadmodule.domain.entity

import androidx.annotation.StringRes

enum class DynamicFeatureModule(val codeName: String) {
    SETTINGS("settings"),
    SEED_DATABASE("seedDatabase"),
    DATA_TASK_FIRESTORE("dataTaskFirestore"),
    DATA_TASK_SQLDELIGHT("dataTaskSqlDelight");

    companion object {
        fun fromString(inputName: String): DynamicFeatureModule? {
            return values().firstOrNull { module ->
                module.codeName == inputName
            }
        }
    }
}