package com.sample.todo.testshared

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.testValue(): T? {
    var data: T? = null
    val latch = java.util.concurrent.CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@testValue.removeObserver(this)
        }
    }
    this.observeForever(observer)
    latch.await(2, java.util.concurrent.TimeUnit.SECONDS)
    return data
}