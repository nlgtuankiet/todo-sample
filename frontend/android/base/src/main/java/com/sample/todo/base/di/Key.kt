package com.sample.todo.base.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.work.ListenableWorker
import dagger.MapKey
import kotlin.reflect.KClass

@Retention(AnnotationRetention.BINARY)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Retention(AnnotationRetention.BINARY)
@MapKey
annotation class WorkerKey(val value: KClass<out ListenableWorker>)

@Retention(AnnotationRetention.BINARY)
@MapKey
annotation class FragmentKey(val value: KClass<out Fragment>)
