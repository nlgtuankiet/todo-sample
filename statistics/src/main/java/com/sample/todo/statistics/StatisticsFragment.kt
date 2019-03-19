package com.sample.todo.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.sample.todo.statistics.databinding.StatisticsFragmentBinding
import javax.inject.Inject

class StatisticsFragment : com.sample.todo.base.BaseFragment() {
    @Inject
    lateinit var viewModelFactory: StatisticsViewModel.Factory
    private val statisticsViewModel: StatisticsViewModel by fragmentViewModel()
    lateinit var binding: StatisticsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StatisticsFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = statisticsViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun invalidate() {
        withState(statisticsViewModel) {
            binding.state = it
        }
    }
}