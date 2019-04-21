package com.sample.todo.di

import com.sample.todo.base.di.FragmentComponent
import com.sample.todo.base.di.FragmentKey
import com.sample.todo.main.about.AboutComponent
import com.sample.todo.main.about.AboutFragment
import com.sample.todo.main.addedit.AddEditComponent
import com.sample.todo.main.addedit.AddEditFragment
import com.sample.todo.main.search.SearchComponent
import com.sample.todo.main.search.SearchFragment
import com.sample.todo.main.statistics.StatisticsComponent
import com.sample.todo.main.statistics.StatisticsFragment
import com.sample.todo.main.taskdetail.TaskDetailComponent
import com.sample.todo.main.taskdetail.TaskDetailFragment
import com.sample.todo.main.tasks.TasksComponent
import com.sample.todo.main.tasks.TasksFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FragmentComponentBindingModule {
    @Binds
    @IntoMap
    @FragmentKey(AboutFragment::class)
    fun AboutComponent(impl: AboutComponent.Factory): FragmentComponent.Factory<*>

    @Binds
    @IntoMap
    @FragmentKey(AddEditFragment::class)
    fun AddEditComponent(impl: AddEditComponent.Factory): FragmentComponent.Factory<*>

    @Binds
    @IntoMap
    @FragmentKey(SearchFragment::class)
    fun SearchComponent(impl: SearchComponent.Factory): FragmentComponent.Factory<*>

    @Binds
    @IntoMap
    @FragmentKey(StatisticsFragment::class)
    fun StatisticsComponent(impl: StatisticsComponent.Factory): FragmentComponent.Factory<*>

    @Binds
    @IntoMap
    @FragmentKey(TasksFragment::class)
    fun TasksComponent(impl: TasksComponent.Factory): FragmentComponent.Factory<*>

    @Binds
    @IntoMap
    @FragmentKey(TaskDetailFragment::class)
    fun TaskDetailComponent(impl: TaskDetailComponent.Factory): FragmentComponent.Factory<*>
}