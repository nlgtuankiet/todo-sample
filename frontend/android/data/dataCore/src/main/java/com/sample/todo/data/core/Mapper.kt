package com.sample.todo.data.core

interface Mapper<F, T> : Function1<F, T> {
    override fun invoke(from: F): T
}