package com.sample.todo.base.listener

object MenuListenerKt {
    @JvmStatic
    fun menuListenerOf(function: Function1<Int, Boolean>): MenuListener = MenuListener(function)
}
