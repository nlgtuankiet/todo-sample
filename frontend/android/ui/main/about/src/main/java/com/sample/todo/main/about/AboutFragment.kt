package com.sample.todo.main.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.work.WorkManager
import com.sample.todo.base.entity.DynamicFeatureModule
import com.sample.todo.main.about.databinding.AboutFragmentBinding
import com.sample.todo.navigation.MainNavigator
import com.sample.todo.base.extension.observeEvent
import com.sample.todo.work.downloadmodule.DownloadModuleWorker
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AboutFragment : DaggerFragment() {
    private lateinit var binding: AboutFragmentBinding
    @Inject
    lateinit var factory: AboutViewModelFactory
    private val aboutViewModel: AboutViewModel by viewModels(factoryProducer = { factory })
    @Inject
    lateinit var workManager: WorkManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AboutFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = aboutViewModel
            lifecycleOwner = viewLifecycleOwner
            seedDatabaseButton.setOnClickListener {
                MainNavigator.toSeedDatabaseActivity(requireActivity())
            }
        }
        aboutViewModel.apply {
            navigateToStatisticsEvent.observeEvent(viewLifecycleOwner) {
                findNavController().navigate(R.id.statisticsFragment)
            }
            navigateToSettingsEvent.observeEvent(viewLifecycleOwner) {
                navigateToSettings()
            }
            displayModuleDetailDialogEvent.observeEvent(viewLifecycleOwner) {
                // TODO download module for now, display module detail dialog later
                DownloadModuleWorker.enqueNewWorker(DynamicFeatureModule.SETTINGS)
            }
        }
        return binding.root
    }

    private fun navigateToSettings() {
        val navigated = MainNavigator.toSettingsActivity(requireActivity())
        if (!navigated)
            throw IllegalArgumentException("Why?")
    }
}