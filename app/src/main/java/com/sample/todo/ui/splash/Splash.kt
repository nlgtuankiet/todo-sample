package com.sample.todo.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.navigation.ActivityNavigator
import com.sample.todo.ui.HostActivity

class Splash : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val goIntent = if (intent?.extras != null
//            && intent.extras?.containsKey("android-support-nav:controller:deepLinkExtras") == true) {
//            // this is from deep link
//            intent.setClass(this, HostActivity::class.java)
//        } else {
//            Intent(this, HostActivity::class.java)
//        }
//        startActivity(goIntent)
//        finish()
        ActivityNavigator(this).apply {
            val des = createDestination().apply {
                intent = Intent(this@Splash, HostActivity::class.java)
            }
            navigate(des, intent.extras, null, null)
        }
        finish()
    }
}