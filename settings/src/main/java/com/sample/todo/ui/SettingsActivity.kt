package com.sample.todo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sample.todo.settings.R
import com.sample.todo.settings.databinding.SettingsActivityBinding
import com.sample.todo.di.SettingsComponent
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
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>
    private lateinit var binding: SettingsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        settingsComponent = SettingsComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@SettingsActivity, R.layout.settings_activity)
        binding.apply {
            lifecycleOwner = this@SettingsActivity
        }
    }
}