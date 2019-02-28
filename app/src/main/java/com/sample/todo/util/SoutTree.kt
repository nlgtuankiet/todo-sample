package com.sample.todo.util

import android.os.Build
import androidx.annotation.RequiresApi
import timber.log.Timber
import java.time.LocalTime

class SoutTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        println("$tag: $message")
        if (t != null) {
            println("$tag: ${t.message}")
            t.printStackTrace()
        }
    }
}