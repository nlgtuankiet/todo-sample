package com.sample.todo.ui.about

import android.content.Intent
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
import com.sample.todo.R
import com.sample.todo.databinding.AboutFragmentBinding
import com.sample.todo.util.extension.observeEvent
import com.sample.todo.worker.downloadmodule.DownloadModuleWorker
import com.sample.todo.worker.seeddatabase.Parameter
import com.sample.todo.worker.seeddatabase.SeedDatabaseWorker
import dagger.android.support.DaggerFragment
import timber.log.Timber
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
                val param = Parameter(totalTasks = 10, itemPerTrunk = 1)
                WorkManager.getInstance().enqueueUniqueWork(
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
        // check if module installed
        val installedModules = splitInstallManager.installedModules
        println(installedModules.toList())
        val isInstalled = installedModules.contains("settings")
        Timber.d("isInstalled: $isInstalled")

        if (!isInstalled) {
            workManager.enqueue(
                OneTimeWorkRequestBuilder<DownloadModuleWorker>()
                    .setInputData(
                        com.sample.todo.worker.downloadmodule.Parameter(
                            modules = listOf(com.sample.todo.worker.downloadmodule.Parameter.Module.Settings)
                        )
                            .toData()
                    )
                    .build()
            )
        } else {
            val intent = Intent(
                requireContext(),
                Class.forName("com.sample.todo.ui.SettingsActivity")
            )
            startActivity(intent)
        }
    }
}