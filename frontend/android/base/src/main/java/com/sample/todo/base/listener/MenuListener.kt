package com.sample.todo.base.listener

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

class MenuListener(
    private val function: Function1<Int, Boolean>
) : MenuItem.OnMenuItemClickListener, Toolbar.OnMenuItemClickListener, Function1<Int, Boolean> by function {
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return function(item?.itemId ?: 0)
    }
}
