package com.sample.todo.main.search

import com.sample.todo.base.FragmentNavigator
import com.sample.todo.base.Holder
import com.sample.todo.base.di.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class SearchNavigator @Inject constructor(
    holder: Holder<SearchFragment>
) : FragmentNavigator(holder)