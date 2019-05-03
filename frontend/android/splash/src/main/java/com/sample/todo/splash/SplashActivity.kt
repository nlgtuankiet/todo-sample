package com.sample.todo.splash

import android.os.Bundle
import dagger.android.DaggerActivity
import timber.log.Timber
import javax.inject.Inject

class SplashActivity : DaggerActivity() {
    @Inject
    lateinit var navigator: SplashNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("enter SplashActivity")
        navigator.toMainActivity()
    }
}
