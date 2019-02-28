package com.sample.todo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

// TODO scope?
class TodoViewModelFactory @Inject constructor(
    providers: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : BassViewModelFactory(providers)

abstract class BassViewModelFactory(
    private val providers: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val found = providers.entries.find { modelClass.isAssignableFrom(it.key) }
        val provider = found?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return provider.get() as T
        } catch (e: Throwable) {
            throw RuntimeException(e)
        }
    }
}