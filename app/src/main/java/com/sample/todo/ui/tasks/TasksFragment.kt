package com.sample.todo.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sample.todo.R
import com.sample.todo.databinding.TasksFragmentBinding
import com.sample.todo.ui.bottomnavigation.BottomNavigationFragment
import com.sample.todo.ui.message.MessageManager
import com.sample.todo.ui.message.setUpSnackbar
import com.sample.todo.util.extension.observeEvent
import com.sample.todo.util.setupWith
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

// TODO fix bug when user double click navigation icon
// TODO @MainThread inline fun <T> LiveData<T>.observe(
class TasksFragment : DaggerFragment() {
    @Inject
    lateinit var messageManager: MessageManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: TasksFragmentBinding

    private val tasksViewModel: TasksViewModel by viewModels { viewModelFactory }
    private var filterPopupMenu: PopupMenu? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TasksFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = tasksViewModel
            lifecycleOwner = viewLifecycleOwner
            tasksRecyclerView.apply {
                adapter = TasksAdapter(
                    lifecycleOwner = viewLifecycleOwner,
                    tasksViewModel = tasksViewModel
                )
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        // TODO fix this with extension companion
                        val canScrollUp = recyclerView.canScrollVertically(-1)
                        tasksViewModel.onTasksRecyclerViewScroll(canScrollUp)
                    }
                })
            }
            bottomNavigationView.setupWith(this@TasksFragment)
        }
        tasksViewModel.apply {
            navigationEvent.observeEvent(viewLifecycleOwner) {
                findNavController().navigate(it)
            }
            showBottomSheetEvent.observeEvent(viewLifecycleOwner) {
                BottomNavigationFragment.showNewInstance(childFragmentManager)
            }
        }

        setUpSnackbar(
            fadingSnackbar = binding.snackBar,
            snackbarMessage = tasksViewModel.snackBarMessage,
            messageManager = messageManager
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tasksViewModel.openFilterPopupEvent.observeEvent(viewLifecycleOwner) {
            showFilterPopupMenu()
        }
    }

    private fun showFilterPopupMenu() {
        filterPopupMenu =
            PopupMenu(requireContext(), binding.toolbar.findViewById(R.id.filter)).apply {
                Timber.d("inflate filter popup menu")
                menuInflater.inflate(R.menu.tasks_filter_popup, menu)
                setOnMenuItemClickListener {
                    tasksViewModel.onFilterMenuSelected(it.itemId)
                    true
                }
            }
        filterPopupMenu?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        /**
         * avoid leak
         * E/WindowManager: android.view.WindowLeaked: Activity com.sample.t odo.ui.HostActivity has leaked window
         * android.widget.PopupWindow$PopupViewContainer{1544591f V.E..... ........ 0,0-147,108}
         * that was originally added here
         */
        filterPopupMenu?.dismiss()
    }
}
