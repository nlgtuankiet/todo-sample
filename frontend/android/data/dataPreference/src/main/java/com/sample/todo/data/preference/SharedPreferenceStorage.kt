package com.sample.todo.data.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import com.sample.todo.data.core.DataScope
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * from the doc: https://developer.android.com/reference/android/content/SharedPreferences
 * For any particular set of preferences, there is a single instance of this class that all clients share.
 *
 * which mean we can now put SharePreference this in Dagger
 */
@DataScope
class SharedPreferenceStorage @Inject constructor(
    context: Context
) : PreferenceStorage {
    override fun getDataImplementationOrdinal(): Int {
        return _dataImplementationOrdinal
    }

    override fun setDataImplementationOrdinal(value: Int) {
        _dataImplementationOrdinal = value
    }

    @VisibleForTesting
    val prefs: SharedPreferences = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private var _totalTaskSeeded: Int by IntPreference(
        preferences = prefs,
        name = PREFS_TOTAL_TASK_SEEDED,
        defaultValue = 0
    )

    private var _taskFilterTypeOrdinal: Int by IntPreference(
        preferences = prefs,
        name = PREFS_TASK_FILTER_TYPE,
        defaultValue = -1
    )

    private var _dataImplementationOrdinal: Int by IntPreference(
        preferences = prefs,
        name = PREFS_DATA_IMPLEMENTATION_ORDINAL,
        defaultValue = -1
    )


    private val _taskFilterTypeOrdinalObservable by lazy {
        prefs.observableOf(PREFS_TASK_FILTER_TYPE, 0)
    }

    override suspend fun getTotalTaskSeeded(): Int = withContext(Dispatchers.IO) {
        _totalTaskSeeded
    }

    override suspend fun increaseTotalTaskSeeded(amount: Int): Int = withContext(Dispatchers.IO) {
        _totalTaskSeeded += amount
        _totalTaskSeeded
    }

    override suspend fun getTaskFilterTypeOrdinal(): Int = withContext(Dispatchers.IO) {
        _taskFilterTypeOrdinal
    }

    override suspend fun setTaskFilterTypeOrdinal(value: Int) = withContext(Dispatchers.IO) {
        _taskFilterTypeOrdinal = value
    }

    override fun getTaskFilterTypeOrdinalObservable(): Observable<Int> {
        return _taskFilterTypeOrdinalObservable.subscribeOn(Schedulers.io())
    }

    companion object {
        const val PREFS_NAME = "todo_pref"
        @VisibleForTesting
        const val PREFS_TASK_FILTER_TYPE = "task_filter_type"
        @VisibleForTesting
        const val PREFS_TOTAL_TASK_SEEDED = "total_task_seeded"
        const val PREFS_DATA_IMPLEMENTATION_ORDINAL = "data_implementation_ordinal"
    }
}