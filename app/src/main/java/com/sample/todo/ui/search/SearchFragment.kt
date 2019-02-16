package com.sample.todo.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sample.todo.R
import com.sample.todo.databinding.SearchFragmentBinding
import com.sample.todo.ui.message.MessageManager
import com.sample.todo.util.LinearOffsetsItemDecoration
import com.sample.todo.util.extension.hideKeyboard
import com.sample.todo.util.extension.observeEvent
import com.sample.todo.util.setupWith
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SearchFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var messageManager: MessageManager
    private lateinit var binding: SearchFragmentBinding
    private val searchViewModel: SearchViewModel by viewModels { viewModelFactory }

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
                adapter = SearchAdapter(
                    searchViewModel = searchViewModel,
                    lifecycleOwner = viewLifecycleOwner
                )
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
            bottomNavigationView.setupWith(this@SearchFragment)
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