package com.sample.todo.base

import android.app.Activity
import android.content.Intent

open class ActivityNavigator(private val activity: Activity) {
    protected fun Intent?.go(): Boolean {
        return if (this == null)
            false
        else {
            activity.startActivity(this, activity.intent.extras)
            true
        }
    }
}
