package com.sample.todo.data.firestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class FirestoreDataSource(
    private val baseQuery: Query,
    private val source: Source = Source.DEFAULT
) :
    PageKeyedDataSource<PageKey, DocumentSnapshot>() {

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private var retryRunnable: Runnable? = null

    class Factory(
        private val query: Query,
        private val source: Source = Source.DEFAULT
    ) : DataSource.Factory<PageKey, DocumentSnapshot>() {
        override fun create(): DataSource<PageKey, DocumentSnapshot> {
            return FirestoreDataSource(query, source)
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<PageKey>,
        callback: LoadInitialCallback<PageKey, DocumentSnapshot>
    ) {
        // Set initial loading state
        _loadingState.postValue(LoadingState.LOADING_INITIAL)

        baseQuery.limit(params.requestedLoadSize.toLong())
            .also {
            }
            .get(source)
            .addOnSuccessListener(Dispatchers.FireStore.executor, object : OnLoadSuccessListener() {
                override fun setResult(snapshot: QuerySnapshot) {
                    val nextPage = getNextPageKey(snapshot)
                    callback.onResult(snapshot.documents, null, nextPage)
                }
            })
            .addOnFailureListener(Dispatchers.FireStore.executor, object : OnLoadFailureListener() {
                override val retryRunnable: Runnable
                    get() = getRetryLoadInitial(params, callback)
            })
    }

    override fun loadBefore(
        params: LoadParams<PageKey>,
        callback: LoadCallback<PageKey, DocumentSnapshot>
    ) {
        // Ignored for now, since we only ever append to the initial load.
        // Future work:
        //  * Could we dynamically unload past pages?
        //  * Could we ask the developer for both a forward and reverse base query
        //    so that we can load backwards easily?
    }

    override fun loadAfter(
        params: LoadParams<PageKey>,
        callback: LoadCallback<PageKey, DocumentSnapshot>
    ) {
        val key = params.key

        // Set loading state
        _loadingState.postValue(LoadingState.LOADING_MORE)

        key.getPageQuery(baseQuery, params.requestedLoadSize.toLong())
            .get(source)
            .addOnSuccessListener(Dispatchers.FireStore.executor, object : OnLoadSuccessListener() {
                override fun setResult(snapshot: QuerySnapshot) {
                    val nextPage = getNextPageKey(snapshot)
                    callback.onResult(snapshot.documents, nextPage)
                }
            })
            .addOnFailureListener(Dispatchers.FireStore.executor, object : OnLoadFailureListener() {
                override val retryRunnable: Runnable
                    get() = getRetryLoadAfter(params, callback)
            })
    }

    private fun getNextPageKey(snapshot: QuerySnapshot): PageKey {
        val data = snapshot.documents
        val last = getLast(data)

        return PageKey(last, null)
    }

    fun retry() {
        val currentState = _loadingState.value
        if (currentState !== LoadingState.ERROR) {
            Timber.tag(TAG).w("retry() not valid when in state: %s", currentState!!)
            return
        }

        if (retryRunnable == null) {
            Timber.tag(TAG).w("retry() called with no eligible retry runnable.")
            return
        }

        retryRunnable!!.run()
    }

    private fun getLast(data: List<DocumentSnapshot>): DocumentSnapshot? {
        return if (data.isEmpty()) {
            null
        } else {
            data[data.size - 1]
        }
    }

    private fun getRetryLoadAfter(
        params: LoadParams<PageKey>,
        callback: LoadCallback<PageKey, DocumentSnapshot>
    ): Runnable {
        return Runnable { loadAfter(params, callback) }
    }

    private fun getRetryLoadInitial(
        params: LoadInitialParams<PageKey>,
        callback: LoadInitialCallback<PageKey, DocumentSnapshot>
    ): Runnable {
        return Runnable { loadInitial(params, callback) }
    }

    /**
     * Success listener that sets success state and nullifies the retry runnable.
     */
    private abstract inner class OnLoadSuccessListener : OnSuccessListener<QuerySnapshot> {

        override fun onSuccess(snapshot: QuerySnapshot) {
            setResult(snapshot)
            _loadingState.postValue(LoadingState.LOADED)

            // Post the 'FINISHED' state when no more pages will be loaded. The data source
            // callbacks interpret an empty result list as a signal to cancel any future loads.
            if (snapshot.documents.isEmpty()) {
                _loadingState.postValue(LoadingState.FINISHED)
            }

            retryRunnable = null
        }

        protected abstract fun setResult(snapshot: QuerySnapshot)
    }

    /**
     * Error listener that logs, sets the error state, and sets up retry.
     */
    private abstract inner class OnLoadFailureListener : OnFailureListener {

        protected abstract val retryRunnable: Runnable

        override fun onFailure(e: Exception) {
            Timber.tag(TAG).w(e, "load:onFailure")

            // On error we do NOT post any value to the PagedList, we just tell
            // the developer that we are now in the error state.
            _loadingState.postValue(LoadingState.ERROR)

            // Set the retry action
            this@FirestoreDataSource.retryRunnable = retryRunnable
        }
    }

    companion object {
        private const val TAG = "FirestoreDataSource"
    }
}