package com.sample.todo.splash

import com.sample.todo.base.ActivityNavigator
import com.sample.todo.base.di.ActivityScope
import com.sample.todo.navigation.IntentProvider
import javax.inject.Inject

@ActivityScope
class SplashNavigator @Inject constructor(
    private val intentProvider: IntentProvider,
    private val splashActivity: SplashActivity
) : ActivityNavigator(splashActivity) {
    fun toMainActivity(): Boolean {
        return intentProvider.mainActivityIntent(splashActivity).go()
    }
}
