package com.sample.todo.dynamic.data.task.firestore.function

import androidx.paging.DataSource
import com.google.firebase.functions.FirebaseFunctions
import com.sample.todo.domain.model.SearchResult
import com.sample.todo.dynamic.data.task.firestore.function.entity.SearchResponce
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.squareup.moshi.JsonAdapter

// TODO report bug cannot have inner inner class
class SearchResultDataSourcefactory @AssistedInject constructor(
    @Assisted private val query: String,
    private val firebaseFunctions: FirebaseFunctions,
    private val searchResponceJsonAdapter: JsonAdapter<SearchResponce>,
    private val anyJsonAdapter: JsonAdapter<Any>,
    private val searchResultMapper: SearchResultMapper
) : DataSource.Factory<Int, SearchResult>() {
    @AssistedInject.Factory
    interface Factory {
        fun create(query: String): SearchResultDataSourcefactory
    }
    override fun create(): DataSource<Int, SearchResult> {
        return SearchResultDataSource(
            query, firebaseFunctions, searchResponceJsonAdapter, anyJsonAdapter, searchResultMapper
        )
    }
}
