package com.sample.todo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.fragmentViewModel
import com.sample.todo.settings.databinding.SettingsFragmentBinding
import javax.inject.Inject
import com.airbnb.mvrx.withState
import com.sample.todo.base.BaseFragment

class SettingsFragment : com.sample.todo.base.BaseFragment() {
    private lateinit var binding: SettingsFragmentBinding
    @Inject
    lateinit var viewModelFactory: SettingsViewModel.Factory
    private val settingsViewModel: SettingsViewModel by fragmentViewModel()

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

    override fun invalidate() {
        withState(settingsViewModel) {
            binding.state = it
        }
    }
}