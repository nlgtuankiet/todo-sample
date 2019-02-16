package com.sample.todo.ui.search

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.todo.databinding.SearchItemBinding
import com.sample.todo.domain.model.SearchResult
import androidx.lifecycle.observe
import com.sample.todo.util.extension.observeEvent
import com.sample.todo.util.inflater
import timber.log.Timber

// TODO implement loading
class SearchAdapter(
    private val searchViewModel: SearchViewModel,
    private val lifecycleOwner: LifecycleOwner
) : PagedListAdapter<SearchResult, SearchResultViewHolder>(DiffCallback) {
    init {
        searchViewModel.source.observe(lifecycleOwner) {
            Timber.d("loaded ${it?.loadedCount} from SearchAdapter: ${this.hashCode()}")
            submitList(it)
        }
        searchViewModel.queryChangeEvent.observeEvent(lifecycleOwner) {
            // see: https://link.medium.com/aI6y7j87gT
            // example: input query: "a b" -> scroll map middle -> input "a", new list will jump map middle
            submitList(null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchResultViewHolder(
        SearchItemBinding.inflate(parent.inflater, parent, false)
    )

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.binding.apply {
            viewModel = searchViewModel
            item = getItem(position)
            setLifecycleOwner(lifecycleOwner)
            executePendingBindings()
        }
    }
}

private object DiffCallback : DiffUtil.ItemCallback<SearchResult>() {
    override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
        return oldItem == newItem
    }
}

class SearchResultViewHolder(val binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root)