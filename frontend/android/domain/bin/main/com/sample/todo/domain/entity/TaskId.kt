package com.sample.todo.domain.entity

import java.util.Random

inline class TaskId(val value: String) {
    companion object {
        private const val ID_LENGTH = 20
        private const val CONTENT = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        private fun generateId(length: Int = ID_LENGTH, content: String = CONTENT): String {
            val builder = StringBuilder()
            val rand = Random()
            for (i in 0 until length) {
                builder.append(content[rand.nextInt(content.length)])
            }
            return builder.toString()
        }

        fun newInstance(): TaskId =
            TaskId(generateId())

        fun validate(taskId: TaskId): Boolean {
            val value = taskId.value
            if (value.length != ID_LENGTH) return false

            for (c in value.asSequence().distinct()) {
                if (!CONTENT.contains(c)) {
                    return false
                }
            }

            return true
        }

        fun validate(taskId: String): Boolean {
            if (taskId.length != ID_LENGTH) return false

            for (c in taskId.asSequence().distinct()) {
                if (!CONTENT.contains(c)) {
                    return false
                }
            }
            return true
        }
    }
}

val TaskId.isValid: Boolean
    get() = TaskId.validate(this)
