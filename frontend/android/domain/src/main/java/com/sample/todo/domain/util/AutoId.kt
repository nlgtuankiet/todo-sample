package com.sample.todo.domain.util

import java.util.Random

private const val autoIdLength = 20
private const val authIdAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
private val rand = Random()

fun autoId(length: Int = autoIdLength, content: String = authIdAlphabet): String {
    val builder = StringBuilder()
    for (i in 0 until length) {
        builder.append(content[rand.nextInt(content.length)])
    }
    return builder.toString()
}