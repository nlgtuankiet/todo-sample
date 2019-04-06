package com.sample.todo.splash

import android.app.Activity
import android.os.Bundle
import com.sample.todo.navigation.SplashNavigator
import timber.log.Timber

class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("enter SplashActivity")
        SplashNavigator.toMainActivity(this)
    }
}