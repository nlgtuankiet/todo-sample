package com.sample.todo.data.task.firestore.function

import androidx.paging.PageKeyedDataSource
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.HttpsCallableResult
import com.sample.todo.data.task.firestore.FireStore
import com.sample.todo.data.task.firestore.function.entity.SearchResponce
import com.sample.todo.domain.model.SearchResult
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class SearchResultDataSource(
    private val query: String,
    private val firebaseFunctions: FirebaseFunctions,
    private val searchResponceJsonAdapter: JsonAdapter<SearchResponce>,
    private val anyJsonAdapter: JsonAdapter<Any>,
    private val searchResultMapper: SearchResultMapper
) : PageKeyedDataSource<Int, SearchResult>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, SearchResult>
    ) {
        loadResult(page = 0, pageSize = params.requestedLoadSize) {
            callback.onResult(it, null, null)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SearchResult>) {
        loadResult(page = params.key + 1, pageSize = params.requestedLoadSize) {
            callback.onResult(it, null)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, SearchResult>) {
        val page = params.key - 1
        if (page >= 0) {
            loadResult(page = params.key + 1, pageSize = params.requestedLoadSize) {
                callback.onResult(it, null)
            }
        }
    }

    private fun loadResult(page: Int, pageSize: Int, callback: (List<SearchResult>) -> Unit) {
        firebaseFunctions.getHttpsCallable("searchTask").call(
            mapOf(
                "query" to query,
                "page" to page,
                "pageSize" to pageSize
            )
        ).addOnCompleteListener(
            Dispatchers.FireStore.executor,
            OnCompleteListener<HttpsCallableResult?> { task ->
                if (task.isSuccessful) {
                    val resultJson = anyJsonAdapter.toJson(task.result?.data)
                    Timber.d("json result is: $resultJson")
                    val searchResponce = searchResponceJsonAdapter.fromJson(resultJson) ?: TODO()
                    Timber.d("searchResponce is: $searchResponce")
                    val searchResults = searchResultMapper.map(searchResponce)
                    callback(searchResults)
                } else {
                    Timber.e(task.exception)
                }
            })
    }
}
