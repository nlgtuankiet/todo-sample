package com.sample.todo.initializer

import android.app.Application
import com.facebook.stetho.Stetho
import com.sample.todo.BuildConfig
import javax.inject.Inject

class StethoInitializer @Inject constructor() : Initializer {
    override fun initialize(application: Application) {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(application)
        }
    }
}