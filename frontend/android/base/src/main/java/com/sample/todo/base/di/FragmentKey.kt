package com.sample.todo.base.di

import androidx.fragment.app.Fragment
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Retention(AnnotationRetention.BINARY)
annotation class FragmentKey(val value: KClass<out Fragment>)
