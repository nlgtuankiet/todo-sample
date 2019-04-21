package com.sample.todo.base.entity

import androidx.annotation.StringRes
import com.sample.todo.base.R

enum class DynamicFeatureModule(
    val codeName: String,
    @StringRes val displayName: Int
) {
    SETTINGS("settings", R.string.dynamic_feature_module_settings_name),
    SEED_DATABASE("seedDatabase", R.string.dynamic_feature_module_seed_database_name),
    DATA_TASK_FIRESTORE("dataTaskFirestore", R.string.dynamic_feature_module_data_task_firestore_name),
    DATA_TASK_SQLDELIGHT("dataTaskSqlDelight", R.string.dynamic_feature_module_data_task_sqldelight_name);

    companion object {
        fun fromString(inputName: String): DynamicFeatureModule? {
            return values().firstOrNull { module ->
                module.codeName == inputName
            }
        }
    }
}
