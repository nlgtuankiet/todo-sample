package com.sample.todo.base.widget

import com.sample.todo.base.listener.Listener
import com.sample.todo.base.listener.MenuListener

data class ToolbarData(
    val menu: Int? = null,
    val menuItemClickHandler: MenuListener? = null,
    val navigationIcon: Int? = null,
    val navigationClickHandler: Listener? = null
)
