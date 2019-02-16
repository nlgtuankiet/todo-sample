package com.sample.todo.util.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.lang.RuntimeException

fun View.hideKeyboard() {
    val imm =
        this.context.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            ?: throw RuntimeException("How?")
    imm.hideSoftInputFromWindow(windowToken, 0)
}