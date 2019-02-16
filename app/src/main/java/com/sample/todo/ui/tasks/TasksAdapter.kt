package com.sample.todo.ui.tasks

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.todo.databinding.TasksTaskMiniItemBinding
import com.sample.todo.domain.model.TaskMini
import androidx.lifecycle.observe
import com.sample.todo.util.extension.observeEvent
import com.sample.todo.util.inflater
import timber.log.Timber

// TODO some thing with submitList(pagedList, commitCallback) see: https://issuetracker.google.com/issues/73781068
// TODO test again
// TODO with re-create view holder when changing filter type, why ?
class TasksAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val tasksViewModel: TasksViewModel
) : PagedListAdapter<TaskMini, TasksViewHolder>(DiffCallback) {
    init {
        tasksViewModel.currentTasks.observe(lifecycleOwner) {
            submitList(it)
        }
        tasksViewModel.filterChangeEvent.observeEvent(lifecycleOwner) {
            submitList(null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        Timber.d("onCreateViewHolder()")
        return TasksViewHolder(
            TasksTaskMiniItemBinding.inflate(parent.inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        Timber.d("onBindViewHolder(holder=$holder, position=$position)")
        holder.binding.apply {
            task = getItem(position)
            viewModel = tasksViewModel
            setLifecycleOwner(lifecycleOwner)
            executePendingBindings()
        }
    }
}

private object DiffCallback : DiffUtil.ItemCallback<TaskMini>() {
    override fun areItemsTheSame(oldItem: TaskMini, newItem: TaskMini): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskMini, newItem: TaskMini): Boolean {
        return oldItem == newItem
    }
}

// TODO: add header see: https://github.com/googlesamples/android-architecture-components/issues/375
class TasksViewHolder(val binding: TasksTaskMiniItemBinding) : RecyclerView.ViewHolder(binding.root)