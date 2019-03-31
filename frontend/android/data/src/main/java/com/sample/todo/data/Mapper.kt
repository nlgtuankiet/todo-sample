package com.sample.todo.data

interface Mapper<F, T> {
    fun map(from: F): T
}