package com.sample.todo.main.search

import com.sample.todo.base.FragmentNavigator
import com.sample.todo.base.Holder
import com.sample.todo.base.di.FragmentScope
import javax.inject.Inject

@FragmentScope
class SearchNavigator @Inject constructor(
    holder: Holder<SearchFragment>
) : FragmentNavigator(holder)
