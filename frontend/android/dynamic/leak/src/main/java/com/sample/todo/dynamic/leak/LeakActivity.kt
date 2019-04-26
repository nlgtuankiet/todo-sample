package com.sample.todo.dynamic.leak

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button

class LeakActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.leak_activity)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            startAsyncWork()
        }
    }

    @SuppressLint("StaticFieldLeak")
    internal fun startAsyncWork() {
        // This runnable is an anonymous class and therefore has a hidden reference to the outer
        // class MainActivity. If the activity gets destroyed before the thread finishes (e.g. rotation),
        // the activity instance will leak.
        val work = Runnable {
            // Do some slow work in background
            SystemClock.sleep(20000)
        }
        Thread(work).start()
    }
}


