package com.sample.todo.main.search

import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.sample.todo.domain.model.SearchResultStatistics
import timber.log.Timber

object BindingAdapters {
    @BindingAdapter("searchResultStat")
    @JvmStatic
    fun searchResultStat(toolbar: Toolbar, stat: SearchResultStatistics?) {
        Timber.d("searchResultStat(stat=$stat)")
        if (stat == null) {
            toolbar.subtitle = null
            return
        }
        val text = toolbar.context.getString(
            R.string.search_search_result_statistics_format,
            stat.totalResultCount,
            stat.totalTaskCount
        )
        toolbar.subtitle = text
    }
}
