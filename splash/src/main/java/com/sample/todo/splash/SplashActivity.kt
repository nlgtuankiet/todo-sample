package com.sample.todo.splash

import android.app.Activity
import android.os.Bundle
import com.sample.todo.appnavigation.SplashNavigator

class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SplashNavigator.toMainActivity(this)
    }
}