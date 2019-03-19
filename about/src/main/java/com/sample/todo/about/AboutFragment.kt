package com.sample.todo.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.sample.todo.about.databinding.AboutFragmentBinding
import com.sample.todo.appnavigation.MainNavigator
import com.sample.todo.base.extension.observeEvent
import com.sample.todo.work.downloadmodule.DownloadModuleWorker
import com.sample.todo.work.downloadmodule.Parameter
import com.sample.todo.work.seeddatabase.Parameter as SeedParameter
import com.sample.todo.work.seeddatabase.SeedDatabaseWorker
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AboutFragment : DaggerFragment() {
    private lateinit var binding: AboutFragmentBinding
    private val aboutViewModel: AboutViewModel by viewModels()
    @Inject
    lateinit var splitInstallManager: SplitInstallManager
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
                val param = SeedParameter(totalTasks = 10, itemPerTrunk = 1)
                workManager.enqueueUniqueWork(
                    SeedDatabaseWorker.WORK_MANE,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                    .setInputData(param.toData())
                    .build()
                )
            }
        }
        aboutViewModel.apply {
            navigateToStatisticsEvent.observeEvent(viewLifecycleOwner) {
                findNavController().navigate(R.id.statisticsFragment)
            }
            navigateToSettingsEvent.observeEvent(viewLifecycleOwner) {
                navigateToSettings()
            }
        }
        return binding.root
    }

    private fun navigateToSettings() {
        val navigated = MainNavigator.toSettingsActivity(requireActivity())
        if (!navigated) {
            workManager.enqueue(
                OneTimeWorkRequestBuilder<DownloadModuleWorker>()
                    .setInputData(
                        Parameter(
                            modules = listOf(Parameter.Module.Settings)
                        )
                            .toData()
                    )
                    .build()
            )
        }
    }
}