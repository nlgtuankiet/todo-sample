package com.sample.todo.main.tasks

import com.sample.todo.base.FragmentNavigator
import com.sample.todo.base.di.FragmentScoped
import com.sample.todo.base.Holder
import javax.inject.Inject

@FragmentScoped
class TasksNavigator @Inject constructor(
    holder: Holder<TasksFragment>
) : FragmentNavigator(holder)
