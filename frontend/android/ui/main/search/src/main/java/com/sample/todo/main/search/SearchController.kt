package com.sample.todo.main.search

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyViewHolder
import com.airbnb.mvrx.withState
import com.sample.todo.base.Holder
import com.sample.todo.base.di.FragmentScope
import javax.inject.Inject
import kotlin.math.roundToInt

@FragmentScope
class SearchController @Inject constructor(
    private val fragmentHolder: Holder<SearchFragment>
) : EpoxyController() {
    private var requestLoadedMore = false

    override fun buildModels() {
        requestLoadedMore = false
        val state = withState(fragmentHolder.instance.searchViewModel) { it }
        state.searchResultStatistics?.let { searchResultStatistics ->
            searchStatisticsItem {
                id("searchStatisticsItem")
                statistics(searchResultStatistics)
            }
        }
        state.searchResult?.let { items ->
            val loadedCount = items.loadedCount
            items.take(loadedCount).forEach { searchResult ->
                searchResultItem {
                    id(searchResult.id)
                    item(searchResult)
                    viewModel(fragmentHolder.instance.searchViewModel)
                }
            }
        }
    }

    override fun onModelBound(
        holder: EpoxyViewHolder,
        boundModel: EpoxyModel<*>,
        position: Int,
        previouslyBoundModel: EpoxyModel<*>?
    ) {
        super.onModelBound(holder, boundModel, position, previouslyBoundModel)
        val state = withState(fragmentHolder.instance.searchViewModel) { it }
        if (position > ((state.searchResult?.loadedCount ?: 0) * 0.8).roundToInt() && !requestLoadedMore) {
            requestLoadedMore = true
            state.searchResult?.loadAround(position)
        }
    }
}
