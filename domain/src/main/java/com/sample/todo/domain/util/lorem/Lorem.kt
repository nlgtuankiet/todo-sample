package com.sample.todo.domain.util.lorem

interface Lorem {
    fun getTitle(min: Int, max: Int): String
    fun getParagraphs(min: Int, max: Int): String
}