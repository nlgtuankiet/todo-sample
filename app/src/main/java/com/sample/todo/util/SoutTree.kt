package com.sample.todo.util

import android.os.Build
import androidx.annotation.RequiresApi
import timber.log.Timber
import java.time.LocalTime

class SoutTree : Timber.DebugTree() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val time = LocalTime.now()
        println("$time $tag: $message")
        if (t != null) {
            println("$tag: ${t.message}")
            t.printStackTrace()
        }
    }
}