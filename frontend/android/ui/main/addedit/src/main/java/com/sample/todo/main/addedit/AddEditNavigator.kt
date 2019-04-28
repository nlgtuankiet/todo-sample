package com.sample.todo.main.addedit

import com.sample.todo.base.FragmentNavigator
import com.sample.todo.base.di.FragmentScope
import com.sample.todo.base.Holder
import javax.inject.Inject

@FragmentScope
class AddEditNavigator @Inject constructor(
    holder: Holder<AddEditFragment>
) : FragmentNavigator(holder)
