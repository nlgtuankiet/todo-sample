package com.sample.todo.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.sample.todo.settings.databinding.SettingsFragmentBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import androidx.fragment.app.viewModels

class SettingsFragment : DaggerFragment() {
    private lateinit var binding: SettingsFragmentBinding
    @Inject
    lateinit var notificationManagerCompat: NotificationManagerCompat
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val settingsViewModel: SettingsViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingsFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = settingsViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }
}