package com.sample.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.sample.todo.base.di.AppScope
import com.sample.todo.base.di.ApplicationScope
import com.sample.todo.main.about.AboutFragment
import com.sample.todo.main.about.AboutSubcomponent
import javax.inject.Inject
import javax.inject.Provider

@AppScope
class TodoFragmentFactory @Inject constructor(
    private val aboutFactory: AboutSubcomponent.Factory
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        println("load name: $className")
        return when (className) {
            AboutFragment::class.java.name -> aboutFactory.create().fragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}