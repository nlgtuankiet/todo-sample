package com.sample.todo.domain

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.threeten.bp.ZonedDateTime

fun main() = runBlocking {
    val a = flow {
        val channel = Channel<Int>(CONFLATED)
        launch {
            repeat(5) {
                delay(500)
                channel.offer(it)
            }
            channel.close()
        }


        try {
            emit(0)
            for (item in channel) {
                emit(item)
            }
        } finally {
        }
    }
    a.collect {
        println(it)
    }
    println("c2")
    a.collect {
        println(it)
    }
    println("c3")
    a.collect {
        println(it)
    }
    println(ZonedDateTime.now())
}
