package com.sample.todo.main.tasks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.mvrx.MvRxView
import com.airbnb.mvrx.MvRxViewModelStore
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.sample.todo.base.extension.observeEvent
import com.sample.todo.domain.repository.MessageManager
import com.sample.todo.extension.setUpSnackbar
import com.sample.todo.main.tasks.R
import com.sample.todo.main.tasks.databinding.TasksFragmentBinding
import timber.log.Timber

// TODO fix bug when user double click navigation icon
class TasksFragment(
    val viewModelFactory: TasksViewModel.Factory,
    private val messageManager: MessageManager,
    private val tasksController: TasksController,
    private val navigator: TasksNavigator
) : Fragment(), MvRxView {

    override val mvrxViewModelStore by lazy { MvRxViewModelStore(viewModelStore) }

    override fun onCreate(savedInstanceState: Bundle?) {
        mvrxViewModelStore.restoreViewModels(this, savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvrxViewModelStore.saveViewModels(outState)
    }

    override fun onStart() {
        super.onStart()
        postInvalidate()
    }

    private lateinit var binding: TasksFragmentBinding
    val tasksViewModel: TasksViewModel by fragmentViewModel()
    private var filterPopupMenu: PopupMenu? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TasksFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = tasksViewModel
            setLifecycleOwner(viewLifecycleOwner)
            tasksRecyclerView.apply {
                adapter = tasksController.adapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        // TODO fix this with extension companion
                        val canScrollUp = recyclerView.canScrollVertically(-1)
                        tasksViewModel.onTasksRecyclerViewScroll(canScrollUp)
                    }
                })
            }
        }
        tasksViewModel.apply {
            navigationEvent.observeEvent(viewLifecycleOwner, navigator::to)
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

    override fun invalidate() {
        withState(tasksViewModel) {
            binding.state = it
            tasksController.submitList(it.tasksMini)
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
