package com.sample.todo.main.search

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.sample.todo.base.di.FragmentScoped
import com.sample.todo.base.Holder
import com.sample.todo.domain.model.SearchResult
import javax.inject.Inject

@FragmentScoped
class SearchController @Inject constructor(
    private val holder: Holder<SearchFragment>
) : PagedListEpoxyController<SearchResult>() {
    private val viewModel by lazy { holder.instance.searchViewModel }

    override fun buildItemModel(currentPosition: Int, item: SearchResult?): EpoxyModel<*> {
        return SearchItemBindingModel_().apply {
            id(item?.hashCode() ?: -1)
            viewModel(viewModel)
            item(item)
        }
    }

    override fun onExceptionSwallowed(exception: RuntimeException) {
        throw exception
    }
}
