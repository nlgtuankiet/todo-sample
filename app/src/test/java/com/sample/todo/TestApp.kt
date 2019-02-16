package com.sample.todo

import com.sample.todo.util.SoutTree
import timber.log.Timber

class TestApp : TodoApplication() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(SoutTree())
    }

    override fun isInUnitTests() = true
}