package com.sample.todo.main.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.sample.todo.base.extension.hideKeyboard
import com.sample.todo.base.extension.observeEvent
import com.sample.todo.domain.repository.MessageManager
import com.sample.todo.extension.setUpSnackbar
import com.sample.todo.main.addedit.databinding.AddEditFragmentBinding

class AddEditFragment(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val messageManager: MessageManager,
    private val navigator: AddEditNavigator
) : Fragment() {
    private lateinit var binding: AddEditFragmentBinding
    private val addEditViewModel: AddEditViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddEditFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = addEditViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        setUpSnackbar(
            messageManager = messageManager,
            snackbarMessage = addEditViewModel.snackBarMessage,
            fadingSnackbar = binding.snackBar
        )
        addEditViewModel.apply {
            navigateUpEvent.observeEvent(viewLifecycleOwner) {
                binding.root.hideKeyboard()
                navigator.up()
            }
        }
        return binding.root
    }
}
