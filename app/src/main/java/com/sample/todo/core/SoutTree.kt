package com.sample.todo.core

import timber.log.Timber

class SoutTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        println("$tag: $message")
        if (t != null) {
            println("$tag: ${t.message}")
            t.printStackTrace()
        }
    }
}