package com.sample.todo.dynamic.seeddatabase.lorem

interface Lorem {
    fun getTitle(min: Int, max: Int): String
    fun getParagraphs(min: Int, max: Int): String
}