package com.sample.todo.main.about

import com.sample.todo.base.di.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class AboutFragmentProvider @Inject constructor(){
    private lateinit var fragment: AboutFragment

    fun get(): AboutFragment = fragment
    fun set(fragment: AboutFragment) {
        this.fragment = fragment
    }

}