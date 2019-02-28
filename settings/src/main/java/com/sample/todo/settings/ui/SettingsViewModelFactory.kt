package com.sample.todo.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.todo.ui.BassViewModelFactory
import javax.inject.Inject
import javax.inject.Provider

class SettingsViewModelFactory @Inject constructor(
    providers: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : BassViewModelFactory(providers)