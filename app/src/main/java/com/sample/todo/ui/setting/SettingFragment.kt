package com.sample.todo.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sample.todo.databinding.SettingFragmentBinding
import com.sample.todo.ui.setting.seedinput.SeedInputFragment
import com.sample.todo.util.extension.observeEvent
import dagger.android.support.DaggerFragment

class SettingFragment : DaggerFragment() {
    private val settingViewModel: SettingViewModel by viewModels()
    lateinit var binding: SettingFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = settingViewModel
            lifecycleOwner = viewLifecycleOwner
            openSettingsActivityButton.setOnClickListener {
                    val intent = Intent(requireContext(), Class.forName("com.sample.todo.ui.SettingsActivity"))
                    startActivity(intent)
            }
        }
        settingViewModel.apply {
            showSeedInputDialogEvent.observeEvent(viewLifecycleOwner) {
                SeedInputFragment.showNewInstance(childFragmentManager)
            }
        }
        return binding.root
    }
}