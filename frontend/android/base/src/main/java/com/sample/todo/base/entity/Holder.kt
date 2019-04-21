package com.sample.todo.base.entity

class Holder<T : Any> {
    private var _instance: T? = null
    var instance: T
        get() = requireNotNull(_instance)
        set(value) {
            require(_instance == null)
            _instance = value
        }
}