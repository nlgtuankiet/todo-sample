package com.sample.todo.dynamic.seeddatabase.worker

import androidx.work.Data
import com.sample.todo.base.extension.getOrThrow

data class Parameter(
    val isBrandNew: Boolean = true,
    val totalTasks: Long = 10,
    val itemPerTrunk: Long = 10000,
    val minTitleLength: Int = 1,
    val maxTitleLength: Int = 3,
    val minDescriptionParagraph: Int = 1,
    val maxDescriptionParagraph: Int = 3
) {
    // TODO implement and test
    fun validate(): Parameter {
        return this
    }

    fun toData(): Data {
        val values = mutableMapOf<String, Any?>().apply {
            put("isBrandNew", isBrandNew)
            put("totalTasks", totalTasks)
            put("itemPerTrunk", itemPerTrunk)
            put("minTitleLength", minTitleLength)
            put("maxTitleLength", maxTitleLength)
            put("minDescriptionParagraph", minDescriptionParagraph)
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
                val itemPerTrunk: Long = getOrThrow(key = "itemPerTrunk", allowNull = false)
                val totalTasks: Long = getOrThrow(key = "totalTasks", allowNull = false)
                val minTitleLength: Int = getOrThrow(key = "minTitleLength", allowNull = false)
                val maxTitleLength: Int = getOrThrow(key = "maxTitleLength", allowNull = false)
                val minDescriptionParagraph: Int = getOrThrow(key = "minDescriptionParagraph", allowNull = false)
                val maxDescriptionParagraph: Int = getOrThrow(key = "maxDescriptionParagraph", allowNull = false)
                return Parameter(
                    isBrandNew = isBrandNew,
                    totalTasks = totalTasks,
                    itemPerTrunk = itemPerTrunk,
                    maxTitleLength = maxTitleLength,
                    minTitleLength = minTitleLength,
                    minDescriptionParagraph = minDescriptionParagraph,
                    maxDescriptionParagraph = maxDescriptionParagraph
                )
            }
        }
    }
}
