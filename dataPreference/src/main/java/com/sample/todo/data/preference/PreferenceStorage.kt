@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.sample.todo.data.preference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import com.sample.todo.data.core.DataScope
import io.reactivex.Observable
import io.reactivex.disposables.Disposables
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// TODO how to make android studio notify about exception could be throw?
interface PreferenceStorage {
    suspend fun getTotalTaskSeeded(): Int
    suspend fun increaseTotalTaskSeeded(amount: Int): Int
    suspend fun getTaskFilterTypeOrdinal(): Int
    suspend fun setTaskFilterTypeOrdinal(value: Int)
    fun getTaskFilterTypeOrdinalObservable(): Observable<Int>
}

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

    @VisibleForTesting
    val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

    private var _totalTaskSeeded: Int by IntPreference(
        prefs,
        PREFS_TOTAL_TASK_SEEDED,
        0
    )

    private var _taskFilterTypeOrdinal: Int by IntPreference(
        prefs,
        PREFS_TASK_FILTER_TYPE,
        -1
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
        return _taskFilterTypeOrdinalObservable
    }

    companion object {
        const val PREFS_NAME = "todo_pref"
        @VisibleForTesting
        const val PREFS_TASK_FILTER_TYPE = "task_filter_type"
        @VisibleForTesting
        const val PREFS_TOTAL_TASK_SEEDED = "total_task_seeded"
    }
}

class IntPreference(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: Int
) : ReadWriteProperty<Any, Int> {
    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Int {
        return preferences.getInt(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        preferences.edit { putInt(name, value) }
    }
}

inline fun <reified T : Any> SharedPreferences.observableOf(key: String, defaultValue: T): Observable<T> {
    requireNotNull(defaultValue)
    val sharedPreferences = this
    return Observable.create { emitter ->
        val tracker: SharedPreferences.OnSharedPreferenceChangeListener =
            SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
                if (changedKey == key && !emitter.isDisposed) {
                    emitter.onNext(getValueOf(key, defaultValue))
                }
            }
        if (!emitter.isDisposed) {
            emitter.onNext(getValueOf(key, defaultValue))
            sharedPreferences.registerOnSharedPreferenceChangeListener(tracker)
            emitter.setDisposable(Disposables.fromAction {
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(tracker)
            })
        }
    }
}

@WorkerThread
inline fun <reified T : Any> SharedPreferences.getValueOf(key: String, defaultValue: T): T {
    requireNotNull(defaultValue)
    return try {
        when (defaultValue) { // TODO workaround?
            is Boolean -> getBoolean(key, defaultValue)
            is Float -> getFloat(key, defaultValue)
            is Int -> getInt(key, defaultValue)
            is Long -> getLong(key, defaultValue)
            is String -> getString(key, defaultValue)
            else -> throw IllegalArgumentException("Unsupported type: ${T::class.java.name}")
        } as? T ?: throw RuntimeException("How this could happend?")
    } catch (e: ClassCastException) {
        defaultValue
    }
}
