package com.sample.todo.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.sample.todo.databinding.StatisticsFragmentBinding
import com.sample.todo.util.setupWith
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class StatisticsFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val statisticsViewModel: StatisticsViewModel by viewModels { viewModelFactory }
    lateinit var binding: StatisticsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StatisticsFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = statisticsViewModel
            lifecycleOwner = viewLifecycleOwner
            bottomNavigationView.setupWith(this@StatisticsFragment)
        }
        return binding.root
    }
}