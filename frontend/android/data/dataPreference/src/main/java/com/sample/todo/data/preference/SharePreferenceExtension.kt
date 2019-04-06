package com.sample.todo.data.preference

import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import io.reactivex.Observable
import io.reactivex.disposables.Disposables

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
