package com.sample.todo.util

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.lifecycle.toLiveData
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sample.todo.ui.HasTopNavigation
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable

inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(this, provider).get(VM::class.java)

inline fun <reified VM : ViewModel> Fragment.activityViewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(requireActivity(), provider).get(VM::class.java)

inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(this, provider).get(VM::class.java)

val View.inflater: LayoutInflater
    get() = LayoutInflater.from(this.context)

inline fun <reified T : Any?> Map<String, Any>.getOrThrow(key: String, allowNull: Boolean = false): T {
    if (!containsKey(key))
        throw IllegalArgumentException("""Required argument "$key" is missing""")
    val value: Any? = get(key)
    if (value == null && !allowNull)
        throw IllegalArgumentException("""Require "$key" as non-null but was a null value.""")
    if (value !is T)
        throw IllegalArgumentException("""Type of "$key" is not ${T::class.java.name}""")
    return value
}

fun <T> Observable<T>.asLiveData(): LiveData<T> = toFlowable(BackpressureStrategy.LATEST).toLiveData()

fun BottomNavigationView.setupWith(fragment: Fragment) {
    val activity = fragment.requireActivity() as? HasTopNavigation
        ?: throw IllegalArgumentException("Fragment activity is not HasTopNavigation")

    setOnNavigationItemSelectedListener {
        activity.onTopNavigationSelected(it.itemId)
        true
    }

    activity.getNavControllerIdLiveData().observe(fragment.viewLifecycleOwner) {
        selectedItemId = it
    }
}