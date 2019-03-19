package com.sample.todo.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.sample.todo.addedit.databinding.AddEditFragmentBinding
import com.sample.todo.base.BaseFragment
import com.sample.todo.base.extension.hideKeyboard
import com.sample.todo.base.extension.observeEvent
import com.sample.todo.base.message.MessageManager
import com.sample.todo.base.message.setUpSnackbar
import javax.inject.Inject

class AddEditFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: AddEditViewModel.Factory
    @Inject
    lateinit var messageManager: MessageManager
    private lateinit var binding: AddEditFragmentBinding
    private val addEditViewModel: AddEditViewModel by fragmentViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddEditFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = addEditViewModel
            lifecycleOwner = viewLifecycleOwner
            state = withState(addEditViewModel) { it }
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

    override fun invalidate() {
        withState(addEditViewModel) {
            binding.state = it
        }
    }
}