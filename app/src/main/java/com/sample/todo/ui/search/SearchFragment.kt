package com.sample.todo.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.sample.todo.R
import com.sample.todo.core.BaseFragment
import com.sample.todo.databinding.SearchFragmentBinding
import com.sample.todo.ui.message.MessageManager
import com.sample.todo.util.LinearOffsetsItemDecoration
import com.sample.todo.util.extension.hideKeyboard
import com.sample.todo.util.extension.observeEvent
import javax.inject.Inject

class SearchFragment : BaseFragment() {
    override fun invalidate() {
        withState(searchViewModel) {
            binding.state = it
            searchController.submitList(it.searchResult())
        }
    }

    @Inject
    lateinit var viewModelFactory: SearchViewModel.Factory
    @Inject
    lateinit var messageManager: MessageManager
    private lateinit var binding: SearchFragmentBinding
    private val searchViewModel: SearchViewModel by fragmentViewModel()
    private lateinit var searchController: SearchController

    // TODO android:onTextChanged="@{(text, start, before, count) -> viewModel.onUsernameTextChanged(text)}"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = searchViewModel
            lifecycleOwner = viewLifecycleOwner
            searchResultRecyclerView.apply {
                searchController = SearchController(searchViewModel)
                adapter = searchController.adapter
                addItemDecoration(LinearOffsetsItemDecoration())
            }
            toolbar.apply {
                inflateMenu(R.menu.search_fragment_main)
                (menu.findItem(R.id.action_search).actionView as SearchView).apply {
                    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            return searchViewModel.onSearchTextChange(newText)
                        }
                    })
                }
            }
        }
        searchViewModel.apply {
            navigationEvent.observeEvent(viewLifecycleOwner) {
                binding.root.hideKeyboard()
                findNavController().navigate(it)
            }
        }
        return binding.root
    }
}