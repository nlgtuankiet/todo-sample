package com.sample.todo.testshared

import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class TestObserver<T>(private val expectedCount: Int) : Observer<T> {
    private val _results: MutableList<T> = mutableListOf()
    val results: List<T>
        get() = _results

    private val latch: CountDownLatch = CountDownLatch(expectedCount)

    override fun onChanged(t: T) {
        if (t != null) {
            _results.add(t)
            latch.countDown()
        }
    }

    fun await() {
        latch.await(1, TimeUnit.SECONDS)
    }
}
