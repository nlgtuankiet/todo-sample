package com.sample.todo.util.extension

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.observe
import com.sample.todo.base.entity.Event
import com.sample.todo.base.extension.observeEvent
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LiveDataExtensionTest : LifecycleOwner {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var lifecycleRegistry: LifecycleRegistry

    override fun getLifecycle() = lifecycleRegistry

    @Before
    fun setup() {
        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    @After
    fun teardown() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    // region distinctUntilChanged()
    @Test
    fun `distinct true, false, false, true`() {
        val input: List<Boolean?> = listOf(true, false, false, true)
        val expecteds: List<Boolean?> = listOf(true, false, true)

        val mutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
        val actuals: MutableList<Boolean?> = mutableListOf()
        val observer: (Boolean?) -> Unit = { actuals.add(it) }
        mutableLiveData
            .distinctUntilChanged()
            .observe(this, observer)
        input.forEach { mutableLiveData.value = it }

        assertEquals(expecteds, actuals.toList())
    }

    @Test
    fun `distinct null, null, true`() {
        val input: List<Boolean?> = listOf(null, null, true)
        val expecteds: List<Boolean?> = listOf(null, true)

        val mutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
        val actuals: MutableList<Boolean?> = mutableListOf()
        val observer: (Boolean?) -> Unit = { actuals.add(it) }
        mutableLiveData
            .distinctUntilChanged()
            .observe(this, observer)
        input.forEach { mutableLiveData.value = it }

        assertEquals(expecteds, actuals.toList())
    }

    @Test
    fun `distinct true, null, null, true`() {
        val input: List<Boolean?> = listOf(true, null, null, true)
        val expecteds: List<Boolean?> = listOf(true, null, true)

        val mutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
        val actuals: MutableList<Boolean?> = mutableListOf()
        val observer: (Boolean?) -> Unit = { actuals.add(it) }
        mutableLiveData
            .distinctUntilChanged()
            .observe(this, observer)
        input.forEach { mutableLiveData.value = it }

        assertEquals(expecteds, actuals.toList())
    }
    // endregion

    // region event
    @Test
    fun `event observe directly`() {
        val input: List<Int> = listOf(1, 2, 3, 4)
        val expecteds: List<Int> = listOf(1, 2, 3, 4)

        val mutableLiveData: MutableLiveData<Event<Int>> = MutableLiveData()
        val actuals: MutableList<Int> = mutableListOf()
        val observer: (Int) -> Unit = { actuals.add(it) }
        mutableLiveData.observeEvent(this, observer)
        input.forEach { mutableLiveData.value = Event(it) }

        assertEquals(expecteds, actuals.toList())
    }

    @Test
    fun `event observe directly late`() {
        val mutableLiveData: MutableLiveData<Event<Int>> = MutableLiveData()
        val actual: MutableList<Int> = mutableListOf()
        val observer: (Int) -> Unit = { actual.add(it) }

        mutableLiveData.value = Event(1)
        mutableLiveData.value = Event(2)
        mutableLiveData.observeEvent(this, observer)
        mutableLiveData.value = Event(3)
        mutableLiveData.value = Event(4)

        assertEquals(listOf(2, 3, 4), actual)
    }

    @Test
    fun `event observe with 2 active observer`() {
        val mutableLiveData: MutableLiveData<Event<Int>> = MutableLiveData()
        val actual1: MutableList<Int> = mutableListOf()
        val actual2: MutableList<Int> = mutableListOf()
        val observer1: (Int) -> Unit = { actual1.add(it) }
        val observer2: (Int) -> Unit = { actual2.add(it) }

        mutableLiveData.observeEvent(this, observer1)
        mutableLiveData.value = Event(1)
        mutableLiveData.value = Event(2)

        mutableLiveData.observeEvent(this, observer2)
        mutableLiveData.value = Event(3)
        mutableLiveData.value = Event(4)

        println("actual1=$actual1")
        println("actual2=$actual2")
        assertEquals(listOf(1, 2, 3, 4), actual1 + actual2)
    }

    @Test
    fun `event observe with 2 active observer + null Event`() {
        val mutableLiveData: MutableLiveData<Event<Int>> = MutableLiveData()
        val actual1: MutableList<Int> = mutableListOf()
        val actual2: MutableList<Int> = mutableListOf()
        val observer1: (Int) -> Unit = { actual1.add(it) }
        val observer2: (Int) -> Unit = { actual2.add(it) }

        mutableLiveData.observeEvent(this, observer1)
        mutableLiveData.value = Event(1)
        mutableLiveData.value = null
        mutableLiveData.value = Event(2)

        mutableLiveData.observeEvent(this, observer2)
        mutableLiveData.value = Event(3)
        mutableLiveData.value = null
        mutableLiveData.value = Event(4)
        mutableLiveData.value = null

        println("actual1=$actual1")
        println("actual2=$actual2")
        assertEquals(listOf(1, 2, 3, 4), actual1 + actual2)
    }
    // endregion
}
