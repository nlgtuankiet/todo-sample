package com.sample.todo.base.widget

data class ToolbarData(
    val menu: Int? = null,
    val menuItemClickHandler: Function1<Int, Boolean>? = null,
    val navigationIcon: Int? = null,
    val navigationClickHandler: Function0<Unit>? = null
)
