package com.sample.todo.main.setting.seedinput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.sample.todo.base.extension.observeEvent
import com.sample.todo.main.setting.databinding.SettingSeedInputFragmentBinding
import com.sample.todo.work.seeddatabase.SeedDatabaseWorker
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class SeedInputFragment : DaggerDialogFragment() {
    private val seedInputViewModel: SeedInputViewModel by viewModels()
    lateinit var binding: SettingSeedInputFragmentBinding
    @Inject
    lateinit var workManager: WorkManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingSeedInputFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = seedInputViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        seedInputViewModel.run {
            requestSeedDatabaseEvent.observeEvent(viewLifecycleOwner) { parameter ->
                workManager.enqueueUniqueWork(
                    SeedDatabaseWorker.WORK_MANE,
                    ExistingWorkPolicy.REPLACE,
                    OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                        .setInputData(parameter.toData())
                        .build()
                )
            }
        }
        return binding.root
    }

    companion object {
        fun showNewInstance(fragmentManager: FragmentManager) {
            // TODO what tag mean?
            SeedInputFragment().show(fragmentManager, "aaa")
        }
    }
}