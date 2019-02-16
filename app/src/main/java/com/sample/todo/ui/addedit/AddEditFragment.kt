package com.sample.todo.ui.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sample.todo.databinding.AddEditFragmentBinding
import com.sample.todo.ui.message.MessageManager
import com.sample.todo.ui.message.setUpSnackbar
import com.sample.todo.util.extension.hideKeyboard
import com.sample.todo.util.extension.observeEvent
import com.sample.todo.util.setupWith
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AddEditFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var messageManager: MessageManager
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
            bottomNavigationView.setupWith(this@AddEditFragment)
        }
        setUpSnackbar(
            messageManager = messageManager,
            snackbarMessage = addEditViewModel.snackBarMessage,
            fadingSnackbar = binding.snackBar
        )
        addEditViewModel.apply {
            navigateUpEvent.observeEvent(viewLifecycleOwner) {
                binding.root.hideKeyboard()
                findNavController().navigateUp()
            }
        }
        return binding.root
    }
}