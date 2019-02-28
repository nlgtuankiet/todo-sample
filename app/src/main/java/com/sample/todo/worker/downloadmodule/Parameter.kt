package com.sample.todo.worker.downloadmodule

import androidx.work.Data
import com.sample.todo.util.getOrThrow

data class Parameter(
    val modules: List<Module>
) {
    sealed class Module(val name: String) {
        object Settings : Module("settings")

        companion object {
            fun fromString(name: String): Module {
                return when(name) {
                    "settings" -> Settings
                    else -> TODO()
                }
            }
        }
    }

    // TODO implement and test
    fun validate(): Parameter {
        if (modules.isEmpty()) TODO()
        return this
    }

    fun toData(): Data {
        val values = mutableMapOf<String, Any?>().apply {
            put("moduleArray", modules.map { it.name }.toTypedArray())
        }
        return Data.Builder()
            .putAll(values)
            .build()
    }

    companion object {
        fun fromData(data: Data): Parameter {
            data.keyValueMap.run {
                val moduleNames: Array<String> = getOrThrow(key = "moduleArray", allowNull = false)
                return Parameter(
                    modules = moduleNames.map { Module.fromString(it) }
                )
            }
        }
    }
}