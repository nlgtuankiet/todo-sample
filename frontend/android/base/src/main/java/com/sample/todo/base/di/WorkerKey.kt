package com.sample.todo.base.di

import androidx.work.ListenableWorker
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Retention(AnnotationRetention.BINARY)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)
