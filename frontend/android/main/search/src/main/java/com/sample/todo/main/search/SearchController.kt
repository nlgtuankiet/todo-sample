package com.sample.todo.main.search

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.sample.todo.domain.model.SearchResult

class SearchController(
    private val searchViewModel: SearchViewModel
) : PagedListEpoxyController<SearchResult>() {
    override fun buildItemModel(currentPosition: Int, item: SearchResult?): EpoxyModel<*> {
        return SearchItemBindingModel_().apply {
            id(item?.hashCode() ?: -1)
            viewModel(searchViewModel)
            item(item)
        }
    }

    override fun onExceptionSwallowed(exception: RuntimeException) {
        throw exception
    }
}
