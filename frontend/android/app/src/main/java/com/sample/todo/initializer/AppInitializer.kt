package com.sample.todo.initializer

import android.app.Application
import javax.inject.Inject

class AppInitializer @Inject constructor(
    private val initializes: @JvmSuppressWildcards Set<Initializer>
) : Initializer {
    override fun initialize(application: Application) {
        initializes.forEach { it.initialize(application) }
    }
}
