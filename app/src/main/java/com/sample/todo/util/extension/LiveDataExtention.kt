package com.sample.todo.util.extension

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.sample.todo.core.Event
import com.sample.todo.ui.message.Message

// TODO: understand and improve this
fun <T> LiveData<T>.debounce(duration: Long = 500L): LiveData<T> = MediatorLiveData<T>().also { mld ->
    val source = this
    val handler = Handler(Looper.getMainLooper())
    val runnable = Runnable {
        mld.value = source.value
    }
    mld.addSource(source) {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, duration)
    }
}

fun <T> MutableLiveData<T>.setValueIfNew(newValue: T) {
    if (this.value != newValue) value = newValue
}

fun <T> MutableLiveData<T>.postValueIfNew(newValue: T) {
    if (this.value != newValue) postValue(newValue)
}

@MainThread
inline fun <T> LiveData<Event<T>>.observeEvent(
    owner: LifecycleOwner,
    crossinline onEventUnhandled: (T) -> Unit
): Observer<Event<T>> {
    val wrappedObserver = Observer<Event<T>> { event ->
        event?.getContentIfNotHandled()?.let(onEventUnhandled)
    }
    observe(owner, wrappedObserver)
    return wrappedObserver
}

fun MutableLiveData<Event<Message>>.postNewMessage(messageId: Int) {
    postValue(Event(Message(messageId = messageId)))
}

fun MutableLiveData<Event<Unit>>.setNewEvent() {
    value = Event(Unit)
}

fun MutableLiveData<Event<Unit>>.postNewEvent() {
    postValue(Event(Unit))
}