package com.sample.todo.ui.search

import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.sample.todo.R
import timber.log.Timber

@BindingAdapter("searchResultStat")
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