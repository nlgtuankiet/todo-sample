package com.sample.todo.initializer

import android.app.Application

interface Initializer {
    fun initialize(application: Application)
}