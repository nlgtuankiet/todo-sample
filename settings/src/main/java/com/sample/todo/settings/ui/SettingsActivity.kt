package com.sample.todo.settings.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sample.todo.TodoApplication
import com.sample.todo.settings.R
import com.sample.todo.settings.databinding.SettingsActivityBinding
import com.sample.todo.settings.di.SettingsComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class SettingsActivity : AppCompatActivity(), HasSupportFragmentInjector {
    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return supportFragmentInjector
    }

    private lateinit var settingsComponent: SettingsComponent
    @Inject
    lateinit var notificationManager: NotificationManagerCompat

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>
    private lateinit var binding: SettingsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@SettingsActivity, R.layout.settings_activity)
        binding.apply {
            lifecycleOwner = this@SettingsActivity
        }
        settingsComponent = SettingsComponent
            .builder()
            .appComponent(run {
                val todoApp = applicationContext as? TodoApplication
                    ?: TODO()
                todoApp.appComponent
            })
            .settingsActivity(this@SettingsActivity)
            .build()
        settingsComponent.inject(this)
        println("notificationManager is $notificationManager")
    }
}