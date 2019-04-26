package com.sample.todo.base.listener

import android.view.View

class Listener(
    private val function: Function0<Unit>
) : View.OnClickListener, Function0<Unit> by function {
    override fun onClick(v: View?) {
        function.invoke()
    }
}
