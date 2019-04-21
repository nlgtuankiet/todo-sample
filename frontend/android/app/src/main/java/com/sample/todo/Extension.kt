package com.sample.todo

import android.content.Context
import com.sample.todo.data.DataComponent
import com.sample.todo.di.AndroidComponent
import com.sample.todo.di.AppComponent
import com.sample.todo.domain.di.DomainComponent

val Context.androidComponent: AndroidComponent
    get() = todoApplication.androidComponent

val Context.dataComponent: DataComponent
    get() = todoApplication.dataComponent

val Context.domainComponent: DomainComponent
    get() = todoApplication.domainComponent

val Context.appComponent: AppComponent
    get() = todoApplication.appComponent

private val Context.todoApplication: TodoApplication
    get() = applicationContext as? TodoApplication
        ?: throw IllegalArgumentException("This context not TodoApplication")
