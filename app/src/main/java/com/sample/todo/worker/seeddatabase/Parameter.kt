package com.sample.todo.worker.seeddatabase

import androidx.work.Data
import com.sample.todo.util.getOrThrow

data class Parameter(
    val isBrandNew: Boolean = true,
    val totalTasks: Int? = null,
    val maxTitleLength: Int? = null,
    val maxDescriptionParagraph: Int? = null
) {
    // TODO implement and test
    fun validate(): Parameter {
        if (isBrandNew) {
            if (totalTasks == null) throw IllegalArgumentException("isBrandNew is true but totalTasks is null")
        }
        return this
    }

    fun toData(): Data {
        val values = mutableMapOf<String, Any?>().apply {
            put("isBrandNew", isBrandNew)
            put("totalTasks", totalTasks)
            put("maxTitleLength", maxTitleLength)
            put("maxDescriptionParagraph", maxDescriptionParagraph)
        }
        return Data.Builder()
            .putAll(values)
            .build()
    }

    companion object {
        fun fromData(data: Data): Parameter {
            data.keyValueMap.run {
                val isBrandNew: Boolean = getOrThrow(key = "isBrandNew", allowNull = false)
                val totalTasks: Int? = getOrThrow(key = "totalTasks", allowNull = true)
                val maxTitleLength: Int? = getOrThrow(key = "maxTitleLength", allowNull = true)
                val maxDescriptionParagraph: Int? = getOrThrow(key = "maxDescriptionParagraph", allowNull = true)
                return Parameter(
                    isBrandNew = isBrandNew,
                    totalTasks = totalTasks,
                    maxTitleLength = maxTitleLength,
                    maxDescriptionParagraph = maxDescriptionParagraph
                )
            }
        }
    }
}
