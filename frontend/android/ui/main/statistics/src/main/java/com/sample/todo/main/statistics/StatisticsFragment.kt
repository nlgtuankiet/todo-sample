package com.sample.todo.main.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.sample.todo.main.statistics.databinding.StatisticsFragmentBinding
import javax.inject.Inject

class StatisticsFragment(
    private val viewModelFactory: ViewModelProvider.Factory
) : Fragment() {
    private val statisticsViewModel: StatisticsViewModel by viewModels { viewModelFactory }
    private lateinit var binding: StatisticsFragmentBinding

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
}