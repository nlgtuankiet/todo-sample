package com.sample.todo.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MvRxView
import com.airbnb.mvrx.MvRxViewModelStore
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.sample.todo.base.extension.hideKeyboard
import com.sample.todo.base.message.MessageManager
import com.sample.todo.base.extension.observeEvent
import com.sample.todo.base.widget.LinearOffsetsItemDecoration
import com.sample.todo.main.search.databinding.SearchFragmentBinding

class SearchFragment(
    val viewModelFactory: SearchViewModel.Factory,
    private val messageManager: MessageManager,
    private val controller: SearchController,
    private val navigator: SearchNavigator
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
                setController(controller)
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
            navigateToTaskDetailEvent.observeEvent(viewLifecycleOwner) {
                binding.root.hideKeyboard()
                navigator.to(it)
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
            controller.submitList(it.searchResult())
        }
    }
}
