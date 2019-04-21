package com.sample.todo.base.widget

data class ToolbarData(
    val menu: Int? = null,
    val menuItemClickHandler: ((Int) -> Boolean)? = null,
    val navigationIcon: Int? = null,
    val navigationClickHandler: (() -> Unit)? = null
)
