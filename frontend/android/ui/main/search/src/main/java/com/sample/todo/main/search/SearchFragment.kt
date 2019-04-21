package com.sample.todo.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.MvRxView
import com.airbnb.mvrx.MvRxViewModelStore
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.sample.todo.base.extension.hideKeyboard
import com.sample.todo.base.message.MessageManager
import com.sample.todo.base.extension.observeEvent
import com.sample.todo.base.widget.LinearOffsetsItemDecoration
import com.sample.todo.main.search.databinding.SearchFragmentBinding
import javax.inject.Inject

class SearchFragment(
    val viewModelFactory: SearchViewModel.Factory,
    private val messageManager: MessageManager,
    private val searchController: SearchController
) : Fragment(), MvRxView {
    override val mvrxViewModelStore by lazy { MvRxViewModelStore(viewModelStore) }
    val searchViewModel: SearchViewModel by fragmentViewModel()

    private lateinit var binding: SearchFragmentBinding

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
                setController(searchController)
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

    override fun invalidate() {
        withState(searchViewModel) {
            binding.state = it
            searchController.submitList(it.searchResult())
        }
    }
}