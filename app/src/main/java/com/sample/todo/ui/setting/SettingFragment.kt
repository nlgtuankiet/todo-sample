package com.sample.todo.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.sample.todo.databinding.SettingFragmentBinding
import com.sample.todo.util.extension.observeEvent
import com.sample.todo.util.setupWith
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SettingFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val settingViewModel: SettingViewModel by viewModels { viewModelFactory }
    lateinit var binding: SettingFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = settingViewModel
            lifecycleOwner = viewLifecycleOwner
            bottomNavigationView.setupWith(this@SettingFragment)
        }
        settingViewModel.apply {
            showSeedInputDialogEvent.observeEvent(viewLifecycleOwner) {
                SeedInputFragment.showNewInstance(childFragmentManager)
            }
        }
        return binding.root
    }
}