package com.sample.todo.base.listener

object ListenerKt {
    @JvmStatic
    fun listenerOf(function: Function0<Unit>): Listener = Listener(function)
}
