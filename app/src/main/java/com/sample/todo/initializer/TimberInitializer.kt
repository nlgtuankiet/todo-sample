package com.sample.todo.initializer

import android.app.Application
import com.sample.todo.BuildConfig
import timber.log.Timber
import javax.inject.Inject

class TimberInitializer @Inject constructor() : Initializer {
    override fun initialize(application: Application) {
//        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
//        }
    }
}