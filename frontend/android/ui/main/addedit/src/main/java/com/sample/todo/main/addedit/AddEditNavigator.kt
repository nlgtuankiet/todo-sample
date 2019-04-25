package com.sample.todo.main.addedit

import com.sample.todo.base.FragmentNavigator
import com.sample.todo.base.di.FragmentScoped
import com.sample.todo.base.Holder
import javax.inject.Inject

@FragmentScoped
class AddEditNavigator @Inject constructor(
    holder: Holder<AddEditFragment>
) : FragmentNavigator(holder)
