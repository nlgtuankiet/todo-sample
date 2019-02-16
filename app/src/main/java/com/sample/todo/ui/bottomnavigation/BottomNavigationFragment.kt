package com.sample.todo.ui.bottomnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.sample.todo.databinding.BottomNavigationFragmentBinding
import com.sample.todo.ui.HasTopNavigation
import com.sample.todo.ui.TodoViewModelFactory
import com.sample.todo.util.DaggerBottomSheetFragment
import javax.inject.Inject

class BottomNavigationFragment : DaggerBottomSheetFragment() {
    @Inject
    lateinit var viewModelFactory: TodoViewModelFactory
    private val bottomNavigationViewModel: BottomNavigationViewModel by viewModels { viewModelFactory }
    private lateinit var binding: BottomNavigationFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomNavigationFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = bottomNavigationViewModel
            setLifecycleOwner(viewLifecycleOwner)
            navigationView.apply {
                menu.findItem(requireHasTopNavigation().getCurrentNavControllerId()).isChecked = true
                setNavigationItemSelectedListener {
                    val topNav = requireHasTopNavigation()
                    topNav.onTopNavigationSelected(it.itemId)
                    true.also {
                        dismiss()
                    }
                }
            }
        }
        return binding.root
    }

    private fun requireHasTopNavigation(): HasTopNavigation {
        val activity = requireActivity()
        return (activity as? HasTopNavigation)
            ?: throw IllegalArgumentException("${activity::class.java.name} is not HasTopNavigation")
    }

    companion object {
        fun showNewInstance(fragmentManager: FragmentManager) {
            BottomNavigationFragment().show(fragmentManager, "BottomNavigationFragment")
        }
    }
}